package com.latamnews.service;

import org.springframework.stereotype.Component;

@Component
public class MockAuthenticationFacade {
    public Long currentUserId() {
        return 1L;
    }
}
