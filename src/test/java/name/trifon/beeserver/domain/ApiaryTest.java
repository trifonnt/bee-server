package name.trifon.beeserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import name.trifon.beeserver.web.rest.TestUtil;

public class ApiaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apiary.class);
        Apiary apiary1 = new Apiary();
        apiary1.setId(1L);
        Apiary apiary2 = new Apiary();
        apiary2.setId(apiary1.getId());
        assertThat(apiary1).isEqualTo(apiary2);
        apiary2.setId(2L);
        assertThat(apiary1).isNotEqualTo(apiary2);
        apiary1.setId(null);
        assertThat(apiary1).isNotEqualTo(apiary2);
    }
}
