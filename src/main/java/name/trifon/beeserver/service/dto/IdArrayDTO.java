package name.trifon.beeserver.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// @Trifon
public class IdArrayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Long> ids = new HashSet<>();


    public IdArrayDTO() {
        // Empty constructor needed for Jackson.
    }

	public Set<Long> getIds() {
		return ids;
	}

	public IdArrayDTO ids(Set<Long> ids) {
		this.ids = ids;
		return this;
	}

	public IdArrayDTO addId(Long id) {
		this.ids.add(id);
		return this;
	}

	public IdArrayDTO removeId(Long id) {
		this.ids.remove(id);
		return this;
	}

	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "IdArrayDTO{"
				+ "ids=" + ids +
				"}";
	}
}
