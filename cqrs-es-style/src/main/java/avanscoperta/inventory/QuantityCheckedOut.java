package avanscoperta.inventory;

import lombok.Data;

import java.util.UUID;

@Data
public class QuantityCheckedOut {
    private final UUID itemId;
    private final Integer quantity;
    private final Integer resultingInStock;
}
