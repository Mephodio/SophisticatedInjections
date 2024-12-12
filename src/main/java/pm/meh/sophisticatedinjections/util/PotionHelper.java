package pm.meh.sophisticatedinjections.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;

public class PotionHelper {
    public static int getBottleAmount() {
        // PotionFluidHandler.getRequiredAmountForFilledBottle
        return 250;
    }

    public static boolean isPotionFluid(FluidStack fluidStack) {
        CompoundTag tag = fluidStack.getOrCreateTag();
        return !PotionUtils.getAllEffects(tag).isEmpty();
    }
}
