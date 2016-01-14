package cafein.post;

import java.util.List;

import cafein.reply.Reply;

public class Post {

	private Integer id;
	private String content;
	private String createdtime;
	private List<Reply> replyList;
	private Integer likes;
	private Integer dearId;

	public Post() {}

	public Post(String content) {
		this.content = content;
	}
	public Post(String content, Integer dearId) {
		this.content = content;
		this.dearId = dearId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	public Integer getDearId() {
		return dearId;
	}
	public void setDearId(Integer dearId) {
		this.dearId = dearId;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", content=" + content + ", dearId=" + dearId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dearId == null) ? 0 : dearId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Post other = (Post) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dearId == null) {
			if (other.dearId != null)
				return false;
		} else if (!dearId.equals(other.dearId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
