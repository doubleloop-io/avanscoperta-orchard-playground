package avanscoperta.inventory;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockReplenishmentPolicy {

    private final CommandGateway commandGateway;
    private final int level;

    public StockReplenishmentPolicy(@Autowired CommandGateway commandGateway, int level) {
        this.commandGateway = commandGateway;
        this.level = level;
    }

    @EventHandler
    public void on(QuantityCheckedOut quantityCheckedOut) {
        if (quantityCheckedOut.getResultingInStock() < level)
            commandGateway.send(new PurchaseOrder(UUID.randomUUID(), quantityCheckedOut.getItemId(), 100));
    }
}
