package cafein.file;

public class ImageFile {
	
	private Integer id;
	private String originalFilename;
	private String storedFilename;
	private String storedTime;
	private Integer postId;
	
	public ImageFile() {}

	public ImageFile(String originalFilename, String storedFilename) {
		this.originalFilename = originalFilename;
		this.storedFilename = storedFilename;
	}

	/* Getter And Setter */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getStoredFilename() {
		return storedFilename;
	}
	public void setStoredFilename(String storedFilename) {
		this.storedFilename = storedFilename;
	}
	public String getStoredTime() {
		return storedTime;
	}
	public void setStoredTime(String storedTime) {
		this.storedTime = storedTime;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((originalFilename == null) ? 0 : originalFilename.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((storedFilename == null) ? 0 : storedFilename.hashCode());
		result = prime * result + ((storedTime == null) ? 0 : storedTime.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageFile other = (ImageFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originalFilename == null) {
			if (other.originalFilename != null)
				return false;
		} else if (!originalFilename.equals(other.originalFilename))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (storedFilename == null) {
			if (other.storedFilename != null)
				return false;
		} else if (!storedFilename.equals(other.storedFilename))
			return false;
		if (storedTime == null) {
			if (other.storedTime != null)
				return false;
		} else if (!storedTime.equals(other.storedTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageFile [id=" + id + ", original_filename=" + originalFilename + ", stored_filename="
				+ storedFilename + ", stored_time=" + storedTime + ", post_id=" + postId + "]";
	}
	
	
}
