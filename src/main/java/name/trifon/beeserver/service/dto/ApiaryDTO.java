package name.trifon.beeserver.service.dto;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties; //@Trifon-manual
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link name.trifon.beeserver.domain.Apiary} entity.
 */
public class ApiaryDTO implements Serializable {

	//@Trifon
	private static final long serialVersionUID = 1L;



    
    private Long id;

    @NotNull
    private String code; //@Trifon

    @NotNull
    private String name; //@Trifon

    private Boolean active = Boolean.TRUE; //@Trifon

    private String description; //@Trifon

    private String address; //@Trifon


    private Long ownerId;

    private String ownerLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiaryDTO)) {
            return false;
        }

        return id != null && id.equals(((ApiaryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiaryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            "}";
    }
}
