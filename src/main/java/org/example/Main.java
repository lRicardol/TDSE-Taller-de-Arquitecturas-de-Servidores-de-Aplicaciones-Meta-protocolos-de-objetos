package org.example;

import static org.example.framework.MicroFramework.*;

public class Main {

    public static void main(String[] args) {

        staticfiles("webroot");

        get("/hello", (req, res) -> {
            res.addHeader("Content-Type", "text/plain");
            return "Hello " + req.getValues("name");
        });

        get("/pi", (req, res) -> {
            res.addHeader("Content-Type", "text/plain");
            return String.valueOf(Math.PI);
        });

        start(8080);
    }
}