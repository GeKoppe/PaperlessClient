package org.dmsextension.paperless.client.templates;

import java.util.List;

public class TSpecifiedSearchResult<T extends IDto> implements IDto {
    private Integer count;
    private String next;
    private String previous;
    private List<T> results;

    public TSpecifiedSearchResult() { }

    @Override
    public String toString() {
        return "TSpecifiedSearchResult{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }

    @Override
    public String toJsonString() {
        return null;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
