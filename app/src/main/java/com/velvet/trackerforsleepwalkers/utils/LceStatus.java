package com.velvet.trackerforsleepwalkers.utils;

public enum LceStatus {
    /**
     * Request has succeeded and response contains data
     */
    SUCCESS,
    /**
     * Request failed
     */
    FAILURE,
    /**
     * Request is sent. Waiting for a response
     */
    IN_FLIGHT
}
