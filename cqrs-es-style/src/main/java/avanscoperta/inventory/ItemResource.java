package avanscoperta.inventory;

import lombok.Setter;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
public class ItemResource {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    private static URI buildLocation(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @GetMapping("/{id}")
    CompletableFuture<HttpEntity<ItemView>> oneItem(@PathVariable String id) {
        throw new NotImplementedException();
    }

    @GetMapping("/")
    CompletableFuture<HttpEntity<List<ItemView>>> allItems(@PageableDefault(size = 20) Pageable pageable) {
        return queryGateway.query(new GetItems(pageable), ResponseTypes.multipleInstancesOf(ItemView.class))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/actives/")
    CompletableFuture<HttpEntity<List<ItemView>>> allActiveItems(@PageableDefault(size = 20) Pageable pageable) {
        final GetItems query = new GetItems(pageable);
        query.setOnlyActiveItems(true);
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ItemView.class))
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/")
    public CompletableFuture<HttpEntity<Void>> addItem
            (@Valid @RequestBody ItemResource.AddItemReq req) {
        final UUID itemId = UUID.randomUUID();
        return commandGateway.send(new CreateItem(itemId, req.name, req.quantity))
                .thenApply(x -> ResponseEntity.created(buildLocation(itemId)).build());
    }

    public static class AddItemReq {
        @Setter
        @NotBlank
        private String name;

        @Setter
        @NotNull
        private Integer quantity;
    }
}
