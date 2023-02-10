package com.jwjung.location.search.adapter.out.remote.naver.dto;

import org.springframework.web.util.HtmlUtils;

import java.util.regex.Pattern;

public record NaverLocationItemV1(String title, String link,
                                  String category, String description,
                                  String telephone, String address,
                                  String roadAddress,
                                  String mapx, String mapy) {
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");

    public String getEscapingTitle() {
        return HtmlUtils.htmlUnescape(TAG_PATTERN.matcher(this.title).replaceAll(""));
    }
}
