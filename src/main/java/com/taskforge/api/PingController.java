package com.taskforge.api;

import com.taskforge.api.dto.ApiResponse;
import com.taskforge.core.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingController {
    private final PingService pingService;

    @GetMapping("/ping")
    public ApiResponse ping() {
        return new ApiResponse(pingService.ping());
    }
}
