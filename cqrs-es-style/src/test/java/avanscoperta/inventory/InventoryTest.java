package avanscoperta.inventory;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTest {

    private FixtureConfiguration<Inventory> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Inventory.class);
    }

    @Test
    public void itWorks() {
        assertThat("a").isEqualTo("a");
    }

}