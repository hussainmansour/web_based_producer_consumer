package com.example.producerconsumerbe.Service.Util;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomColorGenerator {

    public static Color generateColor(){
        float r = ThreadLocalRandom.current().nextFloat(1);
        float b = ThreadLocalRandom.current().nextFloat(1);
        float g = ThreadLocalRandom.current().nextFloat(1);

        return new Color(r, g, b);
    }

}
