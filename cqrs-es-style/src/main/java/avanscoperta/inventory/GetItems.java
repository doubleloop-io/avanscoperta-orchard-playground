package avanscoperta.inventory;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

public class GetItems {
    @Getter
    private final Pageable pageable;

    @Getter
    @Setter
    private boolean onlyActiveItems;

    public GetItems(Pageable pageable) {
        this.pageable = pageable;
    }
}
