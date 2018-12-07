package avanscoperta.inventory;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

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
    public void createNewItemWithNegativeQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.givenNoPriorActivity()
                .when(new CreateItem(itemId, "pen", -15))
                .expectNoEvents()
                .expectException(NegativeQuantityException.class);
    }

    @Test
    public void checkInQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10))
                .when(new CheckInQuantity(itemId, 15))
                .expectEvents(new QuantityCheckedIn(itemId, 15, 25));
    }

    @Test
    public void checkInNegativeQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10))
                .when(new CheckInQuantity(itemId, -15))
                .expectNoEvents()
                .expectException(NegativeQuantityException.class);
    }

    @Test
    public void checkOutQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10),
                new QuantityCheckedIn(itemId, 15, 25))
                .when(new CheckOutQuantity(itemId, 5))
                .expectEvents(new QuantityCheckedOut(itemId, 5, 20))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    public void overCheckOutQuantity() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10),
                new QuantityCheckedIn(itemId, 15, 25))
                .when(new CheckOutQuantity(itemId, 105))
                .expectNoEvents()
                .expectException(OverCheckOutQuantityRequestedExecption.class);
    }

    @Test
    public void deactivate() {
        final UUID itemId = UUID.randomUUID();

        fixture.given(new ItemCreated(itemId, "pen", 10))
                .when(new DeactivateItem(itemId))
                .expectEvents(new ItemDeactivated(itemId))
                .expectSuccessfulHandlerExecution();
    }
}