package name.trifon.beeserver.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class BeehiveDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeehiveDTO.class);
        BeehiveDTO beehiveDTO1 = new BeehiveDTO();
        beehiveDTO1.setId(1L);
        BeehiveDTO beehiveDTO2 = new BeehiveDTO();
        assertThat(beehiveDTO1).isNotEqualTo(beehiveDTO2);
        beehiveDTO2.setId(beehiveDTO1.getId());
        assertThat(beehiveDTO1).isEqualTo(beehiveDTO2);
        beehiveDTO2.setId(2L);
        assertThat(beehiveDTO1).isNotEqualTo(beehiveDTO2);
        beehiveDTO1.setId(null);
        assertThat(beehiveDTO1).isNotEqualTo(beehiveDTO2);
    }
}
