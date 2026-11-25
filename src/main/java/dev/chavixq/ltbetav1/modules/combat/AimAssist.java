package dev.chavixq.ltbetav1.modules.combat;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.entity.player.PlayerEntity;
public class AimAssist extends Module {
    public AimAssist() { super("AimAssist", Category.COMBAT); }
    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        PlayerEntity closest = null; double dist = 6.0;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            double d = p.distanceTo(mc.player);
            if (d < dist) { dist = d; closest = p; }
        }
        if (closest != null) {
            double dx = closest.getX() - mc.player.getX(); double dz = closest.getZ() - mc.player.getZ();
            double dy = closest.getEyeY() - mc.player.getEyeY(); double distXZ = Math.sqrt(dx * dx + dz * dz);
            mc.player.setYaw((float)(Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0f);
            mc.player.setPitch((float)(-(Math.atan2(dy, distXZ) * 180.0 / Math.PI)));
        }
    }
}