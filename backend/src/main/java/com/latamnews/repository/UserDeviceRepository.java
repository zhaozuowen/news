package com.latamnews.repository;

import com.latamnews.entity.UserDevice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByUserIdAndActiveTrue(Long userId);
}
