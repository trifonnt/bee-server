package name.trifon.beeserver.repository;

import name.trifon.beeserver.domain.Device;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param; //@Trifon - childSequence
import org.springframework.stereotype.Repository;
import java.util.Optional; //@Trifon

/**
 * Spring Data  repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device> {
// @Trifon -
    Optional<Device> findOneByCode(String code);
}
