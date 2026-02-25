package net.breadwinners.createlaundry.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.breadwinners.createlaundry.LaundryMod;

@EventBusSubscriber(modid=LaundryMod.MODID)
public class LaundryModDataGens {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new LaundryModBlockStateProv(packOutput, efh));
        gen.addProvider(event.includeClient(), new LaundryModItemModelProv(packOutput, efh));
        gen.addProvider(event.includeClient(), new LaundryModLangProv(packOutput));
    }
}
