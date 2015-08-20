package cafein.post;

import java.util.List;

import cafein.reply.Reply;

public class Post {
	private int pid;
	private String contents;
	private String creattime;
	private List<Reply> replyList;
	
	public Post(int pid, String contents) {
		super();
		this.pid = pid;
		this.contents = contents;
	}
	public Post(String contents) {
		super();
		this.contents = contents;
	}
	public Post(int pid) {
		super();
		this.pid = pid;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
}
