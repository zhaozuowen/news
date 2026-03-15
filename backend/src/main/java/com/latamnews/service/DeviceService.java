package com.latamnews.service;

import com.latamnews.dto.DeviceRegistrationRequest;
import com.latamnews.dto.DeviceResponse;
import com.latamnews.entity.User;
import com.latamnews.entity.UserDevice;
import com.latamnews.repository.UserDeviceRepository;
import com.latamnews.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final MockAuthenticationFacade auth;

    public DeviceResponse register(DeviceRegistrationRequest request) {
        User user = userRepository.findById(auth.currentUserId()).orElseThrow();
        UserDevice device = new UserDevice();
        device.setUser(user);
        device.setPlatform(request.platform());
        device.setDeviceName(request.deviceName());
        device.setFcmToken(request.fcmToken());
        device.setActive(true);
        device.setLastSeenAt(LocalDateTime.now());
        UserDevice saved = userDeviceRepository.save(device);
        return toResponse(saved);
    }

    public List<DeviceResponse> list() {
        return userDeviceRepository.findByUserIdAndActiveTrue(auth.currentUserId()).stream().map(this::toResponse).toList();
    }

    private DeviceResponse toResponse(UserDevice device) {
        return new DeviceResponse(device.getId(), device.getPlatform(), device.getDeviceName(), device.getFcmToken(), device.getActive());
    }
}
