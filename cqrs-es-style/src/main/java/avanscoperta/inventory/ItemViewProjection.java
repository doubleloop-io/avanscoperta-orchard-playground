package avanscoperta.inventory;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemViewProjection {

    @Autowired
    private ItemViewRepository repository;

    @EventHandler
    public void on(ItemCreated e) {
        final ItemView view = new ItemView();
        view.setName(e.getName());
        view.setItemId(e.getItemId());
        view.setQuantity(e.getInitialQuantity());
        view.setIsActive(true);
        repository.save(view);
    }

    @EventHandler
    public void on(ItemDeactivated e) {
        repository.findById(e.getItemId())
                .ifPresent(view -> {
                    view.setIsActive(false);
                    repository.save(view);
                });
    }

    @QueryHandler
    public List<ItemView> fetch(GetItems q) {
        final List<ItemView> all = repository.findAll(q.getPageable()).getContent();
        if (!q.isOnlyActiveItems()) return all;
        return all.stream().filter(v -> v.getIsActive()).collect(Collectors.toList());
    }
}

