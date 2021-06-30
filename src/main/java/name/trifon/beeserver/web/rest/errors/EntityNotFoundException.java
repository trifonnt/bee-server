package name.trifon.beeserver.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

//@Trifon
public class EntityNotFoundException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;

	private final String entityName;

	private final Long id;

	public EntityNotFoundException(String entityName, Long id) {
		super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, "Entity not found", Status.NOT_FOUND);
		this.entityName = entityName;
		this.id = id;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getErrorKey() {
		return id == null ? "null" : id.toString();
	}
}
