package dev.chavixq.ltbetav1.modules.misc;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
public class NoFall extends Module {
    public static boolean active = false;
    public NoFall() { super("NoFall", Category.MISC); }
    @Override public void onEnable() { active = true; }
    @Override public void onDisable() { active = false; }
}