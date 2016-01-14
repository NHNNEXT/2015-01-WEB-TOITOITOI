package cafein.post;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import cafein.reply.Reply;

public class Post {
	@Id
	private String id;
	@Indexed
	private String dearId;
	private String content;
	private Integer likes;
	private String createdTime;
	private Integer totalReplyNum;
	private List<String> imageIds;
	private List<Reply> replyList;
	
	public Post () {}
	public Post (String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDearId() {
		return dearId;
	}

	public void setDearId(String dearId) {
		this.dearId = dearId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getTotalReplyNum() {
		return totalReplyNum;
	}

	public void setTotalReplyNum(Integer totalReplyNum) {
		this.totalReplyNum = totalReplyNum;
	}

	public List<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<String> imageIds) {
		this.imageIds = imageIds;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", dearId=" + dearId + ", content=" + content + ", createdTime=" + createdTime + "]";
	}
}
