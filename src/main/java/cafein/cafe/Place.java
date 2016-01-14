package cafein.cafe;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Place {
	@Id
	private String id;
	@Indexed
	private String uri;
	private String name;
	private Integer totalDearNum;

	public Place() {
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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getTotalDearNum() {
		return totalDearNum;
	}

	public void setTotalDearNum(Integer totalDearNum) {
		this.totalDearNum = totalDearNum;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", uri=" + uri + "]";
	}
}
