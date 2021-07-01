package name.trifon.beeserver.domain;

import java.time.Instant;

//@Trifon
/**
 * A Builder for the MeasurementRecord entity.
 */
public class MeasurementRecordBuilder {

    private Instant recordedAt;

    private Integer inboundCount;

    private Integer outboundCount;

    private Device device;

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public MeasurementRecordBuilder recordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
        return this;
    }

    public void setRecordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Integer getInboundCount() {
        return inboundCount;
    }

    public MeasurementRecordBuilder inboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
        return this;
    }

    public void setInboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
    }

    public Integer getOutboundCount() {
        return outboundCount;
    }

    public MeasurementRecordBuilder outboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
        return this;
    }

    public void setOutboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
    }


    public Device getDevice() {
        return device;
    }

    public MeasurementRecordBuilder device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

	public MeasurementRecord build() {
		return new MeasurementRecord()
			.recordedAt(this.getRecordedAt())
			.inboundCount(this.getInboundCount())
			.outboundCount(this.getOutboundCount())
			.device(this.getDevice())
		;
	}

    @Override
    public String toString() {
        return "MeasurementRecordBuilder{" +
            "  recordedAt='" + getRecordedAt() + "'" +
            ", inboundCount=" + getInboundCount() +
            ", outboundCount=" + getOutboundCount() +
            ", device='" + getDevice() + "'" +
            "}";
    }
}
