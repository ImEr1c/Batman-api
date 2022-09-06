package com.batmanatorul.api.events.impl;

public class EventKey<T> {

    private final Class<T> clazz;

    protected EventKey(Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean equals(Object o) {
        if (!(o instanceof EventKey<?> eventKey))
            return false;

        return eventKey.clazz.equals(clazz);
    }

}
