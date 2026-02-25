package net.breadwinners.createlaundry.datagen;

import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.LaundryModBlocks;
import net.breadwinners.createlaundry.LaundryModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LaundryModLangProv extends LanguageProvider {

    public LaundryModLangProv(PackOutput packOutput) {
        super(packOutput, LaundryMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

//        LaundryModBlocks.BLOCKS.getEntries().forEach(block ->
//                add(block.get(), formatName(block.getId().getPath())));

        LaundryModItems.ITEMS.getEntries().forEach(item ->
                add(item.get(), formatName(item.getId().getPath())));
    }

    private String formatName(String name) {
        return java.util.Arrays.stream(name.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .reduce((a, b) -> a + " " + b)
                .orElse(name);
    }
}
