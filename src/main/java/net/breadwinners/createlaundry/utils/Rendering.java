package net.breadwinners.createlaundry.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import javax.swing.*;

public class Rendering {
    public static void DrawFilledBoxBehind(
            PoseStack matrices,
            VertexConsumer vertexConsumers,
            Matrix4f positionMatrix,
            AABB box,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        // Extract min/max from the AABB
        float minX = (float) box.minX;
        float minY = (float) box.minY;
        float minZ = (float) box.minZ;
        float maxX = (float) box.maxX;
        float maxY = (float) box.maxY;
        float maxZ = (float) box.maxZ;
        DrawFilledBoxBehind(matrices, vertexConsumers, positionMatrix, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
    }
    public static void DrawFilledBoxBehind(
            PoseStack matrices,
            VertexConsumer vertexConsumers,
            Matrix4f positionMatrix,
            float minX,
            float minY,
            float minZ,
            float maxX,
            float maxY,
            float maxZ,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        //vertexConsumers.addVertex(matrix4f, minX, minY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

        vertexConsumers.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
        vertexConsumers.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
        //vertexConsumers.addVertex(matrix4f, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
    }
}
