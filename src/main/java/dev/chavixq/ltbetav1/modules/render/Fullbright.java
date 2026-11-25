package dev.chavixq.ltbetav1.modules.render;
import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
public class Fullbright extends Module {
    public Fullbright() { super("Fullbright", Category.RENDER); }
    @Override public void onEnable() { mc.options.getGamma().setValue(100.0); }
    @Override public void onDisable() { mc.options.getGamma().setValue(1.0); }
}