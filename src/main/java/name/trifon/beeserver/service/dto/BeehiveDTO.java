package name.trifon.beeserver.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; //@Trifon
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link name.trifon.beeserver.domain.Beehive} entity.
 */
public class BeehiveDTO implements Serializable {

	//@Trifon
	private static final long serialVersionUID = 1L;



    
    private Long id;

    @NotNull
    private String code; //@Trifon

    @NotNull
    private String name; //@Trifon

    private Boolean active = Boolean.TRUE; //@Trifon

    private String description; //@Trifon

    private Integer yearCreated = 2021; //@Trifon

    private Integer monthCreated = 1; //@Trifon


    private Long apiaryId;

    private String apiaryCode;
    
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

    public Integer getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public Integer getMonthCreated() {
        return monthCreated;
    }

    public void setMonthCreated(Integer monthCreated) {
        this.monthCreated = monthCreated;
    }

    public Long getApiaryId() {
        return apiaryId;
    }

    public void setApiaryId(Long apiaryId) {
        this.apiaryId = apiaryId;
    }

    public String getApiaryCode() {
        return apiaryCode;
    }

    public void setApiaryCode(String apiaryCode) {
        this.apiaryCode = apiaryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeehiveDTO)) {
            return false;
        }

        return id != null && id.equals(((BeehiveDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeehiveDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", yearCreated=" + getYearCreated() +
            ", monthCreated=" + getMonthCreated() +
            ", apiaryId=" + getApiaryId() +
            ", apiaryCode='" + getApiaryCode() + "'" +
            "}";
    }
}
