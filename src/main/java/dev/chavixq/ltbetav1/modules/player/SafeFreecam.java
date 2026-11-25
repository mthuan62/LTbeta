package dev.chavixq.ltbetav1.modules.player;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.util.math.Vec3d;
public class SafeFreecam extends Module {
    public static boolean active = false;
    public static Vec3d pos = Vec3d.ZERO;
    public static float yaw = 0, pitch = 0;
    public SafeFreecam() { super("SafeFreecam", Category.PLAYER); }
    @Override public void onEnable() {
        if (mc.player != null) { pos = mc.player.getPos(); yaw = mc.player.getYaw(); pitch = mc.player.getPitch(); active = true; }
    }
    @Override public void onDisable() { active = false; }
    @Override public void onTick() {
        if (!active || mc.player == null || mc.currentScreen != null) return;
        yaw = mc.player.getYaw(); pitch = mc.player.getPitch();
        double speed = mc.options.sprintKey.isPressed() ? 1.5 : 0.5;
        double radYaw = Math.toRadians(yaw); double radPitch = Math.toRadians(pitch);
        Vec3d forward = new Vec3d(-Math.sin(radYaw) * Math.cos(radPitch), -Math.sin(radPitch), Math.cos(radYaw) * Math.cos(radPitch)).multiply(speed);
        Vec3d right = new Vec3d(-Math.cos(radYaw), 0, -Math.sin(radYaw)).multiply(speed);
        if (mc.options.forwardKey.isPressed()) pos = pos.add(forward);
        if (mc.options.backKey.isPressed()) pos = pos.subtract(forward);
        if (mc.options.leftKey.isPressed()) pos = pos.add(right);
        if (mc.options.rightKey.isPressed()) pos = pos.subtract(right);
        if (mc.options.jumpKey.isPressed()) pos = pos.add(0, speed, 0);
        if (mc.options.sneakKey.isPressed()) pos = pos.subtract(0, speed, 0);
    }
}