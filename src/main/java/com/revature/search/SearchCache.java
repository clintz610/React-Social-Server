package com.revature.search;

import java.util.concurrent.ConcurrentHashMap;

import com.revature.search.dtos.SearchResponse;

public class SearchCache {

    private final ConcurrentHashMap<String, ResponseBucket> cache = new ConcurrentHashMap<>();

    public SearchResponse get(String query) {
        return cache.get(query).get();
    }

    public  void put(String query, SearchResponse response) {
        cache.put(query, new ResponseBucket(response));
    }

    public  boolean contains(String query) {
        return cache.containsKey(query) && cache.get(query).isValid();
    }
}
