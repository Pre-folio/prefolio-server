package prefolio.prefolioserver.domain.constant;

public enum Path {
    PROFILE("profile"),
    IMAGE("image"),
    THUMBNAIL("thumbnail");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
