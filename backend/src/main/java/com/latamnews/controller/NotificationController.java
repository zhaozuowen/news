package com.latamnews.controller;

import com.latamnews.dto.NotificationHistoryResponse;
import com.latamnews.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/history")
    public List<NotificationHistoryResponse> history() {
        return notificationService.history();
    }
}
