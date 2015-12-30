package cafein.cafe;

public class CandidateDear {
	
	private Integer placeId;
	private String name;
	
	public CandidateDear(){
		
	}
	public CandidateDear(Integer placeId, String name) {
		super();
		this.placeId = placeId;
		this.name = name;
	}

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((placeId == null) ? 0 : placeId.hashCode());
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
		CandidateDear other = (CandidateDear) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (placeId == null) {
			if (other.placeId != null)
				return false;
		} else if (!placeId.equals(other.placeId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CandidateDear [placeId=" + placeId + ", name=" + name + "]";
	}
	
	
}
