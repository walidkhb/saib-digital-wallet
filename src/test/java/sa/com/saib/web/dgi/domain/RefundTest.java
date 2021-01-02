package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class RefundTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Refund.class);
        Refund refund1 = new Refund();
        refund1.setId(1L);
        Refund refund2 = new Refund();
        refund2.setId(refund1.getId());
        assertThat(refund1).isEqualTo(refund2);
        refund2.setId(2L);
        assertThat(refund1).isNotEqualTo(refund2);
        refund1.setId(null);
        assertThat(refund1).isNotEqualTo(refund2);
    }
}
