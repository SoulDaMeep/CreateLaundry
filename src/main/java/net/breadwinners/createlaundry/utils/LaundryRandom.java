package net.breadwinners.createlaundry.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ArmorItem;

public class LaundryRandom {
    // LaundryMod will use this for majority of its randomness
    // RandomSource.nextFloat() 0.0 -> 1.0
    public static final RandomSource randomSource = RandomSource.create();

    public static float GetRandomArmorDirt(float damage, float toughness)
    {
        return damage * (randomSource.nextFloat() / (1.0f + toughness));
    }




}
