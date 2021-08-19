package com.example.rabbitProducer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    @Autowired
    private SomeProducer someProducer;
    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public void monitoringOfCanceled() {
        String message = "Hi from Kaspi";
        someProducer.CheckProducer(message);


    }
}
