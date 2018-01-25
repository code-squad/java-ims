package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import support.domain.AbstractEntity;
@Entity
@Table(name="files")
public class File extends AbstractEntity {
	
	// 컨트롤러를 거치기 때문에 굳이 path를 데이터베이스에 저장할 필요 없다.
	@Column(name="file_name", length=100, nullable=false)
	private String fileName;

	@Column(name="file_original_name", nullable=false)
	private String originalFileName;
	
	@Column(name="file_contentType", nullable=false)
	private String contentType;
		
	public File() {
		
	}
	
	public File(long id, String fileName, String originalFileName, String contentType) {
		super(id);
		this.fileName = fileName;
		this.originalFileName = originalFileName;
		this.contentType = contentType;
	}
	
	public File(String fileName, String originalFileName, String contentType) {
		this(0L, fileName, originalFileName, contentType);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "File [fileName=" + fileName + ", originalFileName=" + originalFileName + ", contentType=" + contentType
				+ ", toString()=" + super.toString() + "]";
	}

}
