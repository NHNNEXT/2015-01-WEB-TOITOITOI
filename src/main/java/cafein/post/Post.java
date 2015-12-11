package cafein.post;

import java.util.List;

import cafein.reply.Reply;

public class Post {

	private int postId;
	private String dear;
	private String content;
	private String createdtime;
	private List<Reply> replyList;
	private int placeId;
	private int likes;

	public Post() {

	}

	public Post(String dear, String content, int placeId) {
		super();
		this.dear = dear;
		this.content = content;
		this.placeId = placeId;
	}

	public Post(int postId, String dear, String content, int placeId) {
		super();
		this.postId = postId;
		this.dear = dear;
		this.content = content;
		this.placeId = placeId;
	}
	
	
	public Post(int postId, String dear, String content, String createdtime, int likes) {
		super();
		this.postId = postId;
		this.dear = dear;
		this.content = content;
		this.createdtime = createdtime;
		this.likes = likes;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getDear() {
		return dear;
	}

	public void setDear(String dear) {
		this.dear = dear;
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

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdtime == null) ? 0 : createdtime.hashCode());
		result = prime * result + ((dear == null) ? 0 : dear.hashCode());
		result = prime * result + likes;
		result = prime * result + placeId;
		result = prime * result + postId;
		result = prime * result + ((replyList == null) ? 0 : replyList.hashCode());
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
		if (dear == null) {
			if (other.dear != null)
				return false;
		} else if (!dear.equals(other.dear))
			return false;
		if (likes != other.likes)
			return false;
		if (placeId != other.placeId)
			return false;
		if (postId != other.postId)
			return false;
		if (replyList == null) {
			if (other.replyList != null)
				return false;
		} else if (!replyList.equals(other.replyList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", dear=" + dear + ", content=" + content + ", createdtime=" + createdtime
				+ ", replyList=" + replyList + ", placeId=" + placeId + ", likes=" + likes + "]";
	}
	
	
	

}
