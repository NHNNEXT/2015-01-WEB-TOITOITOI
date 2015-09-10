package cafein.cafe;

public class Cafe {

	private int cid;
	private String name;

	public Cafe(int cid, String name) {
		this.cid = cid;
		this.name = name;
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

	@Override
	public String toString() {
		return "Cafe [cid=" + cid + ", name=" + name + "]";
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
