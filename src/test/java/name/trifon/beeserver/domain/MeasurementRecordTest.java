package name.trifon.beeserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class MeasurementRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurementRecord.class);
        MeasurementRecord measurementRecord1 = new MeasurementRecord();
        measurementRecord1.setId(1L);
        MeasurementRecord measurementRecord2 = new MeasurementRecord();
        measurementRecord2.setId(measurementRecord1.getId());
        assertThat(measurementRecord1).isEqualTo(measurementRecord2);
        measurementRecord2.setId(2L);
        assertThat(measurementRecord1).isNotEqualTo(measurementRecord2);
        measurementRecord1.setId(null);
        assertThat(measurementRecord1).isNotEqualTo(measurementRecord2);
    }
}
