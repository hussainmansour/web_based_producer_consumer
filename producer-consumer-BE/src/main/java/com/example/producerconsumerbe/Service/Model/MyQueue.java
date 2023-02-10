package com.example.producerconsumerbe.Service.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyQueue implements Observer {

    private String id;
    private final Queue<Product> queue;
    private List<Machine> forwardMachines;
    private int size;
    private boolean locker;

    public MyQueue() {
        this.forwardMachines = new ArrayList<>();
        queue = new LinkedList<>();
        size = 0;
        locker = false;
    }

    public boolean isLocker() {
        return locker;
    }

    public void toggleLocker(){
        this.locker = !locker;
    }

    public void put(Product product) {

        synchronized (this) {
            queue.add(product);
            this.notify();
            this.size = queue.size();
        }
    }

    public Product get() {
        if(this.isLocker())
            return null;
        synchronized (this) {
            if (queue.isEmpty()) {
                return null;
            }

            Product product = queue.remove();
            this.size = queue.size();
            return product;
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Machine> getForwardMachines() {
        return forwardMachines;
    }

    public void setForwardMachines(List<Machine> forwardMachines) {
        this.forwardMachines = forwardMachines;
    }

    public void clear(){
        this.queue.clear();
    }

    public Queue<Product> getQueue() {
        return queue;
    }

    @Override
    public void update() {
        List<Machine> machines = this.getForwardMachines();
        for (Machine machine : machines){
            System.out.println(machine.getConsumerThread());
            //machine.getConsumerThread().start();
            //machine.getProducerThread().start();
        }
    }
}
