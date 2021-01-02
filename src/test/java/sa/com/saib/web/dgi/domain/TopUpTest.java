package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class TopUpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopUp.class);
        TopUp topUp1 = new TopUp();
        topUp1.setId(1L);
        TopUp topUp2 = new TopUp();
        topUp2.setId(topUp1.getId());
        assertThat(topUp1).isEqualTo(topUp2);
        topUp2.setId(2L);
        assertThat(topUp1).isNotEqualTo(topUp2);
        topUp1.setId(null);
        assertThat(topUp1).isNotEqualTo(topUp2);
    }
}
