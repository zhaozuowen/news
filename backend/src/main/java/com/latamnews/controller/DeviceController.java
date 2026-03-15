package com.latamnews.controller;

import com.latamnews.dto.DeviceRegistrationRequest;
import com.latamnews.dto.DeviceResponse;
import com.latamnews.service.DeviceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/register")
    public DeviceResponse register(@Valid @RequestBody DeviceRegistrationRequest request) {
        return deviceService.register(request);
    }

    @GetMapping
    public List<DeviceResponse> list() {
        return deviceService.list();
    }
}
