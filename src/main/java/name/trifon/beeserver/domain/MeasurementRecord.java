package name.trifon.beeserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

//import name.trifon.beeserver.domain.util.AttributeEncryptor; //@Trifon-manual

/**
 * A MeasurementRecord.
 */
@Entity
@Table(name = "measurement_record", uniqueConstraints = {@UniqueConstraint(columnNames={})})
public class MeasurementRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//@Trifon
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "measurement_record_id_generator")
//    @TableGenerator(name = "measurement_record_id_generator"
//    	, table = "identifier", pkColumnName = "sequence_name"
//    	, valueColumnName = "next_value", pkColumnValue = "measurement_record_ids", allocationSize = 1)
    private Long id;

//	private RecordSource recordSource; //@Trifon

//	private String code;

//	public String getCode() {
//		return code;
//	}
//	public void setCode(String code) {
//		this.code = code;
//	}

	@PrePersist
	public void prePersist() {
//		if (code == null || code.isEmpty()) {
//			code = java.util.UUID.randomUUID().toString();
//		}
	}


    @Column(name = "recorded_at")
    private Instant recordedAt; //@Trifon

    @Column(name = "inbound_count")
    private Integer inboundCount; //@Trifon

    @Column(name = "outbound_count")
    private Integer outboundCount; //@Trifon


	@PreRemove
	private void preRemove() {
		if (device != null) {
//			System.err.println("TRIFON - PreRemove -- MeasurementRecord");
//			device.removeMeasurementRecord(this);
		}
	}

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "measurementRecords", allowSetters = true)
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public MeasurementRecord recordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
        return this;
    }

    public void setRecordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Integer getInboundCount() {
        return inboundCount;
    }

    public MeasurementRecord inboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
        return this;
    }

    public void setInboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
    }

    public Integer getOutboundCount() {
        return outboundCount;
    }

    public MeasurementRecord outboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
        return this;
    }

    public void setOutboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
    }

    public Device getDevice() {
        return device;
    }

    public MeasurementRecord device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeasurementRecord)) {
            return false;
        }
        return id != null && id.equals(((MeasurementRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeasurementRecord{" +
            "id=" + getId() +
            ", recordedAt='" + getRecordedAt() + "'" +
            ", inboundCount=" + getInboundCount() +
            ", outboundCount=" + getOutboundCount() +
            "}";
    }
}
