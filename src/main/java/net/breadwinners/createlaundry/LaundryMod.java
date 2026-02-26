package net.breadwinners.createlaundry;

import com.mojang.logging.LogUtils;
import net.breadwinners.createlaundry.init.LaundryModBlockEntities;
import net.breadwinners.createlaundry.init.LaundryModBlocks;
import net.breadwinners.createlaundry.init.LaundryModCreativeTabs;
import net.breadwinners.createlaundry.init.LaundryModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(LaundryMod.MODID)
public class LaundryMod {
    public static final String MODID = "createlaundry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LaundryMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        LaundryModBlockEntities.register(modEventBus);
        LaundryModBlocks.register(modEventBus);
        LaundryModItems.register(modEventBus);
        LaundryModCreativeTabs.register(modEventBus);

        // register ourselves
        NeoForge.EVENT_BUS.register(this);

        // register to get creative tab open events
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == LaundryModCreativeTabs.MAIN_TAB.get()) {
            LaundryModItems.ITEMS.getEntries().forEach(item -> {
                event.accept(item.get());
            });
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
//        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//        }
//
//        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
//
//        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }
}
