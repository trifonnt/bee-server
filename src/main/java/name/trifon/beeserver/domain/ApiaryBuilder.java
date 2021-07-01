package name.trifon.beeserver.domain;


//@Trifon
/**
 * A Builder for the Apiary entity.
 */
public class ApiaryBuilder {

    private String code;

    private String name;

    private Boolean active= Boolean.FALSE;

    private String description;

    private String address;

    private User owner;

    public String getCode() {
        return code;
    }

    public ApiaryBuilder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ApiaryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiaryBuilder active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public ApiaryBuilder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public ApiaryBuilder address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public User getOwner() {
        return owner;
    }

    public ApiaryBuilder owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

	public Apiary build() {
		return new Apiary()
			.code(this.getCode())
			.name(this.getName())
			.active(this.isActive())
			.description(this.getDescription())
			.address(this.getAddress())
			.owner(this.getOwner())
		;
	}

    @Override
    public String toString() {
        return "ApiaryBuilder{" +
            "  code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
