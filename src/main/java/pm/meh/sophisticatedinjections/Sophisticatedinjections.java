package pm.meh.sophisticatedinjections;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeGuiManager;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerRegistry;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import org.slf4j.Logger;
import pm.meh.sophisticatedinjections.upgrades.injection.InjectionUpgradeContainer;
import pm.meh.sophisticatedinjections.upgrades.injection.InjectionUpgradeItem;
import pm.meh.sophisticatedinjections.upgrades.injection.InjectionUpgradeTab;
import pm.meh.sophisticatedinjections.upgrades.injection.InjectionUpgradeWrapper;

@Mod(Sophisticatedinjections.MOD_ID)
public class Sophisticatedinjections {

    public static final String MOD_ID = "sophisticatedinjections";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<InjectionUpgradeItem> INJECTION_UPGRADE = ITEMS.register("injection_upgrade",
            InjectionUpgradeItem::new);

    private static final UpgradeContainerType<InjectionUpgradeWrapper, InjectionUpgradeContainer> INJECTION_TYPE = new UpgradeContainerType<>(InjectionUpgradeContainer::new);

    public Sophisticatedinjections() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::registerContainers);
        modEventBus.addListener(this::addCreative);
        ITEMS.register(modEventBus);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModItems.CREATIVE_TAB.getKey()) event.accept(INJECTION_UPGRADE);
    }

    private void registerContainers(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.MENU_TYPES)) {
            return;
        }

        UpgradeContainerRegistry.register(INJECTION_UPGRADE.getId(), INJECTION_TYPE);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            UpgradeGuiManager.registerTab(INJECTION_TYPE, InjectionUpgradeTab::new);
        });
    }
}
