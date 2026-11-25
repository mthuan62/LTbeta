package dev.chavixq.ltbetav1.modules.basefinding;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.utils.RenderUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import java.util.HashSet;
import java.util.Set;

public class LarpChunkFinder extends Module {
    public static final Set<BlockPos> susChunks = new HashSet<>();
    private int tick = 0;
    public LarpChunkFinder() { super("LarpChunkFinder", Category.BASE_FINDING); }
    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        if (++tick < 100) return; tick = 0;
        ChunkPos center = mc.player.getChunkPos();
        for (int dx = -6; dx <= 6; dx++) for (int dz = -6; dz <= 6; dz++) {
            WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
            if (chunk == null) continue;
            boolean found = false;
            ChunkSection[] sections = chunk.getSectionArray();
            for (int i = 0; i < sections.length && !found; i++) {
                ChunkSection section = sections[i];
                if (section == null || section.isEmpty()) continue;
                int bottomY = chunk.sectionIndexToCoord(i);
                if (bottomY > 60) continue;
                for (int x = 0; x < 16 && !found; x++) for (int y = 0; y < 16 && !found; y++) for (int z = 0; z < 16; z++) {
                    var b = section.getBlockState(x, y, z).getBlock();
                    if (b == Blocks.AMETHYST_CLUSTER || b == Blocks.KELP_PLANT || b == Blocks.COBBLESTONE || b == Blocks.OAK_PLANKS) {
                        susChunks.add(new BlockPos(chunk.getPos().getStartX(), 64, chunk.getPos().getStartZ()));
                        found = true; break;
                    }
                }
            }
        }
    }
    @Override public void onRender(MatrixStack matrices, float tickDelta) {
        for (BlockPos p : susChunks) RenderUtils.drawBox(matrices, p.getX(), p.getY()-64, p.getZ(), p.getX()+16, p.getY()+80, p.getZ()+16, 1f, 0.5f, 0f, 1f, mc);
    }
}