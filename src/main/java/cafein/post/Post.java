package cafein.post;

import java.util.List;

import cafein.reply.Reply;

public class Post {

	private Integer id;
	private String name;
	private String content;
	private String createdtime;
	private List<Reply> replyList;
	private Integer placeId;
	private Integer likes;
	private Integer dearId;

	public Post() {

	}

	public Post(String dear, String content, Integer placeId) {
		super();
		this.name = dear;
		this.content = content;
		this.placeId = placeId;
	}

	public Post(int postId, String dear, String content, Integer placeId) {
		super();
		this.id = postId;
		this.name = dear;
		this.content = content;
		this.placeId = placeId;
	}
	
	
	public Post(Integer id, String content, String createdtime, Integer placeId, Integer likes, Integer dearId) {
		super();
		this.id = id;
		this.content = content;
		this.createdtime = createdtime;
		this.placeId = placeId;
		this.likes = likes;
		this.dearId = dearId;
	}

	public Post(int postId, int placeId, String dear, String content, String createdtime, Integer likes) {
		super();
		this.id = postId;
		this.placeId = placeId;
		this.name = dear;
		this.content = content;
		this.createdtime = createdtime;
		this.likes = likes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String dear) {
		this.name = dear;
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

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdtime == null) ? 0 : createdtime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((dearId == null) ? 0 : dearId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((placeId == null) ? 0 : placeId.hashCode());
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
		if (createdtime == null) {
			if (other.createdtime != null)
				return false;
		} else if (!createdtime.equals(other.createdtime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
			return false;
		if (placeId == null) {
			if (other.placeId != null)
				return false;
		} else if (!placeId.equals(other.placeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", dear=" + name + ", content=" + content + ", createdtime=" + createdtime
				+ ", replyList=" + replyList + ", placeId=" + placeId + ", likes=" + likes + ", dearId=" + dearId
				+ ", getId()=" + getId() + ", getDear()=" + getName() + ", getContent()=" + getContent()
				+ ", getCreatedtime()=" + getCreatedtime() + ", getReplyList()=" + getReplyList() + ", getPlaceId()="
				+ getPlaceId() + ", getLikes()=" + getLikes() + ", getDearId()=" + getDearId() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}
	
	
}
