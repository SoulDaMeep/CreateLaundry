package net.breadwinners.createlaundry.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Unique
    private AABB createLaundry$getModelPartWorldAABB(Player player, ModelPart part) {

        ModelPartAccessor modelPartAccessor = (ModelPartAccessor)(Object)part;
        assert modelPartAccessor != null;
        for(ModelPart.Cube cube : modelPartAccessor.cubes())
        {
            if(cube.minX == part.x && cube.minY == part.y && cube.minZ == part.z)
                return new AABB(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
        }
        return null;
    }

    @Inject(method = "travel", at = @At("HEAD"))
    public void onTravel(Vec3 travelVector, CallbackInfo ci)
    {
        Entity entityCast = (Entity) (Object)this;
        if(!(entityCast instanceof LocalPlayer player)) return;
        Minecraft mc = Minecraft.getInstance();

        PlayerRenderer renderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
        PlayerModel<AbstractClientPlayer> model = renderer.getModel();

        if(player.isInLava()) {
            BlockPos blockpos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
            FluidState fluidstate = player.level().getFluidState(blockpos);
            double d1 = (double)((float)blockpos.getY() + fluidstate.getHeight(player.level(), blockpos));
        }


    }
}
