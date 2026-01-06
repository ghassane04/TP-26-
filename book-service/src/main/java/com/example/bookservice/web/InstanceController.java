package com.example.bookservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/api/debug/instance")
    public String getInstanceInfo() { // Renamed method
        String hostName = System.getenv().getOrDefault("HOSTNAME", "localhost"); // Renamed var
        return "Service Node: " + hostName + " | Port: " + port; // Changed output format
    }
}
