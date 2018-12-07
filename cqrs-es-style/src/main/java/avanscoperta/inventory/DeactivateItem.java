package avanscoperta.inventory;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class DeactivateItem {
    @TargetAggregateIdentifier
    private final UUID itemId;
}
