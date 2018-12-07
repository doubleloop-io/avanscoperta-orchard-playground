package avanscoperta.inventory;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class ItemView {
    @Id
    private UUID itemId;
    private String name;
    private Integer quantity;
}
