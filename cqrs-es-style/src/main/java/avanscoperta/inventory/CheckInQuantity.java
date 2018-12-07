package avanscoperta.inventory;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class CheckInQuantity {
    @TargetAggregateIdentifier
    private final UUID itemId;
    private final Integer quantity;
}
