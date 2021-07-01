package name.trifon.beeserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class BeehiveTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beehive.class);
        Beehive beehive1 = new Beehive();
        beehive1.setId(1L);
        Beehive beehive2 = new Beehive();
        beehive2.setId(beehive1.getId());
        assertThat(beehive1).isEqualTo(beehive2);
        beehive2.setId(2L);
        assertThat(beehive1).isNotEqualTo(beehive2);
        beehive1.setId(null);
        assertThat(beehive1).isNotEqualTo(beehive2);
    }
}
