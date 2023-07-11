package prefolio.prefolioserver.domain.post.domain;

public enum ActTag {
    SOCIETY("society"),
    PROJECT("project"),
    INTERN("intern");

    private String actTag;

    ActTag(String actTag) {
        this.actTag = actTag;
    }

    public String getActTag() {
        return this.actTag;
    }
}
