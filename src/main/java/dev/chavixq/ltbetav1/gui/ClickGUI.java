package dev.chavixq.ltbetav1.gui;

import dev.chavixq.ltbetav1.core.Category;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {
    private final List<Panel> panels = new ArrayList<>();

    public ClickGUI() {
        super(Text.literal("LTbetav1"));
        int startX = 20;
        for (Category cat : Category.values()) {
            panels.add(new Panel(cat, startX, 40));
            startX += 130;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        for (Panel p : panels) p.render(context, mouseX, mouseY, this.textRenderer);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Panel p : panels) if (p.mouseClicked(mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Panel p : panels) p.mouseReleased();
        return super.mouseReleased(mouseX, mouseY, button);
    }
    @Override public boolean shouldPause() { return false; }
}