package dev.chavixq.ltbetav1.modules.player;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.core.ModuleManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
public class AdminList extends Module {
    public AdminList() { super("AdminList", Category.PLAYER); }
    @Override public void onTick() {
        if (mc.world == null || mc.player == null) return;
        boolean admin = false;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            String name = p.getName().getString().toLowerCase();
            if (name.contains("admin") || name.contains("mod") || name.contains("owner")) { admin = true; break; }
        }
        if (admin) {
            ModuleManager.INSTANCE.modules.stream().filter(m -> m.getCategory() == Category.BASE_FINDING && m.isEnabled())
                .forEach(m -> { m.toggle(); mc.player.sendMessage(Text.literal("§c[LT] Admin detected! " + m.getName() + " disabled."), true); });
        }
    }
}