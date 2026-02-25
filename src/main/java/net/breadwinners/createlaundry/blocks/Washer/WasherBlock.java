package net.breadwinners.createlaundry.blocks.Washer;

import net.breadwinners.createlaundry.init.LaundryModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//public class WasherBlock extends BaseEntityBlock {
//
//    public WasherBlock(Properties properties) {
//        super(properties);
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new WasherBlockEntity(pos, state);
//    }
//
//    // Only needed if it ticks
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
//            Level level,
//            BlockState state,
//            BlockEntityType<T> type
//    ) {
//        return type == LaundryModBlockEntities.WASHER_BLOCK_ENTITY.get()
//                ? (lvl, pos, st, be) -> ((WasherBlockEntity) be).tick()
//                : null;
//    }
//}