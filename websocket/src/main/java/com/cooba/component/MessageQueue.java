package com.cooba.component;

public interface MessageQueue {

    void subscribe(String topic);

    void handleMessage(String json);
}
