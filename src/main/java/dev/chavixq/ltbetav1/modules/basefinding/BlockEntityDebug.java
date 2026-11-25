package dev.chavixq.ltbetav1.modules.basefinding;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.utils.RenderUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import java.util.HashSet;
import java.util.Set;

public class BlockEntityDebug extends Module {
    public static final Set<BlockPos> bes = new HashSet<>();
    private int tick = 0;
    public BlockEntityDebug() { super("BlockEntityDebug", Category.BASE_FINDING); }
    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        if (++tick < 40) return; tick = 0; bes.clear();
        ChunkPos center = mc.player.getChunkPos();
        for (int dx = -5; dx <= 5; dx++) for (int dz = -5; dz <= 5; dz++) {
            WorldChunk chunk = mc.world.getChunkManager().getWorldChunk(center.x + dx, center.z + dz);
            if (chunk == null) continue;
            for (BlockEntity be : chunk.getBlockEntities().values()) bes.add(be.getPos());
        }
    }
    @Override public void onRender(MatrixStack matrices, float tickDelta) {
        for (BlockPos p : bes) RenderUtils.drawBox(matrices, p.getX(), p.getY(), p.getZ(), p.getX()+1, p.getY()+1, p.getZ()+1, 1f, 1f, 1f, 1f, mc);
    }
}