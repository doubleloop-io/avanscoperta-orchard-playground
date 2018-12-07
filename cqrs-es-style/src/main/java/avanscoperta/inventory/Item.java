package avanscoperta.inventory;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Item {

    @AggregateIdentifier
    private UUID itemId;
    private Integer currentQuantity;

    public Item() {
    }

    @CommandHandler
    public Item(CreateItem c) {
        if (c.getInitialQuantity() < 0)
            throw new NegativeQuantityException();
        apply(new ItemCreated(c.getItemId(), c.getName(), c.getInitialQuantity()));
    }

    @CommandHandler
    public void handle(CheckInQuantity c) {
        if (c.getQuantity() < 0)
            throw new NegativeQuantityException();
        apply(new QuantityCheckedIn(c.getItemId(), c.getQuantity(), currentQuantity + c.getQuantity()));
    }

    @CommandHandler
    public void handle(CheckOutQuantity c) {
        if (c.getQuantity() > currentQuantity)
            throw new OverCheckOutQuantityRequestedExecption();
        apply(new QuantityCheckedOut(c.getItemId(), c.getQuantity(), currentQuantity - c.getQuantity()));
    }

    @CommandHandler
    public void handle(DeactivateItem c) {
        apply(new ItemDeactivated(c.getItemId()));
    }

    @EventSourcingHandler
    public void on(ItemCreated e) {
        itemId = e.getItemId();
        currentQuantity = e.getInitialQuantity();
    }

    @EventSourcingHandler
    public void on(QuantityCheckedIn e) {
        currentQuantity = e.getResultingInStock();
    }

    @EventSourcingHandler
    public void on(QuantityCheckedOut e) {
        currentQuantity = e.getResultingInStock();
    }
}
