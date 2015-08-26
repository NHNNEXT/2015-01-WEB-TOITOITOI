package cafein.post;

public class Post {
	private int pid;
	private String contents;
	private String creattime;
	private int cid;
	private int liked;
	
	public Post(String contents) {
		super();
		this.contents = contents;
	}	
	public Post(int pid, String contents, String creattime, int liked) {
		super();
		this.pid = pid;
		this.contents = contents;
		this.creattime = creattime;
		this.liked = liked;
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
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getLiked() {
		return liked;
	}
	public void setLiked(int liked) {
		this.liked = liked;
	}
	
}
