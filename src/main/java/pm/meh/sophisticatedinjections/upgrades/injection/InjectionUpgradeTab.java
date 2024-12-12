package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class InjectionUpgradeTab extends UpgradeSettingsTab<InjectionUpgradeContainer> {
    public InjectionUpgradeTab(InjectionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
        super(upgradeContainer, position, screen, Component.literal("injection"), Component.literal("injection tooltip"));
    }

    @Override
    protected void moveSlotsToTab() {}
}
