package name.trifon.beeserver.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; //@Trifon
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link name.trifon.beeserver.domain.MeasurementRecord} entity.
 */
public class MeasurementRecordDTO implements Serializable {

	//@Trifon
	private static final long serialVersionUID = 1L;

//	private RecordSource recordSource;

//	private String code;

//	public String getCode() {
//		return code;
//	}
//	public void setCode(String code) {
//		this.code = code;
//	}



    
    private Long id;

    private Instant recordedAt; //@Trifon

    private Integer inboundCount; //@Trifon

    private Integer outboundCount; //@Trifon


    private Long deviceId;

    private String deviceCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Integer getInboundCount() {
        return inboundCount;
    }

    public void setInboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
    }

    public Integer getOutboundCount() {
        return outboundCount;
    }

    public void setOutboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeasurementRecordDTO)) {
            return false;
        }

        return id != null && id.equals(((MeasurementRecordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeasurementRecordDTO{" +
            "id=" + getId() +
            ", recordedAt='" + getRecordedAt() + "'" +
            ", inboundCount=" + getInboundCount() +
            ", outboundCount=" + getOutboundCount() +
            ", deviceId=" + getDeviceId() +
            ", deviceCode='" + getDeviceCode() + "'" +
            "}";
    }
}
