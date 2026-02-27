package net.breadwinners.createlaundry;

import com.mojang.logging.LogUtils;
import net.breadwinners.createlaundry.init.LaundryModBlockEntities;
import net.breadwinners.createlaundry.init.LaundryModBlocks;
import net.breadwinners.createlaundry.init.LaundryModCreativeTabs;
import net.breadwinners.createlaundry.init.LaundryModItems;
import net.breadwinners.createlaundry.utils.LaundryStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.objectweb.asm.tree.analysis.Value;
import org.slf4j.Logger;

import java.util.List;

/*



    Player Dirtyness working

      Applied on armor!!!!

      Effects ->
    - Slow
    - Weakness

      How it builds ->
    - Mobs hitting player
    - Players hitting players
    - fall damage

      Types ->
    - Cracks
    - Dirt

    Player Dirtyness effects bed dirtyness everytime you sleep




    Boring stuff
    - textures

 */



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

    // TODO: Abstract...
    @SubscribeEvent
    void onEntityHurt(LivingDamageEvent.Post event)
    {
        if(!(event.getEntity() instanceof Player player)) return;
        if(event.getSource().getEntity() != null)
        {
            // the 4 primary slots
            Iterable<ItemStack> armorslots = player.getArmorSlots();
            for(ItemStack item : armorslots)
            {

                // redundant unless playing modded game
                if(!(item.getItem() instanceof ArmorItem armor)) continue;

                // TODO: Make it random based on damage took and defense of the armor.
                // Also maybe try looking at ArmorHurtEvent? sounds useful...
                final float dirtValue = LaundryStorage.getData(item, LaundryStorage.CMP_DIRT_VALUE, 0.0f) + Minecraft.getInstance().level.random.nextFloat() * 0.1f;
                LaundryStorage.setData(item, LaundryStorage.CMP_DIRT_VALUE, dirtValue);
            }
        }
        // Spider, Zombie, fall, drown, etc
        LOGGER.info(event.getSource().toString());
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
