package pm.meh.sophisticatedinjections.mixin.sophisticated;

import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeWrapper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.meh.sophisticatedinjections.util.PotionHelper;

import java.util.function.Consumer;

@Mixin(TankUpgradeWrapper.class)
@Debug(export = true)
public abstract class TankUpgradeWrapperMixin extends UpgradeWrapperBase<TankUpgradeWrapper, TankUpgradeItem> {
    @Final
    @Shadow
    private ItemStackHandler inventory;

    @Shadow
    private FluidStack contents;

    @Shadow
    private long cooldownTime;

    protected TankUpgradeWrapperMixin(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
        super(storageWrapper, upgrade, upgradeSaveHandler);
    }

    @Shadow
    public abstract int getTankCapacity();

    @Shadow
    public abstract int fill(FluidStack resource, IFluidHandler.FluidAction action, boolean ignoreInOutLimit);

    @Shadow
    public abstract FluidStack drain(int maxDrain, IFluidHandler.FluidAction action, boolean ignoreInOutLimit);

    @Inject(method = "tick(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
            at = @At("TAIL"), remap = false)
    public void injectTick(Entity entity, Level world, BlockPos pos, CallbackInfo ci) {
        if (world.getGameTime() >= cooldownTime) {
            ItemStack inputStack = inventory.getStackInSlot(0);
            ItemStack outputStack = inventory.getStackInSlot(1);
            boolean didSomething = false;

            if (PotionFluidHandler.isPotionItem(inputStack)) {
                FluidStack fluid = PotionFluidHandler.getFluidFromPotionItem(inputStack);

                if (contents.isEmpty() || contents.isFluidEqual(fluid) &&
                        getTankCapacity() - contents.getAmount() >= fluid.getAmount()) {
                    inputStack.shrink(1);
                    inventory.setStackInSlot(0, new ItemStack(Items.GLASS_BOTTLE));

                    fill(fluid, IFluidHandler.FluidAction.EXECUTE, false);

                    didSomething = true;
                }
            }

            if (outputStack.is(Items.GLASS_BOTTLE)) {
                int outAmount = PotionHelper.getBottleAmount();
                if (contents.getAmount() >= outAmount && PotionHelper.isPotionFluid(contents)) {
                    ItemStack filledItem = PotionFluidHandler.fillBottle(outputStack, contents);

                    drain(outAmount, IFluidHandler.FluidAction.EXECUTE, false);

                    outputStack.shrink(1);
                    inventory.setStackInSlot(1, filledItem);

                    didSomething = true;
                }
            }

            if (didSomething) {
                cooldownTime = world.getGameTime() + upgradeItem.getTankUpgradeConfig().autoFillDrainContainerCooldown.get();
            }
        }
    }

    @Inject(method = "isValidFluidItem(Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At("HEAD"), remap = false, cancellable = true)
    public void injectIsValidFluidItem(ItemStack stack, boolean isOutput, CallbackInfoReturnable<Boolean> cir) {
        if ((!isOutput && PotionFluidHandler.isPotionItem(stack) && (contents.isEmpty() ||
                contents.isFluidEqual(PotionFluidHandler.getFluidFromPotionItem(stack)))) ||
                (isOutput && stack.is(Items.GLASS_BOTTLE) && !contents.isEmpty())) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
