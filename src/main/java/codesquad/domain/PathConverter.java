package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.file.Path;
import java.nio.file.Paths;

@Converter
public class PathConverter implements AttributeConverter<Path, String> {
    private static final Logger log =  LoggerFactory.getLogger(PathConverter.class);

    @Override
    public String convertToDatabaseColumn(Path attribute) {
        if (attribute == null) {
            return null;
        }
        log.debug("attribute path : {}", attribute);
        return attribute.toString();
    }

    @Override
    public Path convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        log.debug("dbData : {}", dbData);
        return Paths.get(dbData);
    }
}
