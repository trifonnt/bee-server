package name.trifon.beeserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

//import name.trifon.beeserver.domain.util.AttributeEncryptor; //@Trifon-manual

/**
 * A Beehive.
 */
@Entity
@Table(name = "beehive", uniqueConstraints = {@UniqueConstraint(columnNames={"apiary_id", "code"})})
public class Beehive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//@Trifon
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "beehive_id_generator")
//    @TableGenerator(name = "beehive_id_generator"
//    	, table = "identifier", pkColumnName = "sequence_name"
//    	, valueColumnName = "next_value", pkColumnValue = "beehive_ids", allocationSize = 1)
    private Long id;



    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code; //@Trifon

    @NotNull
    @Column(name = "name", nullable = false)
    private String name; //@Trifon

    @Column(name = "active")
    private Boolean active = Boolean.TRUE; //@Trifon

    @Column(name = "description")
    private String description; //@Trifon

    @Column(name = "year_created")
    private Integer yearCreated = 2021; //@Trifon

    @Column(name = "month_created")
    private Integer monthCreated = 1; //@Trifon


	@PreRemove
	private void preRemove() {
		if (apiary != null) {
//			System.err.println("TRIFON - PreRemove -- Beehive");
//			apiary.removeBeehive(this);
		}
	}

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "beehives", allowSetters = true)
    private Apiary apiary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Beehive code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Beehive name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Beehive active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Beehive description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYearCreated() {
        return yearCreated;
    }

    public Beehive yearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
        return this;
    }

    public void setYearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public Integer getMonthCreated() {
        return monthCreated;
    }

    public Beehive monthCreated(Integer monthCreated) {
        this.monthCreated = monthCreated;
        return this;
    }

    public void setMonthCreated(Integer monthCreated) {
        this.monthCreated = monthCreated;
    }

    public Apiary getApiary() {
        return apiary;
    }

    public Beehive apiary(Apiary apiary) {
        this.apiary = apiary;
        return this;
    }

    public void setApiary(Apiary apiary) {
        this.apiary = apiary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beehive)) {
            return false;
        }
        return id != null && id.equals(((Beehive) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beehive{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", yearCreated=" + getYearCreated() +
            ", monthCreated=" + getMonthCreated() +
            "}";
    }
}
