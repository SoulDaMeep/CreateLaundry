package net.breadwinners.createlaundry.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.breadwinners.createlaundry.LaundryMod.MODID;

public class LaundryModCreativeTabs {

    // generic tabs register
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
        CREATIVE_MODE_TABS.register("create_laundry_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Create Laundry"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> LaundryModItems.EXAMPLE_ITEM.get().getDefaultInstance())
            .build()
        );
}
