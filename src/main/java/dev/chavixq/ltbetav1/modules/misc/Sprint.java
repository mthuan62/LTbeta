package dev.chavixq.ltbetav1.modules.misc;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
public class Sprint extends Module {
    public Sprint() { super("Sprint", Category.MISC); }
    @Override public void onTick() { if (mc.player != null) mc.player.setSprinting(true); }
}