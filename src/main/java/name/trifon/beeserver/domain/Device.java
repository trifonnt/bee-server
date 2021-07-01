package name.trifon.beeserver.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

//import name.trifon.beeserver.domain.util.AttributeEncryptor; //@Trifon

/**
 * A Device.
 */
@Entity
@Table(name = "device", uniqueConstraints = {@UniqueConstraint(columnNames={})})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//@Trifon
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "device_id_generator")
//    @TableGenerator(name = "device_id_generator"
//    	, table = "identifier", pkColumnName = "sequence_name"
//    	, valueColumnName = "next_value", pkColumnValue = "device_ids", allocationSize = 1)
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


	@PreRemove
	private void preRemove() {
	}

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

    public Device code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Device name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Device active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Device description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
