package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class KycTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KycTransactions.class);
        KycTransactions kycTransactions1 = new KycTransactions();
        kycTransactions1.setId(1L);
        KycTransactions kycTransactions2 = new KycTransactions();
        kycTransactions2.setId(kycTransactions1.getId());
        assertThat(kycTransactions1).isEqualTo(kycTransactions2);
        kycTransactions2.setId(2L);
        assertThat(kycTransactions1).isNotEqualTo(kycTransactions2);
        kycTransactions1.setId(null);
        assertThat(kycTransactions1).isNotEqualTo(kycTransactions2);
    }
}
