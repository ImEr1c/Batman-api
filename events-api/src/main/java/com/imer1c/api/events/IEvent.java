package com.imer1c.api.events;

public interface IEvent<T> {
    void register(T listener);

    T invoker();
}
