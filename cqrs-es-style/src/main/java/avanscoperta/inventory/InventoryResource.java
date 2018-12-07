package avanscoperta.inventory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
public class InventoryResource {

    @GetMapping("/{id}")
    CompletableFuture<HttpEntity<ItemSummary>> one(@PathVariable String id) {
    throw new NotImplementedException();
    }

    @GetMapping("/")
    CompletableFuture<HttpEntity<Collection<ItemSummary>>> all(@PageableDefault(size = 20) Pageable pageable) {
        throw new NotImplementedException();
    }

    @PostMapping("/")
    public CompletableFuture<HttpEntity<Void>> issue(@Valid @RequestBody InventoryResource.AddItemReq req) {
        throw new NotImplementedException();
    }

    public static class ItemSummary {
    }

    public static class AddItemReq {
    }
}
