package dev.chavixq.ltbetav1.modules.combat;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
public class AutoClicker extends Module {
    public AutoClicker() { super("AutoClicker", Category.COMBAT); }
    private int delay = 0;
    @Override public void onTick() {
        if (mc.player == null || mc.interactionManager == null || mc.currentScreen != null) return;
        if (mc.options.attackKey.isPressed()) {
            if (delay > 0) { delay--; return; } delay = 2 + (int)(Math.random() * 3);
            if (mc.crosshairTarget != null) {
                if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult ehr = (EntityHitResult) mc.crosshairTarget;
                    mc.interactionManager.attackEntity(mc.player, ehr.getEntity()); mc.player.swingHand(Hand.MAIN_HAND);
                } else if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult bhr = (BlockHitResult) mc.crosshairTarget;
                    mc.interactionManager.attackBlock(bhr.getBlockPos(), bhr.getSide()); mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}