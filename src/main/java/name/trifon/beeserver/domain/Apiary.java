package name.trifon.beeserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

//import name.trifon.beeserver.domain.util.AttributeEncryptor; //@Trifon-manual

/**
 * A Apiary.
 */
@Entity
@Table(name = "apiary", uniqueConstraints = {@UniqueConstraint(columnNames={"owner_id", "code"})})
public class Apiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//@Trifon
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "apiary_id_generator")
//    @TableGenerator(name = "apiary_id_generator"
//    	, table = "identifier", pkColumnName = "sequence_name"
//    	, valueColumnName = "next_value", pkColumnValue = "apiary_ids", allocationSize = 1)
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

    @Column(name = "address")
    private String address; //@Trifon


	@PreRemove
	private void preRemove() {
		if (owner != null) {
//			System.err.println("TRIFON - PreRemove -- Apiary");
//			owner.removeApiary(this);
		}
	}

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "apiaries", allowSetters = true)
    private User owner;

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

    public Apiary code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Apiary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Apiary active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Apiary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Apiary address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public Apiary owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apiary)) {
            return false;
        }
        return id != null && id.equals(((Apiary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apiary{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
