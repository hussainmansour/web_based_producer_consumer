package com.example.producerconsumerbe.Controller;

import com.example.producerconsumerbe.ProducerConsumerBeApplication;
import com.example.producerconsumerbe.Service.Socket.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.util.HtmlUtils;

@Controller
@EnableWebSocket
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/prod-cons")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + HtmlUtils.htmlEscape(message) + "!";
    }
}
