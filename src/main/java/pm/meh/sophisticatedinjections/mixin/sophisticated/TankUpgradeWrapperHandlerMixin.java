package pm.meh.sophisticatedinjections.mixin.sophisticated;

import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeWrapper$1")
public class TankUpgradeWrapperHandlerMixin {
    @Inject(method = "isValidInputItem(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void injectIsValidFluidItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (PotionFluidHandler.isPotionItem(stack)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
