package cafein.post;

import java.util.List;

import cafein.reply.Reply;

public class Post {
	private int pid;
	private String contents;
	private String creattime;
	private List<Reply> replyList;
	private int cid;
	private int liked;
	
	public Post() {
		
	}
	public Post(int cid){
		this.cid = cid;
	}
	public Post(int cid, String contents) {
		this.cid = cid;
		this.contents = contents;
	}	
	public Post(int pid, String contents, String creattime, int liked) {
		this.pid = pid;
		this.contents = contents;
		this.creattime = creattime;
		this.liked = liked;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
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
	@Override
	public String toString() {
		return "Post [pid=" + pid + ", contents=" + contents + ", creattime=" + creattime + ", replyList=" + replyList
				+ ", cid=" + cid + ", liked=" + liked + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + pid;
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
		if (cid != other.cid)
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (pid != other.pid)
			return false;
		return true;
	}
	
}
