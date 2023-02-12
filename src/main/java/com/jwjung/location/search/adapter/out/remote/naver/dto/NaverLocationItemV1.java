package com.jwjung.location.search.adapter.out.remote.naver.dto;

import org.springframework.web.util.HtmlUtils;

import java.util.regex.Pattern;

public record NaverLocationItemV1(String title,
                                  String roadAddress,
                                  String link,
                                  String category,
                                  String description,
                                  String telephone,
                                  String address,

                                  String mapx,
                                  String mapy) {
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
    private static final Pattern MULTI_SPACE = Pattern.compile("\\s+");

    public String getEscapingTitle() {
        String htmlUnescape = HtmlUtils.htmlUnescape(TAG_PATTERN.matcher(this.title).replaceAll(""));
        return MULTI_SPACE.matcher(htmlUnescape).replaceAll(" ");
    }
}
