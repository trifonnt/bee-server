package name.trifon.beeserver.repository;

import name.trifon.beeserver.domain.Beehive;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param; //@Trifon - childSequence
import org.springframework.stereotype.Repository;
import java.util.Optional; //@Trifon

/**
 * Spring Data  repository for the Beehive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeehiveRepository extends JpaRepository<Beehive, Long>, JpaSpecificationExecutor<Beehive> {
// @Trifon -
    Optional<Beehive> findOneByApiaryIdAndCode(Long apiaryId, String code);

    Optional<Beehive> findOneByCode(String code);
}
