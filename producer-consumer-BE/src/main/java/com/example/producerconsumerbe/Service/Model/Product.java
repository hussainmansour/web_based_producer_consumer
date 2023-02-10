package com.example.producerconsumerbe.Service.Model;

import com.example.producerconsumerbe.Service.Util.RandomColorGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class Product implements Cloneable{

    private final Color color;
    private final String id;

    public Product() {
        this.id = UUID.randomUUID().toString();
        this.color = RandomColorGenerator.generateColor();
    }

    public Color getColor() {
        return color;
    }
    public String getId() {
        return id;
    }


    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString(){
        ArrayList<Integer> rgb = new ArrayList<>();
        rgb.add(color.getRed());
        rgb.add(color.getGreen());
        rgb.add(color.getBlue());
        return rgb.toString();
    }
}
