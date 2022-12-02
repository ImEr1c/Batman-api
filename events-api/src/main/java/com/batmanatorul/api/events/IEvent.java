package com.batmanatorul.api.events;

public interface IEvent<T> {
    void register(T listener);

    T invoker();
}
