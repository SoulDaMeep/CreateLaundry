package net.breadwinners.createlaundry.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.init.LaundryModBlocks;

public class LaundryModBlockStateProv extends BlockStateProvider {

    public LaundryModBlockStateProv(PackOutput packOutput, ExistingFileHelper efh) {
        super(packOutput, LaundryMod.MODID, efh);
    }

    @Override
    protected void registerStatesAndModels() {
        LaundryModBlocks.BLOCKS.getEntries().forEach(block -> {
            simpleBlock(block.get());
        });
    }
}
