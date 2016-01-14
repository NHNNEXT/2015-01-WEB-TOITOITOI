package cafein.cafe;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Dear {
	@Id
	private String id;
	@Indexed
	private String uri;
	private String name;
	private String placeId;
	private Integer totalPostNum;

	public Dear() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Integer getTotalPostNum() {
		return totalPostNum;
	}

	public void setTotalPostNum(Integer totalPostNum) {
		this.totalPostNum = totalPostNum;
	}

	@Override
	public String toString() {
		return "Dear [uri=" + uri + ", id=" + id + ", name=" + name + "]";
	}
}