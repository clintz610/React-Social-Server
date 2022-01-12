package com.revature.search;

import lombok.Getter;

@Getter
public class SearchResult implements Searchable {

    private final String key;
    private final String label;

    public SearchResult(Searchable entity) {
        this.key = entity.getKey();
        this.label = entity.getLabel();
    }

}
