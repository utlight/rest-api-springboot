package com.tw.bootcamp.controllers;

import com.tw.bootcamp.resources.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class GreetingController {
    private static final String GREETING_TEMPLATE = "Hello, %s";

    @RequestMapping("/")
    @ResponseBody
    public HttpEntity<Greeting> index(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        Greeting greeting = new Greeting(String.format(GREETING_TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).index(name)).withSelfRel());

        return new ResponseEntity<>(greeting, OK);
    }
}
