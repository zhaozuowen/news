package com.latamnews.scheduler;

import com.latamnews.service.MatcherService;
import com.latamnews.service.RssIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsPipelineScheduler {
    private final RssIngestionService rssIngestionService;
    private final MatcherService matcherService;

    @Scheduled(initialDelay = 10000, fixedDelayString = "${app.scheduler.fetch-delay-ms:900000}")
    public void runFetchPipeline() {
        log.info("Running RSS fetch pipeline");
        rssIngestionService.fetchLatest();
    }

    @Scheduled(initialDelay = 20000, fixedDelayString = "${app.scheduler.match-delay-ms:600000}")
    public void runMatchingPipeline() {
        log.info("Running topic matching pipeline");
        matcherService.matchDueTopics();
    }
}
