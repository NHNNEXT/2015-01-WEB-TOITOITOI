package cafein.reply;

public class Reply {
	
	private Integer id;
	private String content;
	private String createdtime;
	private Integer likes;
	private Integer postId;
	
	public Reply () {
		
	}
	
	public Reply(Integer replyId) {
		this.id = replyId;
	}
	
	public Reply(String content) {
		this.content = content;
	}
	
	public Reply(Integer postId, String content) {
		this.postId = postId;
		this.content = content;
	}
	
	public Reply(Integer replyId, String replyContent, String createdime, Integer likes, Integer postId) {
		this.id = replyId;
		this.content = replyContent;
		this.createdtime = createdime;
		this.likes = likes;
		this.postId = postId;
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

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
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
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdtime == null) ? 0 : createdtime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
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
		Reply other = (Reply) obj;
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
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reply [replyId=" + id +"content=" + content + ", createdtime=" + createdtime
				+ ", likes=" + likes + ", postId=" + postId + "]";
	}

	
}
