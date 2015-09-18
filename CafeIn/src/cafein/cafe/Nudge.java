package cafein.cafe;

public class Nudge {
	private int nudgeId;
	private String contents;
	
	public Nudge(int cid, String contents) {
		super();
		this.nudgeId = nudgeId;
		this.contents = contents;
	}

	public int getNudgeId() {
		return nudgeId;
	}

	public void setNudgeId(int nudgeId) {
		this.nudgeId = nudgeId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Nudge [nudgeId=" + nudgeId + ", contents=" + contents + "]";
	}
	
	
	
	
}
