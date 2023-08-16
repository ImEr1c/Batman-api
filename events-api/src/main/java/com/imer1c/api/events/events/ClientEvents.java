package com.imer1c.api.events.events;

import com.imer1c.api.events.EventKey;
import com.imer1c.api.events.Events;
import net.minecraft.client.MinecraftClient;

public interface ClientEvents {

    EventKey<StartTick> START_TICK = Events.create(StartTick.class, startTicks -> client -> {
        for (StartTick startTick : startTicks) {
            startTick.startTick(client);
        }
    });

    EventKey<EndTick> END_TICK = Events.create(EndTick.class, endTicks -> client -> {
        for (EndTick endTick : endTicks) {
            endTick.endTick(client);
        }
    });

    interface StartTick {
        void startTick(MinecraftClient client);
    }

    interface EndTick {
        void endTick(MinecraftClient client);
    }

}
