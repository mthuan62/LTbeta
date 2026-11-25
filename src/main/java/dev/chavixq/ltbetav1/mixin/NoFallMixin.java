package dev.chavixq.ltbetav1.mixin;
import dev.chavixq.ltbetav1.modules.misc.NoFall;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ClientPlayerEntity.class)
public class NoFallMixin {
    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onSendMovementPackets(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (NoFall.active && player.fallDistance > 2.0f) {
            player.setOnGround(true); player.fallDistance = 0.0F;
        }
    }
}