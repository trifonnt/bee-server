package name.trifon.beeserver.repository;

import name.trifon.beeserver.domain.MeasurementRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param; //@Trifon - childSequence
import org.springframework.stereotype.Repository;
import java.util.Optional; //@Trifon

/**
 * Spring Data  repository for the MeasurementRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeasurementRecordRepository extends JpaRepository<MeasurementRecord, Long>, JpaSpecificationExecutor<MeasurementRecord> {
// @Trifon -

}
