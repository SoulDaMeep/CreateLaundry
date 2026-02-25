package net.breadwinners.createlaundry.Events;


import net.breadwinners.createlaundry.LaundryMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@EventBusSubscriber(modid=LaundryMod.MODID)
public class PlayerSleepHandler {

    @SubscribeEvent
    public static void onSleepFinished(PlayerWakeUpEvent event) {

        Player player = event.getEntity();
        Level level = player.level();
        Optional<BlockPos> optionalBlockPos = event.getEntity().getSleepingPos();
        if (optionalBlockPos.isPresent()) {
            BlockPos pos = optionalBlockPos.get();
            BlockState blockstate = level.getBlockState(pos);

            Block block = blockstate.getBlock();

            if(block instanceof BedBlock bed)
            {
                System.out.println(bed.getColor());
            }
        }

    }

}
