package com.example.producerconsumerbe.Controller;

import com.example.producerconsumerbe.Service.ProducerConsumerService;
import com.example.producerconsumerbe.Service.Socket.WebSocketService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/producer_consumer")
@CrossOrigin()
public class ProducerConsumerController {

    private final ProducerConsumerService producerConsumerService;
    private final WebSocketService webSocketService;

    @Autowired
    public ProducerConsumerController(ProducerConsumerService producerConsumerService
            , WebSocketService webSocketService, WebSocketService webSocketService1) {
        this.producerConsumerService = producerConsumerService;
        this.webSocketService = webSocketService1;
    }


    @PostMapping("/addMachine")
    public void addMachine(){
        producerConsumerService.addMachine();
    }

    @PostMapping("/addQueue")
    public void addQueue(){
        producerConsumerService.addQueue();
    }

    //need to get the color
    @PostMapping ("/addProduct")
    public void addProduct(){
        producerConsumerService.addProduct();
    }

    @PostMapping("/connectMtoQ")
    public void connectMachineToQueue(@RequestBody Map<String,Object> json){
        producerConsumerService.connectMachineToQueue(
                (String) json.get("machine_id"), (String) json.get("queue_id"));
    }

    @PostMapping("/connectQtoM")
    public void connectQueueToMachine(@RequestBody Map<String,Object> json){
        producerConsumerService.connectQueueToMachine
                ((String) json.get("machine_id"), (String) json.get("queue_id"));
    }

    @PostMapping("/start")
    public void start(){
        producerConsumerService.start();
    }

    @PostMapping("/pause")
    public void pause(@RequestBody Map<String,Object> json){
        producerConsumerService.pause((String) json.get("queue_id"));
    }

    @PostMapping("/replay")
    public void replay(){
        producerConsumerService.replay();
    }


    @PostMapping("/clear")
    public void clear(){
        producerConsumerService.clear();
    }

    /*@PostMapping("/to")
    public void test(){
        var j = new JSONObject();
        j.put("id","Q1");
        j.put("size","1");
        webSocketService.notifyFrontEnd(
                j.toString());
    }*/
}
