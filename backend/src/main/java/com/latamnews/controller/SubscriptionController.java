package com.latamnews.controller;

import com.latamnews.dto.SubscriptionRequest;
import com.latamnews.dto.SubscriptionResponse;
import com.latamnews.service.SubscriptionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionResponse> list() {
        return subscriptionService.listSubscriptions();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponse create(@Valid @RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
    }
}
