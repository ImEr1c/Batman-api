package com.imer1c.api.events;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Function;

public class Event<T> implements IEvent<T> {

    private final Class<? extends T> clazz;
    private final Function<T[], T> invokerFactory;
    private T invoker;
    private T[] listeners;

    public void register(T listener) {
        Objects.requireNonNull(listener, "Event Listener can not be null");

        T[] temp = listeners;

        listeners = (T[]) Array.newInstance(clazz, listeners.length + 1);

        System.arraycopy(temp, 0, listeners, 0, temp.length);

        listeners[temp.length] = listener;

        invoker = invokerFactory.apply(listeners);
    }

    protected Event(Class<? extends T> clazz, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.clazz = clazz;

        listeners = (T[]) Array.newInstance(clazz, 0);
        invoker = invokerFactory.apply(listeners);
    }

    public T invoker() {
        Objects.requireNonNull(invoker, "Event Invoker is null for some reason");

        return invoker;
    }

}
