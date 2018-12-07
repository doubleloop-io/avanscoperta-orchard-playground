package avanscoperta.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ItemViewRepository extends MongoRepository<ItemView, UUID> {

}
