package codesquad.domain;

import java.nio.file.Path;

public class FileStorageProperty {

    private Path location;

    public FileStorageProperty(Path location) {
        this.location = location;
    }

    public Path getLocation() {
        return this.location;
    }
}
