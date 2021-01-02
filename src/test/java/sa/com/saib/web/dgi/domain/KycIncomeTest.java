package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class KycIncomeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KycIncome.class);
        KycIncome kycIncome1 = new KycIncome();
        kycIncome1.setId(1L);
        KycIncome kycIncome2 = new KycIncome();
        kycIncome2.setId(kycIncome1.getId());
        assertThat(kycIncome1).isEqualTo(kycIncome2);
        kycIncome2.setId(2L);
        assertThat(kycIncome1).isNotEqualTo(kycIncome2);
        kycIncome1.setId(null);
        assertThat(kycIncome1).isNotEqualTo(kycIncome2);
    }
}
