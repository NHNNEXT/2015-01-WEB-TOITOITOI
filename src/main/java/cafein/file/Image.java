package cafein.file;

import org.springframework.data.annotation.Id;

public class Image {
	@Id
	private String id;
	private String originalName;
	private String storedName;
	private String storedTime;
	
	public Image() {}

	public Image(String originalFilename, String storedFilename) {
		this.originalName = originalFilename;
		this.storedName = storedFilename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	public String getStoredTime() {
		return storedTime;
	}

	public void setStoredTime(String storedTime) {
		this.storedTime = storedTime;
	}
	
}
