package com.latamnews.controller;

import com.latamnews.dto.NotificationPreferenceRequest;
import com.latamnews.dto.NotificationPreferenceResponse;
import com.latamnews.service.NotificationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences/notifications")
@RequiredArgsConstructor
public class PreferenceController {
    private final NotificationPreferenceService preferenceService;

    @GetMapping
    public NotificationPreferenceResponse get() {
        return preferenceService.getCurrentSettings();
    }

    @PutMapping
    public NotificationPreferenceResponse update(@Valid @RequestBody NotificationPreferenceRequest request) {
        return preferenceService.update(request);
    }
}
