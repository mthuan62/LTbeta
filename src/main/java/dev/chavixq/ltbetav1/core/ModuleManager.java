package dev.chavixq.ltbetav1.core;

import dev.chavixq.ltbetav1.modules.basefinding.*;
import dev.chavixq.ltbetav1.modules.render.*;
import dev.chavixq.ltbetav1.modules.player.*;
import dev.chavixq.ltbetav1.modules.combat.*;
import dev.chavixq.ltbetav1.modules.misc.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    public final List<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new PlayerBypass());
        modules.add(new LarpChunkFinder());
        modules.add(new SusChunkFinder());
        modules.add(new StashCache());
        modules.add(new TunnelBaseFinder());
        modules.add(new AmethystClusterESP());
        modules.add(new BlockEntityDebug());
        modules.add(new SpawnerFinder());

        modules.add(new PlayerESP());
        modules.add(new Fullbright());
        modules.add(new Nametags());
        modules.add(new PearlTrajectory());
        modules.add(new JumpCircles());
        modules.add(new Tracers());

        modules.add(new SafeFreecam());
        modules.add(new AdminList());
        modules.add(new AutoLog());

        modules.add(new AutoTotem());
        modules.add(new TriggerBot());
        modules.add(new AimAssist());
        modules.add(new AutoClicker());

        modules.add(new AutoEat());
        modules.add(new FastUse());
        modules.add(new Sprint());
        modules.add(new NoFall());
    }

    public void onTick() { for (Module m : modules) if (m.isEnabled()) m.onTick(); }
    public void onRender(MatrixStack matrices, float tickDelta) { for (Module m : modules) if (m.isEnabled()) m.onRender(matrices, tickDelta); }
    public void onRenderHUD(DrawContext context, RenderTickCounter tickCounter) { for (Module m : modules) if (m.isEnabled()) m.onRenderHUD(context, tickCounter); }
}