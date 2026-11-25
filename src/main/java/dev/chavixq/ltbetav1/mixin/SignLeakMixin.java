package dev.chavixq.ltbetav1.mixin;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(SignEditScreen.class)
public class SignLeakMixin {
    @ModifyVariable(method = "method_49825", at = @At("HEAD"), argsOnly = true)
    private static Text stripTranslatable(Text original) {
        if (original != null) return Text.literal(original.getString());
        return original;
    }
}