package name.trifon.beeserver.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class ApiaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiaryDTO.class);
        ApiaryDTO apiaryDTO1 = new ApiaryDTO();
        apiaryDTO1.setId(1L);
        ApiaryDTO apiaryDTO2 = new ApiaryDTO();
        assertThat(apiaryDTO1).isNotEqualTo(apiaryDTO2);
        apiaryDTO2.setId(apiaryDTO1.getId());
        assertThat(apiaryDTO1).isEqualTo(apiaryDTO2);
        apiaryDTO2.setId(2L);
        assertThat(apiaryDTO1).isNotEqualTo(apiaryDTO2);
        apiaryDTO1.setId(null);
        assertThat(apiaryDTO1).isNotEqualTo(apiaryDTO2);
    }
}
