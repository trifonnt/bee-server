package name.trifon.beeserver.domain;


//@Trifon
/**
 * A Builder for the Beehive entity.
 */
public class BeehiveBuilder {

    private String code;

    private String name;

    private Boolean active= Boolean.FALSE;

    private String description;

    private Integer yearCreated;

    private Integer monthCreated;

    private Apiary apiary;

    public String getCode() {
        return code;
    }

    public BeehiveBuilder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public BeehiveBuilder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public BeehiveBuilder active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public BeehiveBuilder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYearCreated() {
        return yearCreated;
    }

    public BeehiveBuilder yearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
        return this;
    }

    public void setYearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public Integer getMonthCreated() {
        return monthCreated;
    }

    public BeehiveBuilder monthCreated(Integer monthCreated) {
        this.monthCreated = monthCreated;
        return this;
    }

    public void setMonthCreated(Integer monthCreated) {
        this.monthCreated = monthCreated;
    }


    public Apiary getApiary() {
        return apiary;
    }

    public BeehiveBuilder apiary(Apiary apiary) {
        this.apiary = apiary;
        return this;
    }

    public void setApiary(Apiary apiary) {
        this.apiary = apiary;
    }

	public Beehive build() {
		return new Beehive()
			.code(this.getCode())
			.name(this.getName())
			.active(this.isActive())
			.description(this.getDescription())
			.yearCreated(this.getYearCreated())
			.monthCreated(this.getMonthCreated())
			.apiary(this.getApiary())
		;
	}

    @Override
    public String toString() {
        return "BeehiveBuilder{" +
            "  code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", yearCreated=" + getYearCreated() +
            ", monthCreated=" + getMonthCreated() +
            ", apiary='" + getApiary() + "'" +
            "}";
    }
}
