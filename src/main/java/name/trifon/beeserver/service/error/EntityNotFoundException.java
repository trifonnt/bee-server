package name.trifon.beeserver.service.error;

//@Trifon
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Long id;
	
//	public EntityNotFoundException() {
//		super("Not found entity");
//	}

	public EntityNotFoundException(Long id) {
		super("Not found entity with id:" + id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
