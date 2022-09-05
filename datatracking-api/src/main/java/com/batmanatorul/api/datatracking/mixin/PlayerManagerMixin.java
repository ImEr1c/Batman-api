package com.batmanatorul.api.datatracking.mixin;

import com.batmanatorul.api.datatracking.interfaces.SyncServerWorld;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void sendDataToPlayer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ((SyncServerWorld) player.getWorld()).sendDataToNewPlayer(player);
    }
}
