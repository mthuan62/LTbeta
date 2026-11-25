package dev.chavixq.ltbetav1.modules.player;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import net.minecraft.text.Text;
public class AutoLog extends Module {
    public AutoLog() { super("AutoLog", Category.PLAYER); }
    @Override public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!mc.isInSingleplayer() && mc.player.getHealth() <= 4.0f) {
            mc.player.sendMessage(Text.literal("§c[LT] Low health! Disconnecting..."), false);
            mc.disconnect();
        }
    }
}