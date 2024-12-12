package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

import java.util.function.Consumer;

public class InjectionUpgradeWrapper extends UpgradeWrapperBase<InjectionUpgradeWrapper, InjectionUpgradeItem> {
    protected InjectionUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
        super(backpackWrapper, upgrade, upgradeSaveHandler);
    }
}
