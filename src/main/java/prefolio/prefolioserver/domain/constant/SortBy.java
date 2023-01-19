package prefolio.prefolioserver.domain.constant;

public enum SortBy {
    CREATED_AT("createdAt"),
    LIKES("likes"),
    HITS("hits");

    private String sortBy;

    SortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return this.sortBy;
    }
}
