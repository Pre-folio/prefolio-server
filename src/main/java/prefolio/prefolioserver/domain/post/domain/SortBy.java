package prefolio.prefolioserver.domain.post.domain;

import lombok.Getter;

@Getter
public enum SortBy {
    CREATED_AT("createdAt"),
    LIKES("likes"),
    HITS("hits");

    private final String sortBy;

    SortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
