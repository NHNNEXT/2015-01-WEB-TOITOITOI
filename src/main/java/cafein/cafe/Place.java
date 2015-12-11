package cafein.cafe;

public class Place {

	private int placeId;
	private String name;
	
	public Place(){
		
	}

	public Place(int placeId, String name) {
		super();
		this.placeId = placeId;
		this.name = name;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
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
		result = prime * result + placeId;
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
		Place other = (Place) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (placeId != other.placeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", name=" + name + "]";
	}

}
