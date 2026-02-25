package net.breadwinners.createlaundry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.breadwinners.createlaundry.LaundryMod.MODID;

public class LaundryModBlocks {

    // generic blocks register
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    // register block
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        LaundryModItems.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    // register simple block (ie cobblestone like)
    public static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new Block(properties));
    }

    public static void register(IEventBus bus) {

        BLOCKS.register(bus);
    }


    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = registerSimpleBlock("example_block",
        BlockBehaviour.Properties.of().strength(5f).requiresCorrectToolForDrops().mapColor(MapColor.STONE)
    );
}
