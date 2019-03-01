package com.lambdaschool.javacars;

public class CarsNotFoundException extends RuntimeException{

    public CarsNotFoundException (Long id) {
        super("Could not find car" + " " + id);
    }
}
