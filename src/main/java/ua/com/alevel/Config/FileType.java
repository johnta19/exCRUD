package ua.com.alevel.Config;

public enum FileType {
    JSON_TYPE("library.json"),
    CSV_TYPE("library.csv");

    private final String path;

    FileType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
