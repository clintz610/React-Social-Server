package com.revature.search;

import com.revature.search.dtos.SearchResponse;

import java.time.LocalTime;

public class ResponseBucket {

    private final SearchResponse response;
    private final LocalTime maxCacheAge;

    public ResponseBucket(SearchResponse response) {
        this.response = response;
        this.maxCacheAge = LocalTime.now().plusSeconds(360);
    }

    public SearchResponse get() {
        return this.response;
    }

    public boolean isValid() {
        return LocalTime.now().isBefore(this.maxCacheAge);
    }
}
