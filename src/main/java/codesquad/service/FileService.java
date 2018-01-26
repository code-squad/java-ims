package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.File;
import codesquad.domain.FileRepository;

@Service
public class FileService {
	@Resource(name = "fileRepository")
	private FileRepository fileRepository;

	public void add(File dbFile) {
		fileRepository.save(dbFile);
	}

	public File findById(long id) {
		return fileRepository.findOne(id);
	}

	public List<File> findAll() {
		return fileRepository.findAll();
	}

	public File findByfileName(String originalFileName) {
		return fileRepository.findByOriginalFileName(originalFileName).orElseGet(null);
	}
}
