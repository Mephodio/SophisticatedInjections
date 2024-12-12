package pm.meh.sophisticatedinjections.mixin.sophisticated;

import net.minecraft.server.level.ServerPlayer;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.network.UpgradeToggleMessage;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pm.meh.sophisticatedinjections.upgrades.injection.InjectionUpgradeWrapper;

import java.util.Map;

@Mixin(UpgradeToggleMessage.class)
@Debug(export = true)
public class UpgradeToggleMessageMixin {
    @Inject(method = "lambda$handleMessage$1(Lnet/p3pp3rf1y/sophisticatedbackpacks/network/UpgradeToggleMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/IBackpackWrapper;)V",
            at = @At(value = "INVOKE", target = "Lnet/p3pp3rf1y/sophisticatedcore/upgrades/IUpgradeWrapper;setEnabled(Z)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, remap = false, cancellable = true)
    private static void injectSetEnabled(UpgradeToggleMessage msg, ServerPlayer player, IBackpackWrapper w, CallbackInfo ci, Map slotWrappers, IUpgradeWrapper upgradeWrapper) {
        if (upgradeWrapper instanceof InjectionUpgradeWrapper injectionWrapper) {
            injectionWrapper.injectIntoPlayer(player);
            ci.cancel();
        }
    }
}
