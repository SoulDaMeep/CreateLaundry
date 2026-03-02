package net.breadwinners.createlaundry.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.breadwinners.createlaundry.LaundryMod.MODID;
//import net.breadwinners.createlaundry.blocks.Washer.WasherBlockEntity;

public class LaundryModBlockEntities {

    // generic block entities register
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    // register the block entities
    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerBlockEntity(
            String name,
            BlockEntityType.BlockEntitySupplier<T> factory,
            Supplier<? extends Block> block
    ) {
        return BLOCK_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(factory, block.get()).build(null)
        );
    }

//    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WasherBlockEntity>> WASHER_BLOCK_ENTITY = registerBlockEntity(
//  "washer",
//        WasherBlockEntity::new,
//        LaundryModBlocks.WASHER_BLOCK
//    );
}
