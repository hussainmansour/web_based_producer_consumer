package com.example.producerconsumerbe.Service.Snapshot;

import com.example.producerconsumerbe.Service.Model.Network;
import com.example.producerconsumerbe.Service.Model.Product;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Memento {

    private final List<Pair<Integer,Product>> products;

    public Memento(List<Pair<Integer,Product>> products){
        this.products = products;
    }

    public List<Pair<Integer,Product>> getState(){
        return products;
    }
}
