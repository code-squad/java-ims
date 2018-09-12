package codesquad.domain;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

public class FileStoragePropertyTest {
    @Test
    public void getLocation() {
        FileStorageProperty fileStorageProperty = new FileStorageProperty(Paths.get("target/files/" + Math.abs(new Random().nextLong())));
        Path location = fileStorageProperty.getLocation();
        assertNotNull(location);
    }
}
