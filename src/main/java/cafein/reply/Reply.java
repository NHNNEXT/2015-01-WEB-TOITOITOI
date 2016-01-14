package cafein.reply;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Reply {
	@Id
	private String id;
	@Indexed
	private String postId;
	private String content;
	private String createdTime;
	private Integer likes;
	private List<String> imageIds;

	public Reply() {
	}

	public Reply(String content, String postId) {
		super();
		this.content = content;
		this.postId = postId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public List<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<String> imageIds) {
		this.imageIds = imageIds;
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", createdTime=" + createdTime + "]";
	}
}
