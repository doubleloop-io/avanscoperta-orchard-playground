package avanscoperta.inventory;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class CreateItem {
    @TargetAggregateIdentifier
    private final UUID itemId;
    private final String name;
    private final Integer initialQuantity;
}
