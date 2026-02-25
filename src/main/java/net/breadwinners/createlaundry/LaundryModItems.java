package net.breadwinners.createlaundry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.breadwinners.createlaundry.LaundryMod.MODID;

public class LaundryModItems {

    // generic items register
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    // register item
    public static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }

    // register simple item (ie diamond like)
    public static DeferredItem<Item> registerSimpleItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final DeferredItem<Item> EXAMPLE_ITEM = registerSimpleItem("example_item");

//    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
//    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item",
//        new Item.Properties()
//            .food(new FoodProperties
//                .Builder()
//                .alwaysEdible()
//                .nutrition(1)
//                .saturationModifier(2f)
//                .build()
//            )
//    );
}
