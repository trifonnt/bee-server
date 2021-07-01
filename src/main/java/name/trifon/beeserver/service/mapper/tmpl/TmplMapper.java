package name.trifon.beeserver.service.mapper.tmpl;

/**
 * Contract for a generic entityTemplateDTO to entityDTO mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <T> - Template type parameter.
 */

public interface TmplMapper <D, T> {

	D toDto(T template);

	T toTemplate(D dto);

}
