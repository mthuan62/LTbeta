package dev.chavixq.ltbetav1.modules.combat;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.Hand;
public class TriggerBot extends Module {
    public TriggerBot() { super("TriggerBot", Category.COMBAT); }
    @Override public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.player.getAttackCooldownProgress(0.0F) >= 1.0F) {
            if (mc.crosshairTarget instanceof EntityHitResult hit) {
                Entity target = hit.getEntity();
                if (target != null && target.isAlive()) {
                    mc.interactionManager.attackEntity(mc.player, target); mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}