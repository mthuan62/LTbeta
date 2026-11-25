package dev.chavixq.ltbetav1.modules.render;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
public class PlayerESP extends Module {
    public PlayerESP() { super("PlayerESP", Category.RENDER); }
    @Override public void onRender(MatrixStack matrices, float tickDelta) {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            var b = p.getBoundingBox().expand(0.1);
            RenderUtils.drawBox(matrices, (float)b.minX, (float)b.minY, (float)b.minZ, (float)b.maxX, (float)b.maxY, (float)b.maxZ, 1f, 0f, 1f, 1f, mc);
        }
    }
}