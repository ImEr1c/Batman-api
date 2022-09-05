package com.batmanatorul.api.base.api;

public interface IEvent<T> {
    void register(T listener);

    T invoker();
}
