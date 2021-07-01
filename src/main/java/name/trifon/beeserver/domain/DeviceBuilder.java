package name.trifon.beeserver.domain;


//@Trifon
/**
 * A Builder for the Device entity.
 */
public class DeviceBuilder {

    private String code;

    private String name;

    private Boolean active= Boolean.FALSE;

    private String description;

    public String getCode() {
        return code;
    }

    public DeviceBuilder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public DeviceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public DeviceBuilder active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public DeviceBuilder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }


	public Device build() {
		return new Device()
			.code(this.getCode())
			.name(this.getName())
			.active(this.isActive())
			.description(this.getDescription())
		;
	}

    @Override
    public String toString() {
        return "DeviceBuilder{" +
            "  code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
