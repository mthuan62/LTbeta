package dev.chavixq.ltbetav1.modules.basefinding;

import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.core.ModuleManager;
import dev.chavixq.ltbetav1.utils.RenderUtils;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import java.util.HashMap;
import java.util.Map;

public class StashCache extends Module {
    public static final Map<BlockPos, Integer> cache = new HashMap<>();
    public double maxDistance = 200.0; 
    private int tick = 0;

    public StashCache() { super("StashCache", Category.BASE_FINDING); }

    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        if (++tick < 20) return; tick = 0;
        ChunkPos center = mc.player.getChunkPos();
        for (int dx = -6; dx <= 6; dx++) for (int dz = -6; dz <= 6; dz++) {
            WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
            if (chunk == null) continue;
            for (Map.Entry<BlockPos, BlockEntity> e : chunk.getBlockEntities().entrySet()) {
                var b = e.getValue().getCachedState().getBlock();
                int score = 0;
                if (b == Blocks.CHEST || b == Blocks.BARREL) score = 1;
                else if (b == Blocks.ENDER_CHEST || b.getTranslationKey().contains("shulker_box")) score = 5;
                else if (b == Blocks.BEACON || b == Blocks.LODESTONE) score = 10;
                if (score > 0) cache.putIfAbsent(e.getKey(), score);
            }
        }
    }

    @Override public void onRender(MatrixStack matrices, float tickDelta) {
        if (mc.player == null || cache.isEmpty()) return;
        Vec3d playerPos = mc.player.getPos();
        double maxDistSq = maxDistance * maxDistance;

        for (Map.Entry<BlockPos, Integer> entry : cache.entrySet()) {
            BlockPos p = entry.getKey();
            if (playerPos.squaredDistanceTo(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5) > maxDistSq) continue;

            int s = entry.getValue();
            float r = s >= 5 ? 1f : 0f;
            float g = s >= 5 ? 0.2f : 1f;
            float b = s >= 5 ? 0.2f : 1f;
            RenderUtils.drawBox(matrices, p.getX(), p.getY(), p.getZ(), p.getX()+1, p.getY()+1, p.getZ()+1, r, g, b, 1f, mc);
            
            boolean isTracerActive = ModuleManager.INSTANCE.modules.stream().anyMatch(m -> m.getName().equals("Tracers") && m.isEnabled());
            if (isTracerActive) {
                RenderUtils.drawTracer(matrices, new Vec3d(p.getX()+0.5, p.getY()+0.5, p.getZ()+0.5), r, g, b, 1f, mc);
            }
        }
    }

    @Override public void onRenderHUD(DrawContext context, RenderTickCounter tickCounter) {
        if (mc.player == null || cache.isEmpty()) return;
        Vec3d playerPos = mc.player.getPos();
        double maxDistSq = maxDistance * maxDistance;
        int y = 50;

        for (Map.Entry<BlockPos, Integer> entry : cache.entrySet()) {
            BlockPos p = entry.getKey();
            if (playerPos.squaredDistanceTo(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5) > maxDistSq) continue;
            if (y > context.getScaledWindowHeight() - 20) break;
            
            String text = String.format("%d, %d, %d | %s", p.getX(), p.getY(), p.getZ(), entry.getValue() >= 5 ? "Shulker/Ender" : "Chest");
            context.drawTextWithShadow(mc.textRenderer, text, 4, y, entry.getValue() >= 5 ? 0xFFFF5555 : 0xFF55FFFF);
            y += 10;
        }
    }
}