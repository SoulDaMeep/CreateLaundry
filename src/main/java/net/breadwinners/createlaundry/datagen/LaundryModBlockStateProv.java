package net.breadwinners.createlaundry.datagen;

import java.util.ArrayList;

import net.breadwinners.createlaundry.blocks.Washer.WasherBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.init.LaundryModBlocks;

public class LaundryModBlockStateProv extends BlockStateProvider {

    private ArrayList<String> EXCLUDED_BLOCKS = new ArrayList<>();

    public LaundryModBlockStateProv(PackOutput packOutput, ExistingFileHelper efh) {
        super(packOutput, LaundryMod.MODID, efh);
    }

    @Override
    protected void registerStatesAndModels() {
        EXCLUDED_BLOCKS.add("washer");
        LaundryModBlocks.BLOCKS.getEntries().forEach(block -> {
            if (!EXCLUDED_BLOCKS.contains(block.getId().getPath())) {
                simpleBlock(block.get());
            }
        });

        washerBlock(LaundryModBlocks.WASHER.get());
    }

    private void washerBlock(Block block) {

        ResourceLocation def = modLoc("block/washer/washer_def");
        ResourceLocation fluid = modLoc("block/washer/washer_fluid");
        ResourceLocation rotener = modLoc("block/washer/washer_rotener");
        ResourceLocation front = modLoc("block/washer/washer_front");

        BlockModelBuilder model = models().cube(
                "washer",
                fluid,
                fluid,
                front,
                rotener,
                def,
                def
        );

        getVariantBuilder(block).forAllStates(state -> {

            Direction facing = state.getValue(WasherBlock.FACING);

            int yRot = switch (facing) {
                case NORTH -> 0;
                case EAST  -> 90;
                case SOUTH -> 180;
                case WEST  -> 270;
                default -> 0;
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(yRot)
                    .build();
        });

        simpleBlockItem(block, model);
    }
}
