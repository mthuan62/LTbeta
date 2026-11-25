package dev.chavixq.ltbetav1.modules.misc;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.item.ItemStack;
public class AutoEat extends Module {
    public AutoEat() { super("AutoEat", Category.MISC); }
    private int oldSlot = -1;
    @Override public void onTick() {
        if (mc.player == null || mc.currentScreen != null) return;
        if (mc.player.getHungerManager().getFoodLevel() >= 18) {
            if (oldSlot != -1) { mc.player.getInventory().selectedSlot = oldSlot; oldSlot = -1; mc.options.useKey.setPressed(false); }
            return;
        }
        if (mc.player.isUsingItem()) return;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem().isFood()) {
                if (oldSlot == -1) oldSlot = mc.player.getInventory().selectedSlot;
                mc.player.getInventory().selectedSlot = i; mc.options.useKey.setPressed(true); return;
            }
        }
    }
}