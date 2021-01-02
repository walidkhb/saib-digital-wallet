package sa.com.saib.web.dgi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sa.com.saib.web.dgi.web.rest.TestUtil;

public class PeerToPeerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeerToPeer.class);
        PeerToPeer peerToPeer1 = new PeerToPeer();
        peerToPeer1.setId(1L);
        PeerToPeer peerToPeer2 = new PeerToPeer();
        peerToPeer2.setId(peerToPeer1.getId());
        assertThat(peerToPeer1).isEqualTo(peerToPeer2);
        peerToPeer2.setId(2L);
        assertThat(peerToPeer1).isNotEqualTo(peerToPeer2);
        peerToPeer1.setId(null);
        assertThat(peerToPeer1).isNotEqualTo(peerToPeer2);
    }
}
