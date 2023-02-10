package com.example.producerconsumerbe.Service.Model;

import com.example.producerconsumerbe.Service.Snapshot.CareTaker;
import com.example.producerconsumerbe.Service.Snapshot.Memento;
import com.example.producerconsumerbe.Service.Snapshot.Originator;
import com.example.producerconsumerbe.Service.Socket.WebSocketService;
import org.json.JSONObject;
import org.springframework.data.util.Pair;


import java.util.HashMap;
import java.util.List;

public class Network {

    private final HashMap<String,Machine> machines;
    private final HashMap<String,MyQueue> queues;
    private final CareTaker careTaker;
    private final Originator originator;
    private final WebSocketService webSocketService;

    private long running = 0;


    public Network(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
        this.machines = new HashMap<>();
        this.queues = new HashMap<>();
        this.careTaker = new CareTaker();
        this.originator = new Originator();
    }


    public void addMachine(Machine machine){
        machine.setId("M" + (machines.size() + 1));
        machines.put("M" + (machines.size() + 1),machine);
    }

    public void addQueue(MyQueue queue){
        queue.setId("Q" + (queues.size() + 1));
        queues.put("Q" + (queues.size() + 1),queue);
    }

    public void connectMachineToQueue(String machineId,String queueId){
        System.out.println(machines.get(machineId));
        System.out.println(machineId);
        System.out.println(queueId);
        machines.get(machineId).setNextQueue(queues.get(queueId));
    }

    public void connectQueueToMachine(String machineId,String queueId){
        System.out.println(machines.get(machineId));

        machines.get(machineId).getPrevQueues().add(queues.get(queueId));
        queues.get(queueId).getForwardMachines().add(machines.get(machineId));
    }

    public void addProduct(Product product){
        if (running == 0) originator.getProducts().add(Pair.of(0,product));
        originator.getProducts().add(Pair.of((int) System.currentTimeMillis(),product));
        queues.get("Q1").put(product);
        notifyQueueSize(queues.get("Q1"));
       // notifyForwardMachineOfQ1(queues.get("Q1").getForwardMachines());
    }

    public void notifyForwardMachineOfQ1(List<Machine> machines){
        for(var machine:machines){
            machine.getConsumerThread().start();
        }
    }

    public void pause(String queueId){
        queues.get(queueId).toggleLocker();
    }

    public void start(){
        running = System.currentTimeMillis();
        for (Machine machine : machines.values()){
            machine.activate();
        }
    }

    public void replay(){
        Memento memento = originator.saveToMemento();
        careTaker.add(memento);

        for (MyQueue queue : queues.values()){
            queue.clear();
        }

        start();

        long start = System.currentTimeMillis();

        var products = originator.getProducts();

        for (var product : products){
            if (System.currentTimeMillis() - start > product.getFirst()){
                queues.get("Q1").put(product.getSecond());
            } else {
                try{
                    wait(System.currentTimeMillis() - start);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyQueueSize(MyQueue queue){
        JSONObject json = new JSONObject();
        json.put("id",queue.getId());
        json.put("size",queue.getSize());
        json.put("operation","updateQueueSize");
        webSocketService.notifyFrontEnd(json.toString());
    }

}
