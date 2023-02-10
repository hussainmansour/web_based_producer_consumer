package com.example.producerconsumerbe.Service.Snapshot;

import com.example.producerconsumerbe.Service.Model.Product;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Originator {

    private List<Pair<Integer,Product>> products;

    public Originator() {
        this.products = new ArrayList<>();
    }

    public List<Pair<Integer,Product>> getProducts() {
        return products;
    }

    public void setProducts(List<Pair<Integer,Product>> products) {
        this.products = products;
    }

    public Memento saveToMemento(){
        return new Memento(products);
    }

    public void getStateFromMemento(Memento memento){
        this.products = memento.getState();
    }
}
