package com.taskforge.core;

import org.springframework.stereotype.Service;

@Service
public class PingService {
    public String ping() {
        return "Taskforge is alive!";
    }
}
