package com.batmanatorul.api.base.impl;

import com.batmanatorul.api.base.api.IEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Events {

    private static final Map<EventKey<?>, IEvent<?>> methodListeners = new HashMap<>();

    public static<T> EventKey<T> create(Class<T> clazz, Function<T[], T> invoker) {
        Objects.requireNonNull(clazz, "Event class can not be null when creating an event key");
        Objects.requireNonNull(invoker, "Invoker can not be null when creating an event key");

        IEvent<T> event = new Event<>(clazz, invoker);
        EventKey<T> eventKey = new EventKey<>(clazz);
        methodListeners.put(eventKey, event);

        return eventKey;
    }

    public static<T> T getInvoker(EventKey<T> key) {
        return (T) methodListeners.get(key).invoker();
    }

    public static<T> void registerListener(EventKey<T> eventKey, T listener) {
        IEvent<T> event = (IEvent<T>) methodListeners.get(eventKey);
        event.register(listener);
    }

}
