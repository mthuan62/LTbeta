package dev.chavixq.ltbetav1.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderUtils {
    public static final RenderLayer LINES_LAYER = RenderLayer.of(
            "lt_lines",
            RenderLayer.MultiPhaseParameters.builder()
                    .pipeline(RenderPipelines.LINES)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .build(false)
    );

    public static void drawBox(MatrixStack matrices, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a, MinecraftClient mc) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        matrices.push();
        matrices.translate(-cam.x, -cam.y, -cam.z);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        VertexConsumerProvider.Immediate consumers = mc.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vc = consumers.getBuffer(LINES_LAYER);

        line(vc, mat, x1, y1, z1, x2, y1, z1, r, g, b, a); line(vc, mat, x2, y1, z1, x2, y1, z2, r, g, b, a);
        line(vc, mat, x2, y1, z2, x1, y1, z2, r, g, b, a); line(vc, mat, x1, y1, z2, x1, y1, z1, r, g, b, a);
        line(vc, mat, x1, y2, z1, x2, y2, z1, r, g, b, a); line(vc, mat, x2, y2, z1, x2, y2, z2, r, g, b, a);
        line(vc, mat, x2, y2, z2, x1, y2, z2, r, g, b, a); line(vc, mat, x1, y2, z2, x1, y2, z1, r, g, b, a);
        line(vc, mat, x1, y1, z1, x1, y2, z1, r, g, b, a); line(vc, mat, x2, y1, z1, x2, y2, z1, r, g, b, a);
        line(vc, mat, x2, y1, z2, x2, y2, z2, r, g, b, a); line(vc, mat, x1, y1, z2, x1, y2, z2, r, g, b, a);

        consumers.draw(LINES_LAYER);
        matrices.pop();
    }

    public static void drawTracer(MatrixStack matrices, Vec3d target, float r, float g, float b, float a, MinecraftClient mc) {
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        Vec3d look = Vec3d.fromPolar(mc.gameRenderer.getCamera().getPitch(), mc.gameRenderer.getCamera().getYaw()).multiply(2.0);
        Vec3d start = cam.add(look);
        
        matrices.push();
        matrices.translate(-cam.x, -cam.y, -cam.z);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        VertexConsumerProvider.Immediate consumers = mc.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vc = consumers.getBuffer(LINES_LAYER);
        
        line(vc, mat, (float)start.x, (float)start.y, (float)start.z, (float)target.x, (float)target.y, (float)target.z, r, g, b, a);
        
        consumers.draw(LINES_LAYER);
        matrices.pop();
    }

    private static void line(VertexConsumer vc, Matrix4f mat, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
        vc.vertex(mat, x1, y1, z1).color(r, g, b, a);
        vc.vertex(mat, x2, y2, z2).color(r, g, b, a);
    }
}