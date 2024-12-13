package pm.meh.sophisticatedinjections.upgrades.injection;

import com.simibubi.create.AllSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import pm.meh.sophisticatedinjections.util.PotionHelper;

import java.util.List;
import java.util.function.Consumer;

public class InjectionUpgradeWrapper extends UpgradeWrapperBase<InjectionUpgradeWrapper, InjectionUpgradeItem> {
    protected InjectionUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
        super(backpackWrapper, upgrade, upgradeSaveHandler);
    }

    @Override
    public boolean canBeDisabled() {
        return false;
    }

    public void injectIntoPlayer(Player player) {
        storageWrapper.getFluidHandler().ifPresent(fluidHandler -> {
            int portionSize = PotionHelper.getBottleAmount();
            boolean injected = false;

            for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
                FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);

                if (fluidInTank.getAmount() >= portionSize) {
                    CompoundTag fluidTag = fluidInTank.getOrCreateTag();
                    List<MobEffectInstance> mobEffects = PotionUtils.getAllEffects(fluidTag);

                    if (!mobEffects.isEmpty()) {
                        fluidHandler.drain(new FluidStack(fluidInTank, portionSize), IFluidHandler.FluidAction.EXECUTE, false);

                        for (MobEffectInstance effectInstance : mobEffects) {
                            MobEffect effect = effectInstance.getEffect();
                            if (effect.isInstantenous()) {
                                effect.applyInstantenousEffect(null, null, player, effectInstance.getAmplifier(), 0.5D);
                            } else {
                                player.addEffect(new MobEffectInstance(effectInstance));
                            }
                        }
                        injected = true;
                        break;
                    }
                }
            }

            if (injected) {
                player.displayClientMessage(Component.literal("Injected potion"), true);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        AllSoundEvents.STEAM.getMainEvent(), SoundSource.PLAYERS, 1.0f, 0.8f);
            } else {
                player.displayClientMessage(Component.literal("No potions available"), true);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        AllSoundEvents.SPOUTING.getMainEvent(), SoundSource.PLAYERS, 1.0f, 2.0f);
            }
        });
    }
}
