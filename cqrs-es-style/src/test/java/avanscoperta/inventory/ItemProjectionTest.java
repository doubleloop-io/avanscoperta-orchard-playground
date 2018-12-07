package avanscoperta.inventory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemProjectionTest {

    @Autowired
    private ItemViewRepository repository;

    @Autowired
    private ItemProjection projection;

    @Test
    public void addOneItem() {
        final UUID itemId = UUID.randomUUID();
        final ItemCreated e = new ItemCreated(itemId, "foo", 100);
        projection.on(e);
        final Optional<ItemView> first = repository.findAll()
                .stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst();

        assertThat(first.isPresent()).isTrue();
        assertThat(first.get().getName()).isEqualTo(e.getName());
        assertThat(first.get().getQuantity()).isEqualTo(e.getInitialQuantity());
    }
}