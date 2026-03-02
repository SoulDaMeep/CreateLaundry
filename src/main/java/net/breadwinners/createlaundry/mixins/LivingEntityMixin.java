package net.breadwinners.createlaundry.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.breadwinners.createlaundry.utils.LaundryStorage;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Unique
    @Final
    private static final float CLEAN_AMOUNT = 0.5f;


    private static AABB cubeToAABB(ModelPart.Cube cube) {
        return new AABB(
                cube.minX / 16f, cube.minY / 16f, cube.minZ / 16f,
                cube.maxX / 16f, cube.maxY / 16f, cube.maxZ / 16f
        );
    }

    @Unique
    private static boolean isPartInLiquid(Player player, Camera camera, AABB box, double offsetY) {
        final Vec3 camPos = camera.getPosition();
        final Vec3 playerPos = player.position();
        BlockPos pos = BlockPos.containing(playerPos.subtract(0, offsetY, 0));
        FluidState fluid = player.level().getFluidState(pos);

        if (!fluid.isEmpty()) {
            double fluidHeight = pos.getY() + fluid.getHeight(player.level(), pos);
            AABB fluidBox = new AABB(
                    pos.getX(), pos.getY(), pos.getZ(),       // min corner
                    pos.getX() + 1.0, fluidHeight, pos.getZ() + 1.0 // max corner
            );
            fluidBox = fluidBox.move(-camPos.x, -camPos.y, -camPos.z);
            if(fluidBox.intersects(box))
            {
                return true;
            }
        }
        return false;
    }


    @Inject(method = "travel", at = @At("HEAD"))
    public void onTravel(Vec3 travelVector, CallbackInfo ci) {

        Entity entity = (Entity)(Object)this;
        if (!(entity instanceof LocalPlayer player)) return;

        Minecraft mc = Minecraft.getInstance();
        PlayerRenderer renderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
        PlayerModel<AbstractClientPlayer> model = renderer.getModel();
        Camera camera = mc.gameRenderer.getMainCamera();;
//        if (player.tickCount % 10 != 0) return;

        if(player.isEyeInFluidType(NeoForgeMod.LAVA_TYPE.value()))
        {
            LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.FEET), CLEAN_AMOUNT);
            LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.LEGS), CLEAN_AMOUNT);
            LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.CHEST), CLEAN_AMOUNT);
            LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.HEAD), CLEAN_AMOUNT);
            LOGGER.info("FULLY SUBMERGED | Skipping Checks");
        }
        else if (player.isInLava()) {
            ModelPartAccessor modelLeg = (ModelPartAccessor)(Object)model.leftLeg;
            ModelPart.Cube legCube = modelLeg.cubes().getFirst();
            ModelPartAccessor modelBody = (ModelPartAccessor)(Object)model.body;
            ModelPart.Cube bodyCube = modelBody.cubes().getFirst();
            ModelPartAccessor modelHead = (ModelPartAccessor)(Object)model.head;
            ModelPart.Cube headCube = modelHead.cubes().getFirst();

            AABB legBox = cubeToAABB(legCube);
            AABB bodyBox = cubeToAABB(bodyCube);
            AABB headBox = cubeToAABB(headCube);

            bodyBox = bodyBox.move(0, legBox.getYsize(), 0);
            headBox = headBox.move(0, legBox.getYsize() + bodyBox.getYsize() + headBox.getYsize(), 0);

            final Vec3 localPos = player.position().subtract(camera.getPosition());

            legBox = legBox.move(localPos);
            bodyBox = bodyBox.move(localPos);
            headBox = headBox.move(localPos);

            if (isPartInLiquid(player, camera, legBox, legBox.getYsize() - bodyBox.getYsize())) {
                LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.FEET), CLEAN_AMOUNT);
                LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.LEGS), CLEAN_AMOUNT);
                System.out.println("Legs cleaned");
            }
            if (isPartInLiquid(player, camera, bodyBox, legBox.getYsize() - bodyBox.getYsize())) {
                LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.CHEST), CLEAN_AMOUNT);
                System.out.println("Chest cleaned");
            }
            // not needed because check if eye height is in fluid
//            if (isPartInLiquid(player, camera, headBox, -player.getEyeHeight())) {
//                LaundryStorage.handleArmorCleaned(player.getItemBySlot(EquipmentSlot.HEAD), 0.1f);
//                System.out.println("Head cleaned");
//            }
        }
    }
}
