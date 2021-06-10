package ati.dgtic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ati.dgtic.web.rest.TestUtil;

public class AgrementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agrement.class);
        Agrement agrement1 = new Agrement();
        agrement1.setId(1L);
        Agrement agrement2 = new Agrement();
        agrement2.setId(agrement1.getId());
        assertThat(agrement1).isEqualTo(agrement2);
        agrement2.setId(2L);
        assertThat(agrement1).isNotEqualTo(agrement2);
        agrement1.setId(null);
        assertThat(agrement1).isNotEqualTo(agrement2);
    }
}
