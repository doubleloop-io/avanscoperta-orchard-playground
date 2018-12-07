package avanscoperta.inventory;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class ItemView {
    @Id
    @Getter
    @Setter
    private UUID itemId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer quantity;
}
