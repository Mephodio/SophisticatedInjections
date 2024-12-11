package pm.meh.sophisticatedinjections.mixin.sophisticated;

import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeWrapper$1")
public class TankUpgradeWrapperHandlerMixin {
    @Inject(method = "isValidInputItem(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void injectIsValidFluidItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (GenericItemEmptying.canItemBeEmptied(ServerLifecycleHooks.getCurrentServer().overworld(), stack)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
