package com.latamnews.repository;

import com.latamnews.entity.RssSource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssSourceRepository extends JpaRepository<RssSource, Long> {
    List<RssSource> findByActiveTrueOrderByPriorityDesc();
}
