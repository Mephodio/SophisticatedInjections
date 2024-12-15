package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.*;

import java.util.Map;

import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.GUI_CONTROLS;
import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.ICONS;

public class InjectionUpgradeTab extends UpgradeSettingsTab<InjectionUpgradeContainer> {

    public static final ButtonDefinition.Toggle<InjectionState> BUTTON_INJECT = ButtonDefinitions.createToggleButtonDefinition(
            Map.of(
                    InjectionState.AVAILABLE, GuiHelper.getButtonStateData(new UV(192, 48), "Inject", Dimension.SQUARE_16, new Position(1, 1)),
                    InjectionState.IN_PROGRESS, GuiHelper.getButtonStateData(new UV(160, 48), "Injecting...", Dimension.SQUARE_16, new Position(1, 1)),
                    InjectionState.NO_POTION, GuiHelper.getButtonStateData(new UV(224, 48), "No potion", Dimension.SQUARE_16, new Position(1, 1))
            )
    );

    public InjectionUpgradeTab(InjectionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
        super(upgradeContainer, position, screen, Component.literal("injection"), Component.literal("injection tooltip"));

        addHideableChild(new ToggleButton<>(new Position(x + 3, y + 24), BUTTON_INJECT, (button) -> getContainer().inject(), () -> {
            ClientLevel level = Minecraft.getInstance().level;
            long worldTime = level != null ? level.getGameTime() : 0;
            return worldTime < getContainer().getSavedInjectionTime() ? InjectionState.IN_PROGRESS : InjectionState.AVAILABLE;
        }));
    }

    @Override
    protected void moveSlotsToTab() {}
}
