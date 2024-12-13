package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.GUI_CONTROLS;
import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.ICONS;

public class InjectionUpgradeTab extends UpgradeSettingsTab<InjectionUpgradeContainer> {

    private static final ButtonDefinition BUTTON_INJECT = new ButtonDefinition(Dimension.SQUARE_16,
            new TextureBlitData(GUI_CONTROLS, new UV(29, 0), Dimension.SQUARE_18),
            new TextureBlitData(GUI_CONTROLS, new UV(47, 0), Dimension.SQUARE_18),
            new TextureBlitData(ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(192, 48), Dimension.SQUARE_16),
            Component.literal("test"));

    public InjectionUpgradeTab(InjectionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
        super(upgradeContainer, position, screen, Component.literal("injection"), Component.literal("injection tooltip"));

        addHideableChild(new Button(new Position(x + 3, y + 24), BUTTON_INJECT, (button) -> {
            getContainer().inject();
        }));
    }

    @Override
    protected void moveSlotsToTab() {}
}
