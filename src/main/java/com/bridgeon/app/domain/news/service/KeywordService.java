package com.bridgeon.app.domain.news.service;

import com.bridgeon.app.domain.news.entity.NewsArticle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class KeywordService {
    /** 기사 목록에서 키워드 상위 N개 추출 */
    public List<String> extractTopKeywords(List<NewsArticle> list, int limit) {
        Map<String, Integer> freq = new HashMap<>();
        for (NewsArticle n : list) {
            String text = (n.getNewstitle() + " " + (n.getSummary() == null ? "" : n.getSummary()))
                    .replaceAll("<[^>]*>", " ")
                    .replaceAll("[^가-힣A-Za-z0-9 ]", " ")
                    .toLowerCase(Locale.ROOT);
            for (String w : text.split("\\s+")) {
                if (w.isBlank()) continue;
                freq.merge(w, 1, Integer::sum);
            }
        }
        return freq.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    /** 텍스트가 지정 토큰을 하나라도 포함하는지 */
    public boolean containsAny(String text, List<String> tokens) {
        if (text == null) return false;
        for (String t : tokens) {
            if (text.contains(t)) return true;
        }
        return false;
    }
}

