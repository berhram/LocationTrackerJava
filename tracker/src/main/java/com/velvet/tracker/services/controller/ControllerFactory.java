package com.velvet.tracker.services.controller;

public interface ControllerFactory<T extends ServiceController> {
    T create();
}
