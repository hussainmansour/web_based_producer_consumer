package com.example.producerconsumerbe.Service.Snapshot;

import java.util.Stack;

public class CareTaker {

    private final Stack<Memento> mementoStack;

    public CareTaker(){
        mementoStack = new Stack<>();
    }

    public void add(Memento memento){
        mementoStack.push(memento);
    }

    public Memento getLast(){
        return mementoStack.pop();
    }

    public int getSize(){
        return mementoStack.size();
    }

    public void clear(){
        mementoStack.clear();
    }

}
