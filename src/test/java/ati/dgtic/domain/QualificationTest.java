package ati.dgtic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ati.dgtic.web.rest.TestUtil;

public class QualificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualification.class);
        Qualification qualification1 = new Qualification();
        qualification1.setId(1L);
        Qualification qualification2 = new Qualification();
        qualification2.setId(qualification1.getId());
        assertThat(qualification1).isEqualTo(qualification2);
        qualification2.setId(2L);
        assertThat(qualification1).isNotEqualTo(qualification2);
        qualification1.setId(null);
        assertThat(qualification1).isNotEqualTo(qualification2);
    }
}
