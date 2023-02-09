package com.jwjung.location.remote.naver.dto;

import lombok.With;
import org.springframework.web.util.HtmlUtils;

import java.util.regex.Pattern;

public record NaverMapItemV1(@With String title, String link,
                             String category, String description,
                             String telephone, String address,
                             String roadAddress,
                             String mapx, String mapy) {
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");

    public String getEscapingTitle() {
        return HtmlUtils.htmlUnescape(TAG_PATTERN.matcher(this.title).replaceAll(""));
    }
}
