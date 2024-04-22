package com.cooba.component.mq;

public interface MessageQueue {

    void subscribe(String topic);

    void handleMessage(String json);
}
