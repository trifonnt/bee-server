package name.trifon.beeserver.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter; //@Trifon

/**
 * Criteria class for the {@link name.trifon.beeserver.domain.MeasurementRecord} entity. This class is used
 * in {@link name.trifon.beeserver.web.rest.MeasurementRecordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /measurement-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MeasurementRecordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter recordedAt;

    private IntegerFilter inboundCount;

    private IntegerFilter outboundCount;

    private LongFilter deviceId;

    public MeasurementRecordCriteria() {
    }

    public MeasurementRecordCriteria(MeasurementRecordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.recordedAt = other.recordedAt == null ? null : other.recordedAt.copy();
        this.inboundCount = other.inboundCount == null ? null : other.inboundCount.copy();
        this.outboundCount = other.outboundCount == null ? null : other.outboundCount.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
    }

    @Override
    public MeasurementRecordCriteria copy() {
        return new MeasurementRecordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(InstantFilter recordedAt) {
        this.recordedAt = recordedAt;
    }

    public IntegerFilter getInboundCount() {
        return inboundCount;
    }

    public void setInboundCount(IntegerFilter inboundCount) {
        this.inboundCount = inboundCount;
    }

    public IntegerFilter getOutboundCount() {
        return outboundCount;
    }

    public void setOutboundCount(IntegerFilter outboundCount) {
        this.outboundCount = outboundCount;
    }

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
        this.deviceId = deviceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MeasurementRecordCriteria that = (MeasurementRecordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(recordedAt, that.recordedAt) &&
            Objects.equals(inboundCount, that.inboundCount) &&
            Objects.equals(outboundCount, that.outboundCount) &&
            Objects.equals(deviceId, that.deviceId)
          ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        recordedAt,
        inboundCount,
        outboundCount,
        deviceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeasurementRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (recordedAt != null ? "recordedAt=" + recordedAt + ", " : "") +
                (inboundCount != null ? "inboundCount=" + inboundCount + ", " : "") +
                (outboundCount != null ? "outboundCount=" + outboundCount + ", " : "") +
                (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
            "}";
    }

}
