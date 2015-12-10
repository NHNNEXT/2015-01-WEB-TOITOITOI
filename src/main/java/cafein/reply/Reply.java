package cafein.reply;

public class Reply {
	private int reId;
	private String replyContent;
	private String replyTime; 
	private int liked;
	private int pid;
	
	
	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}
	
	public Reply(int reId, String replyContent, String replyTime, int liked) {
		super();
		this.reId = reId;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
		this.liked = liked;
	}
	public Reply (int pid, String content){
		this.pid = pid;
		this.replyContent = content;
	}
	public Reply(String content) {
		super();
		replyContent = content;
	}
	
	public Reply(int replyId) {
		reId = replyId;
	}
	
	public int getReId() {
		return reId;
	}
	public void setReId(int reId) {
		this.reId = reId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + liked;
		result = prime * result + pid;
		result = prime * result + reId;
		result = prime * result + ((replyContent == null) ? 0 : replyContent.hashCode());
		result = prime * result + ((replyTime == null) ? 0 : replyTime.hashCode());
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
		
		if (pid != other.pid)
			return false;
		if (reId != other.reId)
			return false;
		if (replyContent == null) {
			if (other.replyContent != null)
				return false;
		} else if (!replyContent.equals(other.replyContent))
			return false;
		if (replyTime == null) {
			if (other.replyTime != null)
				return false;
		} else if (!replyTime.equals(other.replyTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reply [reId=" + reId + ", replyContent=" + replyContent + ", replyTime=" + replyTime + ", liked="
				+ liked + ", pid=" + pid + "]";
	}
	
	

}
