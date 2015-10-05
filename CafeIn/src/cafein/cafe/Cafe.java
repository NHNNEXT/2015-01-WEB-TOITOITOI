package cafein.cafe;

public class Cafe {

	private int cid;
	private String name;
	private int postNum;
	private double distance;
	
	public Cafe(int cid, String name) {
		this.cid = cid;
		this.name = name;
	}
	public Cafe(int cid, String name, int postNum) {
		this(cid, name);
		this.postNum = postNum;
	}
	public Cafe(int cid, String name, int postNum, double distance) {
		this(cid, name, postNum);
		this.distance = distance;
	}	
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "Cafe [cid=" + cid + ", name=" + name + ", postNum=" + postNum + "]";
	}
	@Override
	public int hashCode() {
		return cid;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Cafe))
			return false;
		return this.cid == ((Cafe)obj).getCid();
	}

	
}
