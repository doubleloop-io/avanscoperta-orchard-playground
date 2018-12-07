package avanscoperta.contacts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/contacts")
public class ContactResource {

    private final Map<String, ContactSummary> contactSummaryMap = new HashMap<>();

    private static URI buildLocation(String cardId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardId)
                .toUri();
    }

    @GetMapping("/{id}")
    CompletableFuture<HttpEntity<ContactSummary>> one(@PathVariable String id) {
        if (!contactSummaryMap.containsKey(id))
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        return CompletableFuture.completedFuture(ResponseEntity.ok(contactSummaryMap.get(id)));
    }

    @GetMapping("/")
    CompletableFuture<HttpEntity<Collection<ContactSummary>>> all(@PageableDefault(size = 20) Pageable pageable) {
        return CompletableFuture.completedFuture(ResponseEntity.ok(contactSummaryMap.values()));
    }

    @PostMapping("/")
    public CompletableFuture<HttpEntity<Void>> issue(@Valid @RequestBody AddContactReq req) {
        String contactId = "#" + contactSummaryMap.size() + 1;
        final ContactSummary summary = new ContactSummary();
        summary.id = contactId;
        summary.name = req.name;
        summary.phone = req.phone;
        contactSummaryMap.put(contactId, summary);

        return CompletableFuture.completedFuture(ResponseEntity.created(buildLocation(contactId)).build());
    }

    public static class ContactSummary {
        @Getter
        private String id;
        @Getter
        private String name;
        @Getter
        private String phone;
    }

    public static class AddContactReq {
        @Setter
        private String name;
        @Setter
        private String phone;
    }
}
