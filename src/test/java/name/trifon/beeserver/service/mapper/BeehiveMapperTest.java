package name.trifon.beeserver.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BeehiveMapperTest {

    private BeehiveMapper beehiveMapper;

    @BeforeEach
    public void setUp() {
        beehiveMapper = new BeehiveMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(beehiveMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(beehiveMapper.fromId(null)).isNull();
    }
}
