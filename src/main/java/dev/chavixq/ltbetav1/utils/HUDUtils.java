package dev.chavixq.ltbetav1.utils;

import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.core.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HUDUtils {
    public static void drawArrayList(DrawContext context) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden || mc.getDebugHud().shouldShowDebugHud()) return;

        List<Module> activeModules = ModuleManager.INSTANCE.modules.stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparingInt(m -> -mc.textRenderer.getWidth(m.getName())))
                .collect(Collectors.toList());

        int y = 2;
        int screenWidth = context.getScaledWindowWidth();
        for (Module m : activeModules) {
            int width = mc.textRenderer.getWidth(m.getName());
            context.fill(screenWidth - width - 4, y - 1, screenWidth, y + 9, 0x80000000);
            context.drawTextWithShadow(mc.textRenderer, m.getName(), screenWidth - width - 2, y, 0xFF00FFCC);
            y += 10;
        }
    }
}