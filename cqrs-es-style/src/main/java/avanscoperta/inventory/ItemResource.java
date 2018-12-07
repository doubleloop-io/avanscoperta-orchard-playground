package avanscoperta.inventory;

import lombok.Setter;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
public class ItemResource {

    @Autowired
    private CommandGateway commandGateway;

    private static URI buildLocation(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @GetMapping("/{id}")
    CompletableFuture<HttpEntity<ItemSummary>> oneItem(@PathVariable String id) {
        throw new NotImplementedException();
    }

    @GetMapping("/")
    CompletableFuture<HttpEntity<Collection<ItemSummary>>> allItems(@PageableDefault(size = 20) Pageable pageable) {
        throw new NotImplementedException();
    }

    @PostMapping("/")
    public CompletableFuture<HttpEntity<Void>> addItem(@Valid @RequestBody ItemResource.AddItemReq req) {
        final UUID itemId = UUID.randomUUID();
        return commandGateway.send(new CreateItem(itemId, req.name, req.quantity))
                .thenApply(x -> ResponseEntity.created(buildLocation(itemId)).build());
    }

    public static class ItemSummary {
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
