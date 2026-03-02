package net.breadwinners.createlaundry.Events;


import com.mojang.blaze3d.vertex.PoseStack;
import net.breadwinners.createlaundry.LaundryMod;
import net.breadwinners.createlaundry.mixins.ModelPartAccessor;
import net.breadwinners.createlaundry.mixins.PartDefinitionAccessor;
import net.breadwinners.createlaundry.utils.Rendering;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;

import java.awt.*;
import java.util.Optional;

import static net.breadwinners.createlaundry.utils.LaundryStorage.*;
@EventBusSubscriber(modid=LaundryMod.MODID)
public class RenderEvent {

    private static AABB cubeToAABB(ModelPart.Cube cube) {
        return new AABB(
                cube.minX / 16f, cube.minY / 16f, cube.minZ / 16f,
                cube.maxX / 16f, cube.maxY / 16f, cube.maxZ / 16f
        );
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        LocalPlayer player = mc.player;

        PoseStack matrices = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

        Vec3 camPos = event.getCamera().getPosition();
        Vec3 playerPos = player.position();

        PlayerRenderer renderer =
                (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);

        PlayerModel<AbstractClientPlayer> model = renderer.getModel();

        float partialTick = event.getPartialTick().getRealtimeDeltaTicks();

        ModelPartAccessor modelLeg = (ModelPartAccessor)(Object)model.body;
        ModelPart.Cube legCube = modelLeg.cubes().getFirst();
        ModelPartAccessor modelBody = (ModelPartAccessor)(Object)model.body;
        ModelPart.Cube bodyCube = modelBody.cubes().getFirst();
        ModelPartAccessor modelHead = (ModelPartAccessor)(Object)model.head;
        ModelPart.Cube headCube = modelHead.cubes().getFirst();

        AABB legBox = cubeToAABB(legCube);
        AABB bodyBox = cubeToAABB(bodyCube);
        AABB headBox = cubeToAABB(headCube);
        // Offset body by leg height
        bodyBox = bodyBox.move(0, legBox.getYsize(), 0);
        headBox = headBox.move(0, legBox.getYsize() + bodyBox.getYsize() + headBox.getYsize(), 0);

        final Vec3 localPos = playerPos.subtract(camPos);

        legBox = legBox.move(localPos);
        bodyBox = bodyBox.move(localPos);
        headBox = headBox.move(localPos);

        BlockPos pos = BlockPos.containing(playerPos.subtract(0, legBox.getYsize() - bodyBox.getYsize(), 0));
        FluidState fluid = player.level().getFluidState(pos);

        if (!fluid.isEmpty()) {
            double fluidHeight = pos.getY() + fluid.getHeight(player.level(), pos);
            AABB fluidBox = new AABB(
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1.0, fluidHeight, pos.getZ() + 1.0
            );
            fluidBox = fluidBox.move(-camPos.x, -camPos.y, -camPos.z);
            Color color = new Color(255, 0, 0, 255/2);
            if(fluidBox.intersects(headBox))
                color = new Color(0, 255, 0, 255/2);
            DebugRenderer.renderFilledBox(matrices, buffer, fluidBox, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        }

        DebugRenderer.renderFilledBox(matrices, buffer, legBox, 0f, 1f, 0f, 0.25f);
        DebugRenderer.renderFilledBox(matrices, buffer, bodyBox, 0f, 0f, 1f, 0.25f);
        DebugRenderer.renderFilledBox(matrices, buffer, headBox, 1f, 0f, 0f, 0.25f);

//        matrices.pushPose();
//
//        matrices.translate(
//                playerPos.x - camPos.x,
//                playerPos.y - camPos.y + 1.4f,
//                playerPos.z - camPos.z
//        );
//
//        matrices.mulPose(new Quaternionf().rotationY(
//                (float) Math.toRadians(-bodyYaw)
//        ));

//        renderModelPart(matrices, buffer, model.head, 1f, 0f, 0f, 0.25f);
//        renderModelPart(matrices, buffer, model.body, 1f, 0f, 0f, 0.25f);
//        renderModelPart(matrices, buffer, model.leftLeg, 1f, 0f, 0f, 0.25f);
//        renderModelPart(matrices, buffer, model.rightLeg, 1f, 0f, 0f, 0.25f);
//        renderModelPart(matrices, buffer, model.leftArm, 1f, 0f, 0f, 0.25f);
//        renderModelPart(matrices, buffer, model.rightArm, 1f, 0f, 0f, 0.25f);

        buffer.endBatch();
    }
    private static void renderModelPart(
            PoseStack poseStack,
            MultiBufferSource buffer,
            ModelPart part,
            float r, float g, float b, float a
    ) {
        ModelPartAccessor modelPartAccessor = (ModelPartAccessor)(Object)part;
        poseStack.pushPose();
        poseStack.scale(1.0f, -1.0f, -1.0f);
        part.translateAndRotate(poseStack);

        for (ModelPart.Cube cube : modelPartAccessor.cubes()) {
            AABB box = new AABB(
                    cube.minX / 16f,
                    cube.minY / 16f,
                    cube.minZ / 16f,
                    cube.maxX / 16f,
                    cube.maxY / 16f,
                    cube.maxZ / 16f
            );

            DebugRenderer.renderFilledBox(
                    poseStack,
                    buffer,
                    box,
                    r, g, b, a
            );
        }

        // Recursively render children
        for (ModelPart child : modelPartAccessor.children().values()) {
            renderModelPart(poseStack, buffer, child, r, g, b, a);
        }

        poseStack.popPose();
    }
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
                final float current = getData(bedBlockEntity, ATT_DIRT_VALUE);
                final float dirtyness = current + newValue;

                setData(bedBlockEntity, ATT_DIRT_VALUE, dirtyness);
            }
        }
    }
}
