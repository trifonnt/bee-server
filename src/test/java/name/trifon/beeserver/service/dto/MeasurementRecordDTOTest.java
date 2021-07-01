package name.trifon.beeserver.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class MeasurementRecordDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurementRecordDTO.class);
        MeasurementRecordDTO measurementRecordDTO1 = new MeasurementRecordDTO();
        measurementRecordDTO1.setId(1L);
        MeasurementRecordDTO measurementRecordDTO2 = new MeasurementRecordDTO();
        assertThat(measurementRecordDTO1).isNotEqualTo(measurementRecordDTO2);
        measurementRecordDTO2.setId(measurementRecordDTO1.getId());
        assertThat(measurementRecordDTO1).isEqualTo(measurementRecordDTO2);
        measurementRecordDTO2.setId(2L);
        assertThat(measurementRecordDTO1).isNotEqualTo(measurementRecordDTO2);
        measurementRecordDTO1.setId(null);
        assertThat(measurementRecordDTO1).isNotEqualTo(measurementRecordDTO2);
    }
}
