package cafein.file;

import org.springframework.stereotype.Component;

@Component
public class ImageFile {
	
	private Integer id;
	private String original_filename;
	private String stored_filename;
	private String stored_time;
	private Integer post_id;
	
	public ImageFile() {
	}

	public ImageFile(String original_filename, String stored_filename) {
		super();
		this.original_filename = original_filename;
		this.stored_filename = stored_filename;
	}

	public ImageFile(String original_filename, String stored_filename, Integer post_id) {
		super();
		this.original_filename = original_filename;
		this.stored_filename = stored_filename;
		this.post_id = post_id;
	}

	public ImageFile(Integer id, String original_filename, String stored_filename, String stored_time, Integer post_id) {
		super();
		this.id = id;
		this.original_filename = original_filename;
		this.stored_filename = stored_filename;
		this.stored_time = stored_time;
		this.post_id = post_id;
	}

	/* Getter And Setter */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOriginal_filename() {
		return original_filename;
	}
	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}
	public String getStored_filename() {
		return stored_filename;
	}
	public void setStored_filename(String stored_filename) {
		this.stored_filename = stored_filename;
	}
	public String getStored_time() {
		return stored_time;
	}
	public void setStored_time(String stored_time) {
		this.stored_time = stored_time;
	}
	public Integer getPost_id() {
		return post_id;
	}
	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((original_filename == null) ? 0 : original_filename.hashCode());
		result = prime * result + ((post_id == null) ? 0 : post_id.hashCode());
		result = prime * result + ((stored_filename == null) ? 0 : stored_filename.hashCode());
		result = prime * result + ((stored_time == null) ? 0 : stored_time.hashCode());
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
		if (original_filename == null) {
			if (other.original_filename != null)
				return false;
		} else if (!original_filename.equals(other.original_filename))
			return false;
		if (post_id == null) {
			if (other.post_id != null)
				return false;
		} else if (!post_id.equals(other.post_id))
			return false;
		if (stored_filename == null) {
			if (other.stored_filename != null)
				return false;
		} else if (!stored_filename.equals(other.stored_filename))
			return false;
		if (stored_time == null) {
			if (other.stored_time != null)
				return false;
		} else if (!stored_time.equals(other.stored_time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageFile [id=" + id + ", original_filename=" + original_filename + ", stored_filename="
				+ stored_filename + ", stored_time=" + stored_time + ", post_id=" + post_id + "]";
	}
	
	
}
