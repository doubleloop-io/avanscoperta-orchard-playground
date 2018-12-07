package avanscoperta.inventory;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class PurchaseOrder {
    @TargetAggregateIdentifier
    private final UUID orderId;
    private final UUID itemId;
    private final int quantity;
}
