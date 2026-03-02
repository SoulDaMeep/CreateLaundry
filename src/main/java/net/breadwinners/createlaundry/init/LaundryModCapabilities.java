package net.breadwinners.createlaundry.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class LaundryModCapabilities {

    public static void register(IEventBus modEventBus) {
        // Hook into the mod event bus manually
        modEventBus.register(LaundryModCapabilities.class);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                LaundryModBlockEntities.WASHER.get(),
                (be, side) -> be.getItemHandler()
        );

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                LaundryModBlockEntities.WASHER.get(),
                (be, side) -> be.getFluidHandler()
        );
    }
}
