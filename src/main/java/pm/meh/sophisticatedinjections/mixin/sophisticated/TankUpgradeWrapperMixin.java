package pm.meh.sophisticatedinjections.mixin.sophisticated;

import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeWrapper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(TankUpgradeWrapper.class)
@Debug(export = true)
public abstract class TankUpgradeWrapperMixin {
    @Final
    @Shadow
    private ItemStackHandler inventory;

    @Shadow
    private FluidStack contents;

    @Shadow
    public abstract int getTankCapacity();

    @Shadow
    public abstract int fill(FluidStack resource, IFluidHandler.FluidAction action, boolean ignoreInOutLimit);

    @Inject(method = "tick(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/util/LazyOptional;ifPresent(Lnet/minecraftforge/common/util/NonNullConsumer;)V",
                    shift = At.Shift.AFTER, ordinal = 1), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    public void injectTick(LivingEntity entity, Level world, BlockPos pos, CallbackInfo ci, AtomicBoolean didSomething) {
        ItemStack stack = inventory.getStackInSlot(0);

        if (!didSomething.get() && PotionFluidHandler.isPotionItem(stack)) {
            FluidStack fluid = PotionFluidHandler.getFluidFromPotionItem(stack);

            if (contents.isEmpty() || contents.isFluidEqual(fluid) &&
                    getTankCapacity() - contents.getAmount() >= fluid.getAmount()) {
                stack.shrink(1);
                // TODO handle stacks of more than one item
                inventory.setStackInSlot(0, new ItemStack(Items.GLASS_BOTTLE));

                fill(fluid, IFluidHandler.FluidAction.EXECUTE, false);

                didSomething.set(true);
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
