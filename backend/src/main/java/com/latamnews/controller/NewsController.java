package com.latamnews.controller;

import com.latamnews.dto.NewsArticleResponse;
import com.latamnews.service.NewsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/feed")
    public List<NewsArticleResponse> feed() {
        return newsService.latestFeed();
    }
}
