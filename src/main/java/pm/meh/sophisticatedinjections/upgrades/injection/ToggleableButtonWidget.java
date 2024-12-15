package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Button;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;

import javax.annotation.Nullable;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class ToggleableButtonWidget extends Button {

    private final Supplier<Boolean> isDisabled;
    private boolean hovered = false;

    public ToggleableButtonWidget(Position position, ButtonDefinition buttonDefinition, IntConsumer onClick, Supplier<Boolean> isDisabled) {
        super(position, buttonDefinition, onClick);
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean isHovered() {
        return this.hovered;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
        if (isDisabled.get()) {

        } else {
            super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
        }
    }
}
