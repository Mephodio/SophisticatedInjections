package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

import java.util.Map;

public class InjectionUpgradeTab extends UpgradeSettingsTab<InjectionUpgradeContainer> {

    public static final ButtonDefinition.Toggle<Boolean> BUTTON_INJECT = ButtonDefinitions.createToggleButtonDefinition(
            Map.of(
                    false, GuiHelper.getButtonStateData(new UV(192, 48), "gui.sophisticatedinjections.upgrades.injection.inject.available", Dimension.SQUARE_16, new Position(1, 1)),
                    true, GuiHelper.getButtonStateData(new UV(160, 48), "gui.sophisticatedinjections.upgrades.injection.inject.in_progress", Dimension.SQUARE_16, new Position(1, 1))
            )
    );

    public InjectionUpgradeTab(InjectionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
        super(upgradeContainer, position, screen,
                Component.translatable("gui.sophisticatedinjections.upgrades.injection"),
                Component.translatable("gui.sophisticatedinjections.upgrades.injection.tooltip"));

        addHideableChild(new ToggleButton<>(new Position(x + 3, y + 24), BUTTON_INJECT, (button) -> getContainer().inject(), () -> {
            ClientLevel level = Minecraft.getInstance().level;
            long worldTime = level != null ? level.getGameTime() : 0;
            return worldTime < getContainer().getSavedInjectionTime();
        }));
    }

    @Override
    protected void moveSlotsToTab() {}
}
