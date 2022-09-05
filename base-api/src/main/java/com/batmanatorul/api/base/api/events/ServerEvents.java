package com.batmanatorul.api.base.api.events;

import com.batmanatorul.api.base.impl.EventKey;
import com.batmanatorul.api.base.impl.Events;
import net.minecraft.server.MinecraftServer;

public interface ServerEvents {
    EventKey<ServerStarting> SERVER_STARTING = Events.create(ServerStarting.class, serverStartings -> server -> {
        for (ServerStarting serverStarting : serverStartings) {
            serverStarting.startServer(server);
        }
    });

    EventKey<ServerStarted> SERVER_STARTED = Events.create(ServerStarted.class, serverStarteds -> server -> {
        for (ServerStarted serverStarted : serverStarteds) {
            serverStarted.start(server);
        }
    });

    EventKey<ServerStopping> SERVER_STOPPING = Events.create(ServerStopping.class, serverStoppings -> server -> {
        for (ServerStopping serverStopping : serverStoppings) {
            serverStopping.stop(server);
        }
    });

    EventKey<ServerStopped> SERVER_STOPPED = Events.create(ServerStopped.class, serverStoppeds -> server -> {
        for (ServerStopped serverStopped : serverStoppeds) {
            serverStopped.stop(server);
        }
    });

    EventKey<ServerTick> TICK = Events.create(ServerTick.class, serverTicks -> server -> {
        for (ServerTick serverTick : serverTicks) {
            serverTick.tick(server);
        }
    });

    interface ServerStarting {
        void startServer(MinecraftServer server);
    }

    interface ServerStopping {
        void stop(MinecraftServer server);
    }

    interface ServerStarted {
        void start(MinecraftServer server);
    }

    interface ServerStopped {
        void stop(MinecraftServer server);
    }

    interface ServerTick {
        void tick(MinecraftServer server);
    }


}
