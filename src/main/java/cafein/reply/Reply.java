package cafein.reply;

public class Reply {
	
	private int id;
	private String content;
	private String createdtime;
	private int likes;
	private int postId;
	
	public Reply () {
		
	}
	
	public Reply(int replyId) {
		this.id = replyId;
	}
	
	public Reply(String content) {
		this.content = content;
	}
	
	public Reply(int postId, String content) {
		this.postId = postId;
		this.content = content;
	}
	
	public Reply(int replyId, String replyContent, String createdime, int likes, int postId) {
		this.id = replyId;
		this.content = replyContent;
		this.createdtime = createdime;
		this.likes = likes;
		this.postId = postId;
	}

	public int getReplyId() {
		return id;
	}

	public void setReplyId(int replyId) {
		this.id = replyId;
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdtime == null) ? 0 : createdtime.hashCode());
		result = prime * result + likes;
		result = prime * result + postId;
		result = prime * result + id;
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
		if (likes != other.likes)
			return false;
		if (postId != other.postId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reply [replyId=" + id +"content=" + content + ", createdtime=" + createdtime
				+ ", likes=" + likes + ", postId=" + postId + "]";
	}

	
}
