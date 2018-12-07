package avanscoperta.inventory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemProjectionTest {

    @Autowired
    private ItemViewRepository repository;

    @Autowired
    private ItemProjection projection;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void addOneItem() {
        final UUID itemId = UUID.randomUUID();
        final ItemCreated e = new ItemCreated(itemId, "foo", 100);

        projection.on(e);
        final List<ItemView> list = projection.fetch(new GetAllItems());

        assertThat(list.get(0).getItemId()).isEqualTo(e.getItemId());
        assertThat(list.get(0).getName()).isEqualTo(e.getName());
        assertThat(list.get(0).getQuantity()).isEqualTo(e.getInitialQuantity());
    }

    @Test
    public void addManyItems() {
        final ItemCreated e1 = new ItemCreated(UUID.randomUUID(), "foo", 1);
        final ItemCreated e2 = new ItemCreated(UUID.randomUUID(), "bar", 2);
        final ItemCreated e3 = new ItemCreated(UUID.randomUUID(), "baz", 3);

        projection.on(e1);
        projection.on(e2);
        projection.on(e3);
        final List<ItemView> list = projection.fetch(new GetAllItems());

        assertThat(list.get(0).getItemId()).isEqualTo(e1.getItemId());
        assertThat(list.get(1).getItemId()).isEqualTo(e2.getItemId());
        assertThat(list.get(2).getItemId()).isEqualTo(e3.getItemId());
    }

    @Test
    public void onlyActiveItems() {
        final ItemCreated e1 = new ItemCreated(UUID.randomUUID(), "foo", 1);
        final ItemCreated e2 = new ItemCreated(UUID.randomUUID(), "bar", 2);
        final ItemCreated e3 = new ItemCreated(UUID.randomUUID(), "baz", 3);
        final ItemDeactivated e4 = new ItemDeactivated(e2.getItemId());

        projection.on(e1);
        projection.on(e2);
        projection.on(e3);
        projection.on(e4);
        final GetAllItems query = new GetAllItems();
        query.setOnlyActiveItems(true);
        final List<ItemView> list = projection.fetch(query);

        assertThat(list.get(0).getItemId()).isEqualTo(e1.getItemId());
        assertThat(list.get(1).getItemId()).isEqualTo(e3.getItemId());
    }
}