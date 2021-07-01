package name.trifon.beeserver.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MeasurementRecordMapperTest {

    private MeasurementRecordMapper measurementRecordMapper;

    @BeforeEach
    public void setUp() {
        measurementRecordMapper = new MeasurementRecordMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(measurementRecordMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(measurementRecordMapper.fromId(null)).isNull();
    }
}
