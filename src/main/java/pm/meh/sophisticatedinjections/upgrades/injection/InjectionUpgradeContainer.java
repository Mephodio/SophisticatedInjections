package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public class InjectionUpgradeContainer extends UpgradeContainerBase<InjectionUpgradeWrapper, InjectionUpgradeContainer> {
    public InjectionUpgradeContainer(Player player, int upgradeContainerId, InjectionUpgradeWrapper upgradeWrapper, UpgradeContainerType<InjectionUpgradeWrapper, InjectionUpgradeContainer> type) {
        super(player, upgradeContainerId, upgradeWrapper, type);
    }

    @Override
    public void handleMessage(CompoundTag compoundTag) {

    }
}
