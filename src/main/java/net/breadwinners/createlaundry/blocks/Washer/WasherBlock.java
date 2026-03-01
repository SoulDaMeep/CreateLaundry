package net.breadwinners.createlaundry.blocks.Washer;

import net.breadwinners.createlaundry.utils.LaundryRotatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.EntityBlock;

import org.jetbrains.annotations.Nullable;

public class WasherBlock extends LaundryRotatableBlock {

    public WasherBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.5F)
                .requiresCorrectToolForDrops());
    }

//    // Called when placed or neighbor updates
//    @Override
//    public void neighborChanged(BlockState state, Level level, BlockPos pos,
//                                Block block, BlockPos fromPos, boolean moving) {
//
//        if (!level.isClientSide) {
//            boolean powered = level.getBestNeighborSignal(pos) > 0;
//
//            if (state.getValue(ACTIVE) != powered) {
//                level.setBlock(pos, state.setValue(ACTIVE, powered), 3);
//            }
//        }
//    }

    // BlockEntity creation
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WasherBlockEntity(pos, state);
    }
}