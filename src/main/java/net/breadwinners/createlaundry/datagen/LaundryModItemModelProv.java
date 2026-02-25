package net.breadwinners.createlaundry.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.LaundryModItems;

public class LaundryModItemModelProv extends ItemModelProvider {

    public LaundryModItemModelProv(PackOutput packOutput, ExistingFileHelper efh) {
        super(packOutput, LaundryMod.MODID, efh);
    }

    @Override
    protected void registerModels() {
        LaundryModItems.ITEMS.getEntries().forEach(item -> {
            basicItem(item.get());
        });
    }
}
