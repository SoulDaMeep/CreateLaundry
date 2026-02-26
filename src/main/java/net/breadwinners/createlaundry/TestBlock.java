package net.breadwinners.createlaundry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlock extends Block {

    public TestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (!level.isClientSide)
            return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.hitResult == null)
            return;

        if (!(mc.hitResult instanceof net.minecraft.world.phys.BlockHitResult hit))
            return;

        if (!hit.getBlockPos().equals(pos))
            return;

        MultiPlayerGameMode gameMode = mc.gameMode;

        if (gameMode == null)
            return;

        if (mc.player != null && mc.player.swinging) {

            for (int i = 0; i < 50; i++) // number of particles
            {
                level.addParticle(
                        ParticleTypes.DRIPPING_WATER,

                        pos.getX() + random.nextDouble(),
                        pos.getY() + random.nextDouble() * 1.5,
                        pos.getZ() + random.nextDouble(),

                        (random.nextDouble() - 0.5) * 0.02,
                        random.nextDouble() * 0.02,
                        (random.nextDouble() - 0.5) * 0.02
                );
            }
        }
    }
}
