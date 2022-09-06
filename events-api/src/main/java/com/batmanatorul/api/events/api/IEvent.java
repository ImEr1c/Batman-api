package com.batmanatorul.api.events.api;

public interface IEvent<T> {
    void register(T listener);

    T invoker();
}
