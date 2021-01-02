package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class KycPersonalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KycPersonal.class);
        KycPersonal kycPersonal1 = new KycPersonal();
        kycPersonal1.setId(1L);
        KycPersonal kycPersonal2 = new KycPersonal();
        kycPersonal2.setId(kycPersonal1.getId());
        assertThat(kycPersonal1).isEqualTo(kycPersonal2);
        kycPersonal2.setId(2L);
        assertThat(kycPersonal1).isNotEqualTo(kycPersonal2);
        kycPersonal1.setId(null);
        assertThat(kycPersonal1).isNotEqualTo(kycPersonal2);
    }
}
