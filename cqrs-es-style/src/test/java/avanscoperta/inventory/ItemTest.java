package avanscoperta.inventory;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    private FixtureConfiguration<Item> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Item.class);
    }

    @Test
    public void createNewItem() {
        final UUID itemId = UUID.randomUUID();
        fixture.givenNoPriorActivity()
                .when(new CreateItem(itemId, "pen", 10))
                .expectEvents(new ItemCreated(itemId, "pen", 10))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    public void checkinQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10))
                .when(new CheckInQuantity(itemId, 15))
                .expectEvents(new QuantityCheckedIn(itemId,  15, 25))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    public void checkOutQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10),
                      new QuantityCheckedIn(itemId,  15, 25))
                .when(new CheckOutQuantity(itemId, 5))
                .expectEvents(new QuantityCheckedOut(itemId, 5, 20))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    public void overCheckOutQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10),
                new QuantityCheckedIn(itemId,  15, 25))
                .when(new CheckOutQuantity(itemId, 105))
                .expectNoEvents()
                .expectException(OverCheckOutQuantityRequestedExecption.class);
    }
}