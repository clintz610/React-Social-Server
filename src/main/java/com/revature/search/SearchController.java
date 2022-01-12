package com.revature.search;

import com.revature.search.dtos.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResponse queryByString(@RequestParam(name = "query", required = true) String query) {
        return searchService.userQuery(query);
    }

    @GetMapping(path= "/2")
    public SearchResponse query2ByString(@RequestParam(name = "query", required = true) String query) {
        return searchService.userQuery(query);
    }
}
