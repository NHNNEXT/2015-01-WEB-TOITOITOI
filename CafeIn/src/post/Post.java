package cafein.post;

public class Post {
	private int pid;
	private String contents;
	private String creattime;
	
	public Post(String contents) {
		super();
		this.contents = contents;
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
