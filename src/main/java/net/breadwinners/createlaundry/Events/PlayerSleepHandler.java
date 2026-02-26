package net.breadwinners.createlaundry.Events;


import net.breadwinners.createlaundry.LaundryMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import java.util.Optional;

import static net.breadwinners.createlaundry.utils.LaundryStorage.*;

@EventBusSubscriber(modid=LaundryMod.MODID)
public class PlayerSleepHandler {

    @SubscribeEvent
    public static void onSleepFinished(PlayerWakeUpEvent event) {

        Player player = event.getEntity();
        Level level = player.level();
        // might change this to client level? idk...
        if(level instanceof ClientLevel)
            return;

        Optional<BlockPos> optionalBlockPos = event.getEntity().getSleepingPos();
        if (optionalBlockPos.isPresent()) {
            final RandomSource randomSource = level.random; // levels random gen
            BlockPos pos = optionalBlockPos.get();
            BlockState blockstate = level.getBlockState(pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof BedBlockEntity bedBlockEntity)
            {
                final float newValue = level.random.nextFloat() * 0.1f;
                final float current = getData(bedBlockEntity, DIRTYVALUE);
                final float dirtyness = current + newValue;

                setData(bedBlockEntity, DIRTYVALUE, dirtyness);
            }
        }
    }
}
