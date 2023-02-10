package com.example.producerconsumerbe.Service.Model;


import com.example.producerconsumerbe.Service.Socket.WebSocketService;
import com.example.producerconsumerbe.Service.Util.SERVICE_TIME;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Machine {

    private String id;
    private boolean running;
    private Product product;
    private Thread producerThread;
    private Thread consumerThread;
    private List<MyQueue> prevQueues;
    private MyQueue nextQueue;
    private int serviceTime;

    private final WebSocketService webSocketService;

    public Machine(WebSocketService webSocketService){
        this.webSocketService = webSocketService;
        this.id = UUID.randomUUID().toString();
        this.running = false;
        this.serviceTime = ThreadLocalRandom.current()
                .nextInt(SERVICE_TIME.MINIMUM.time, SERVICE_TIME.MAXIMUM.time);
        this.prevQueues = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Thread getProducerThread() {
        return producerThread;
    }

    public void setProducerThread(Thread producerThread) {
        this.producerThread = producerThread;
    }

    public Thread getConsumerThread() {
        return consumerThread;
    }

    public void setConsumerThread(Thread consumerThread) {
        this.consumerThread = consumerThread;
    }

    public List<MyQueue> getPrevQueues() {
        return prevQueues;
    }

    public void setPrevQueues(List<MyQueue> prevQueues) {
        this.prevQueues = prevQueues;
    }

    public void addQueueBefore(MyQueue q)
    {
        this.prevQueues.add(q);
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    public MyQueue getNextQueue() {
        return nextQueue;
    }

    public void setNextQueue(MyQueue nextQueue) {
        this.nextQueue = nextQueue;
    }


    public void notifyAllObservers(){
        nextQueue.update();
    }

    public void activate(){

        Runnable consumer = () -> {
            while(true) {
                for (MyQueue queue : prevQueues) {
                    if (!queue.isEmpty()) {
                        product = queue.get();
                        if (product == null) continue;
                        running = true;
                        notifyMachineRunning();
                        notifyQueueSize(queue);
                        System.out.println(serviceTime);
                        try {
                            Thread.sleep(serviceTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        running = false;
                        notifyMachineFinished();
                        // producerThread.start();
                        //-------------------
                        System.out.println("jjjjjjjjjjjjjjjjjjj");
                        nextQueue.put(product);
                        notifyQueueSize(nextQueue);
                        notifyAllObservers();
                        product = null;
                        //------------------------
                        // break;
                    }
                }
            }
        };
        System.out.println("CREATED THREAD");
        consumerThread = new Thread(consumer);
        consumerThread.start();
        //producerThread.start();
        }

    public void producer() {
        if (product != null && !running){
            System.out.println("jjjjjjjjjjjjjjjjjjj");
            nextQueue.put(product);
            notifyQueueSize(nextQueue);
            notifyAllObservers();
            product = null;
        }
        //consumerThread.start();
    }


    public void notifyQueueSize(MyQueue queue){
        JSONObject json = new JSONObject();
        json.put("id",queue.getId());
        json.put("size",queue.getSize());
        json.put("operation","updateQueueSize");
        webSocketService.notifyFrontEnd(json.toString());
    }

    public void notifyMachineRunning(){
        JSONObject json = new JSONObject();
        json.put("id",this.getId());
        var array = new JSONArray();
        array.put(product.getColor().getRed());
        array.put(product.getColor().getGreen());
        array.put(product.getColor().getBlue());
        json.put("color",array);
        json.put("operation","machineRunning");
        webSocketService.notifyFrontEnd(json.toString());
    }

    public void notifyMachineFinished(){
        JSONObject json = new JSONObject();
        json.put("operation","machineFinished");
        json.put("id",this.getId());
        webSocketService.notifyFrontEnd(json.toString());
    }

}
