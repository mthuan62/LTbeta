package dev.chavixq.ltbetav1;

import dev.chavixq.ltbetav1.core.ModuleManager;
import dev.chavixq.ltbetav1.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class LTClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModuleManager.INSTANCE.init();
        
        KeyBinding guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ltbetav1.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.ltbetav1"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            ModuleManager.INSTANCE.onTick();
            while (guiKey.wasPressed()) mc.setScreen(new ClickGUI());
        });

        WorldRenderEvents.LAST.register(context -> {
            if (context.matrixStack() != null) {
                ModuleManager.INSTANCE.onRender(context.matrixStack(), context.tickDelta());
            }
        });

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            ModuleManager.INSTANCE.onRenderHUD(context, tickDelta);
            dev.chavixq.ltbetav1.utils.HUDUtils.drawArrayList(context);
        });
    }
}