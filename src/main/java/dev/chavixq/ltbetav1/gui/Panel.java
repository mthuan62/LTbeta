package dev.chavixq.ltbetav1.gui;

import dev.chavixq.ltbetav1.core.Category;
import dev.chavixq.ltbetav1.core.Module;
import dev.chavixq.ltbetav1.core.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import java.util.List;
import java.util.stream.Collectors;

public class Panel {
    public int x, y;
    public Category category;
    private boolean dragging;
    private int dragX, dragY;
    public boolean extended = true;

    public Panel(Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
    }

    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer tr) {
        if (dragging) { this.x = mouseX - dragX; this.y = mouseY - dragY; }
        context.fill(x, y, x + 120, y + 16, 0xFF1A1A1A);
        context.drawTextWithShadow(tr, category.name(), x + 4, y + 4, 0xFFFFFFFF);

        if (!extended) return;

        List<Module> mods = ModuleManager.INSTANCE.modules.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
        int currentY = y + 16;
        context.fill(x, currentY, x + 120, currentY + (mods.size() * 16), 0xFF0F0F0F);

        for (Module m : mods) {
            boolean hover = mouseX >= x && mouseX <= x + 120 && mouseY >= currentY && mouseY <= currentY + 16;
            if (hover) context.fill(x, currentY, x + 120, currentY + 16, 0xFF222222);
            context.drawTextWithShadow(tr, m.getName(), x + 4, currentY + 4, m.isEnabled() ? 0xFF00FFCC : 0xFF888888);
            currentY += 16;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseX <= x + 120 && mouseY >= y && mouseY <= y + 16) {
            if (button == 0) { dragging = true; dragX = (int) (mouseX - x); dragY = (int) (mouseY - y); return true; }
            else if (button == 1) { extended = !extended; return true; }
        }
        if (!extended) return false;

        int currentY = y + 16;
        List<Module> mods = ModuleManager.INSTANCE.modules.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
        for (Module m : mods) {
            if (mouseX >= x && mouseX <= x + 120 && mouseY >= currentY && mouseY <= currentY + 16) {
                if (button == 0) m.toggle();
                return true;
            }
            currentY += 16;
        }
        return false;
    }
    public void mouseReleased() { dragging = false; }
}