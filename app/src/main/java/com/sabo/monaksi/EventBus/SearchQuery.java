package com.sabo.monaksi.EventBus;

public class SearchQuery {
    private boolean search;
    private String query;

    public SearchQuery(boolean search, String query) {
        this.search = search;
        this.query = query;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
