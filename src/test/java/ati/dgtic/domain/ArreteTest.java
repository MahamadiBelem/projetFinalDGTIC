package ati.dgtic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ati.dgtic.web.rest.TestUtil;

public class ArreteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arrete.class);
        Arrete arrete1 = new Arrete();
        arrete1.setId(1L);
        Arrete arrete2 = new Arrete();
        arrete2.setId(arrete1.getId());
        assertThat(arrete1).isEqualTo(arrete2);
        arrete2.setId(2L);
        assertThat(arrete1).isNotEqualTo(arrete2);
        arrete1.setId(null);
        assertThat(arrete1).isNotEqualTo(arrete2);
    }
}
