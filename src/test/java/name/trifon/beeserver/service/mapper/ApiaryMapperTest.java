package name.trifon.beeserver.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiaryMapperTest {

    private ApiaryMapper apiaryMapper;

    @BeforeEach
    public void setUp() {
        apiaryMapper = new ApiaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(apiaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(apiaryMapper.fromId(null)).isNull();
    }
}
