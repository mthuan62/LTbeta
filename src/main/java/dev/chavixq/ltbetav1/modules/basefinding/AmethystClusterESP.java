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

public class AmethystClusterESP extends Module {
    public static final Set<BlockPos> amethyst = new HashSet<>();
    private int tick = 0;
    public AmethystClusterESP() { super("AmethystESP", Category.BASE_FINDING); }
    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        if (++tick < 60) return; tick = 0; amethyst.clear();
        ChunkPos center = mc.player.getChunkPos();
        for (int dx = -4; dx <= 4; dx++) for (int dz = -4; dz <= 4; dz++) {
            WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
            if (chunk == null) continue;
            ChunkSection[] sections = chunk.getSectionArray();
            for (int i = 0; i < sections.length; i++) {
                ChunkSection section = sections[i];
                if (section == null || section.isEmpty()) continue;
                int bottomY = chunk.sectionIndexToCoord(i);
                if (bottomY > 60) continue;
                for (int x = 0; x < 16; x++) for (int y = 0; y < 16; y++) for (int z = 0; z < 16; z++) {
                    var b = section.getBlockState(x, y, z).getBlock();
                    if (b == Blocks.BUDDING_AMETHYST || b == Blocks.AMETHYST_CLUSTER) {
                        amethyst.add(new BlockPos(chunk.getPos().getStartX() + x, bottomY + y, chunk.getPos().getStartZ() + z));
                    }
                }
            }
        }
    }
    @Override public void onRender(MatrixStack matrices, float tickDelta) {
        for (BlockPos p : amethyst) RenderUtils.drawBox(matrices, p.getX(), p.getY(), p.getZ(), p.getX()+1, p.getY()+1, p.getZ()+1, 0.5f, 0f, 1f, 1f, mc);
    }
}