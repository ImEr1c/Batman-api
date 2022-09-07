package com.batmanatorul.api.events.mixin;

import com.batmanatorul.api.events.api.events.ServerEvents;
import com.batmanatorul.api.events.impl.Events;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerMixin {

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"))
    public void beforeStart(CallbackInfo ci) {
        Events.getInvoker(ServerEvents.SERVER_STARTING).startServer((MinecraftServer) (Object) this);
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setFavicon(Lnet/minecraft/server/ServerMetadata;)V"))
    public void afterStart(CallbackInfo ci) {
        Events.getInvoker(ServerEvents.SERVER_STARTED).start((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void beforeShut(CallbackInfo ci) {
        Events.getInvoker(ServerEvents.SERVER_STOPPING).stop((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("TAIL"))
    public void afterShut(CallbackInfo ci) {
        Events.getInvoker(ServerEvents.SERVER_STOPPED).stop((MinecraftServer) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Events.getInvoker(ServerEvents.TICK).tick((MinecraftServer) (Object) this);
    }
}
