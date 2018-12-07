package avanscoperta.inventory;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemProjection {

    @Autowired
    private ItemViewRepository repository;

    @EventHandler
    public void on(ItemCreated e) {
        final ItemView view = new ItemView();
        view.setName(e.getName());
        view.setItemId(e.getItemId());
        view.setQuantity(e.getInitialQuantity());
        repository.save(view);
    }

    @QueryHandler
    public List<ItemView> fetch(GetAllItems q) {
        return repository.findAll();
    }
}

