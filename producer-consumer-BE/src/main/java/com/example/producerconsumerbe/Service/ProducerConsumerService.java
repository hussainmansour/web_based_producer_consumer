package com.example.producerconsumerbe.Service;

import com.example.producerconsumerbe.Service.Model.Machine;
import com.example.producerconsumerbe.Service.Model.MyQueue;
import com.example.producerconsumerbe.Service.Model.Network;
import com.example.producerconsumerbe.Service.Model.Product;
import com.example.producerconsumerbe.Service.Socket.WebSocketService;
import org.springframework.stereotype.Service;

@Service
public class ProducerConsumerService {

    private Network network;
    private final WebSocketService webSocketService;

    public ProducerConsumerService(WebSocketService webSocketService){
        network = new Network(webSocketService);
        this.webSocketService = webSocketService;
    }

    public void addMachine(){
        network.addMachine(new Machine(webSocketService));
    }

    public void addQueue(){
        network.addQueue(new MyQueue());
    }

    public void addProduct(){
        network.addProduct(new Product());
    }

    public void connectMachineToQueue(String machineId,String queueId){
        network.connectMachineToQueue(machineId,queueId);
    }

    public void connectQueueToMachine(String machineId,String queueId){
        network.connectQueueToMachine(machineId,queueId);
    }

    public void pause(String queueId){
        network.pause(queueId);
    }

    public void start(){
        network.start();
    }

    public void replay(){
        network.replay();
    }

    public void clear() {
        network = new Network(webSocketService);
    }

}
