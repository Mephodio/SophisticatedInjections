package pm.meh.sophisticatedinjections.upgrades.injection;

import com.simibubi.create.AllSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import org.jetbrains.annotations.Nullable;
import pm.meh.sophisticatedinjections.util.PotionHelper;

import java.util.List;
import java.util.function.Consumer;

public class InjectionUpgradeWrapper extends UpgradeWrapperBase<InjectionUpgradeWrapper, InjectionUpgradeItem>
        implements ITickableUpgrade {

    private static final int INJECTION_DELAY = 20;

    long scheduledInjectionTime = 0;
    List<MobEffectInstance> scheduledEffects = null;

    protected InjectionUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
        super(backpackWrapper, upgrade, upgradeSaveHandler);
    }

    @Override
    public boolean canBeDisabled() {
        return false;
    }

    @Override
    public void tick(@Nullable LivingEntity entity, Level level, BlockPos blockPos) {
        if (scheduledInjectionTime > 0 && scheduledInjectionTime <= level.getGameTime()) {
            for (MobEffectInstance effectInstance : scheduledEffects) {
                MobEffect effect = effectInstance.getEffect();
                if (effect.isInstantenous()) {
                    effect.applyInstantenousEffect(null, null, entity, effectInstance.getAmplifier(), 0.5D);
                } else {
                    entity.addEffect(new MobEffectInstance(effectInstance));
                }
            }

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    AllSoundEvents.STEAM.getMainEvent(), SoundSource.PLAYERS, 1.0f, 0.8f);

            scheduledInjectionTime = 0;
            scheduledEffects = null;
        }
    }

    public void injectIntoPlayer(Player player) {

        if (scheduledInjectionTime > 0) {
            return;
        }

        storageWrapper.getFluidHandler().ifPresent(fluidHandler -> {
            int portionSize = PotionHelper.getBottleAmount();
            boolean injected = false;

            for (int tank = 0; tank < fluidHandler.getTanks(); tank++) {
                FluidStack fluidInTank = fluidHandler.getFluidInTank(tank);

                if (fluidInTank.getAmount() >= portionSize) {
                    CompoundTag fluidTag = fluidInTank.getOrCreateTag();
                    scheduledEffects = PotionUtils.getAllEffects(fluidTag);

                    if (!scheduledEffects.isEmpty()) {
                        fluidHandler.drain(new FluidStack(fluidInTank, portionSize), IFluidHandler.FluidAction.EXECUTE, false);
                        injected = true;
                        scheduledInjectionTime = player.level().getGameTime() + INJECTION_DELAY;
                        break;
                    }
                }
            }

            String msg = injected ? "Injecting potion" : "No potions available";
            player.displayClientMessage(Component.literal(msg), true);

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    injected ? SoundEvents.BOTTLE_FILL : AllSoundEvents.SPOUTING.getMainEvent(),
                    SoundSource.PLAYERS, 1.0f, 2.0f);
        });
    }
}
