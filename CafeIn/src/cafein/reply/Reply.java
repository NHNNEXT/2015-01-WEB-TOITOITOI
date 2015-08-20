package cafein.reply;

public class Reply {
	private int reId;
	private String replyContent;
	private String replyTime; 
	
	
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
