package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class KycTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kyc.class);
        Kyc kyc1 = new Kyc();
        kyc1.setId(1L);
        Kyc kyc2 = new Kyc();
        kyc2.setId(kyc1.getId());
        assertThat(kyc1).isEqualTo(kyc2);
        kyc2.setId(2L);
        assertThat(kyc1).isNotEqualTo(kyc2);
        kyc1.setId(null);
        assertThat(kyc1).isNotEqualTo(kyc2);
    }
}
