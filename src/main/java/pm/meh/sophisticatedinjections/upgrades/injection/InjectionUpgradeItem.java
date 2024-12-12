package pm.meh.sophisticatedinjections.upgrades.injection;

import net.minecraft.resources.ResourceLocation;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InjectionUpgradeItem extends UpgradeItemBase<InjectionUpgradeWrapper> {

    private static final UpgradeType<InjectionUpgradeWrapper> TYPE = new UpgradeType(InjectionUpgradeWrapper::new);

    public InjectionUpgradeItem() {
        super(new IUpgradeCountLimitConfig() {
            @Override
            public int getMaxUpgradesPerStorage(String s, @Nullable ResourceLocation resourceLocation) {
                return Integer.MAX_VALUE;
            }

            @Override
            public int getMaxUpgradesInGroupPerStorage(String s, UpgradeGroup upgradeGroup) {
                return Integer.MAX_VALUE;
            }
        });
    }

    @Override
    public UpgradeType<InjectionUpgradeWrapper> getType() {
        return TYPE;
    }

    @Override
    public List<UpgradeConflictDefinition> getUpgradeConflicts() {
        return List.of();
    }
}
