package name.trifon.beeserver.repository;

import name.trifon.beeserver.domain.Apiary;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param; //@Trifon - childSequence
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; //@Trifon

/**
 * Spring Data  repository for the Apiary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiaryRepository extends JpaRepository<Apiary, Long>, JpaSpecificationExecutor<Apiary> {
// @Trifon -
    Optional<Apiary> findOneByOwnerIdAndCode(Long ownerId, String code);

    Optional<Apiary> findOneByCode(String code);

    @Query("select apiary from Apiary apiary where apiary.owner.login = ?#{principal.username}")
    List<Apiary> findByOwnerIsCurrentUser();
}
