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
 * Criteria class for the {@link name.trifon.beeserver.domain.Beehive} entity. This class is used
 * in {@link name.trifon.beeserver.web.rest.BeehiveResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /beehives?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BeehiveCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private BooleanFilter active;

    private StringFilter description;

    private IntegerFilter yearCreated;

    private IntegerFilter monthCreated;

    private LongFilter apiaryId;

    public BeehiveCriteria() {
    }

    public BeehiveCriteria(BeehiveCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.yearCreated = other.yearCreated == null ? null : other.yearCreated.copy();
        this.monthCreated = other.monthCreated == null ? null : other.monthCreated.copy();
        this.apiaryId = other.apiaryId == null ? null : other.apiaryId.copy();
    }

    @Override
    public BeehiveCriteria copy() {
        return new BeehiveCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(IntegerFilter yearCreated) {
        this.yearCreated = yearCreated;
    }

    public IntegerFilter getMonthCreated() {
        return monthCreated;
    }

    public void setMonthCreated(IntegerFilter monthCreated) {
        this.monthCreated = monthCreated;
    }

    public LongFilter getApiaryId() {
        return apiaryId;
    }

    public void setApiaryId(LongFilter apiaryId) {
        this.apiaryId = apiaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BeehiveCriteria that = (BeehiveCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(active, that.active) &&
            Objects.equals(description, that.description) &&
            Objects.equals(yearCreated, that.yearCreated) &&
            Objects.equals(monthCreated, that.monthCreated) &&
            Objects.equals(apiaryId, that.apiaryId)
          ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        active,
        description,
        yearCreated,
        monthCreated,
        apiaryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeehiveCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (yearCreated != null ? "yearCreated=" + yearCreated + ", " : "") +
                (monthCreated != null ? "monthCreated=" + monthCreated + ", " : "") +
                (apiaryId != null ? "apiaryId=" + apiaryId + ", " : "") +
            "}";
    }

}
