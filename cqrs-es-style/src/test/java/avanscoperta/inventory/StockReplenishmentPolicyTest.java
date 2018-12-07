package avanscoperta.inventory;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StockReplenishmentPolicyTest {

    private SpyCommandGateway spyCommandGateway;

    @Before
    public void setUp() throws Exception {
        spyCommandGateway = new SpyCommandGateway();
    }

    @Test
    public void underTheTreshold() {
        final StockReplenishmentPolicy replenishmentPolicy = new StockReplenishmentPolicy(spyCommandGateway, 6);
        replenishmentPolicy.on(new QuantityCheckedOut(UUID.randomUUID(), 10, 7));
        assertThat(spyCommandGateway.sendCommands().size()).isEqualTo(0);
    }

    @Test
    public void overTheLevel() {
        final StockReplenishmentPolicy replenishmentPolicy = new StockReplenishmentPolicy(spyCommandGateway, 6);
        replenishmentPolicy.on(new QuantityCheckedOut(UUID.randomUUID(), 10, 2));
        assertThat(spyCommandGateway.sendCommands().size()).isEqualTo(1);
    }
}