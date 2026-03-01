package net.breadwinners.createlaundry.Events;

import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.utils.LaundryRandom;
import net.breadwinners.createlaundry.utils.LaundryStorage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid=LaundryMod.MODID)
public class EntityHurtHandler {
    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event)
    {

        if(!(event.getEntity() instanceof Player player)) return;

        // newDamage is the damage subtracted from the health (final player hurt damage)
        // newDamage includes blocked damage, shielded damage, and absorbtion
        final float finalDamage = event.getNewDamage();

        if(isFallDamage(event.getSource()))
        {
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            LaundryStorage.handleArmorDirtied(boots, finalDamage);
            return;
        }

        Iterable<ItemStack> armorSlots = player.getArmorSlots();
        for(ItemStack item : armorSlots)
        {
            LaundryStorage.handleArmorDirtied(item, finalDamage);
        }

        // Spider, Zombie, fall, drown, etc
        LaundryMod.LOGGER.debug(event.getSource().toString());
    }

    private static boolean isFallDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.FALL);
    }
}
