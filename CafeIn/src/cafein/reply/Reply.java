package cafein.reply;

public class Reply {
	private int reId;
	private String replyContent;
	private String replyTime; 
	private int liked;
	
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

	public Reply(String content) {
		super();
		replyContent = content;
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

}
