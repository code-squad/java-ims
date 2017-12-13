package codesquad.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import codesquad.exception.InvalidStoreFileException;

public class FileStorage {
	private final String filePath = "C:\\dev\\storage";
	private final Path rootLocation = Paths.get(filePath);
	private AtomicLong fileId = new AtomicLong(0);
	
	public String getFileName(MultipartFile file) {
		return StringUtils.cleanPath(file.getOriginalFilename());
	}
	
	public void store(MultipartFile file) throws IOException, InvalidStoreFileException {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if(file.isEmpty()) {
			throw new InvalidStoreFileException("선택된 파일이 없습니다.");
		}
		Files.copy(file.getInputStream(), rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
	}
	
	
	
	public String getFilePath() {
		return filePath;
	}
	
	public Long getNextId() {
		return fileId.incrementAndGet();
	}
}