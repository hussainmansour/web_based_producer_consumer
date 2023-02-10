package com.example.producerconsumerbe.Service.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyFrontEnd(final String payload){
        messagingTemplate.convertAndSend("/topic/notify", payload);
    }

    /*public void updateQueueSize(final String payload) {
        messagingTemplate.convertAndSend("/topic/updateQueueSize", payload);
    }

    public void machineRunning(final String payload) {
        messagingTemplate.convertAndSend("/topic/machineRunning", payload);
    }

    public void machineFinished(final String payload) {
        messagingTemplate.convertAndSend("/topic/machineFinished", payload);
    }*/

}
