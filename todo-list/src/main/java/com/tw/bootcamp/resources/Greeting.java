package com.tw.bootcamp.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Greeting extends ResourceSupport {
    private String content;

    @JsonCreator
    public Greeting(@JsonProperty String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
