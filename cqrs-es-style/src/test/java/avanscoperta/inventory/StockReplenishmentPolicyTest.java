package avanscoperta.inventory;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class StockReplenishmentPolicyTest {

    private SpyCommandGateway spyCommandGateway;

    @Before
    public void setUp() throws Exception {
        spyCommandGateway = new SpyCommandGateway();
    }

    @Test
    public void underTheTreshold() {
        final StockReplenishmentPolicy replenishmentPolicy = new StockReplenishmentPolicy(spyCommandGateway, 6);
        replenishmentPolicy.on(new QuantityCheckedOut(UUID.randomUUID(), 10, 7));
        assertThat(spyCommandGateway.sendCommands().size()).isEqualTo(0);
    }

    @Test
    public void overTheLevel() {
        final StockReplenishmentPolicy replenishmentPolicy = new StockReplenishmentPolicy(spyCommandGateway, 6);
        replenishmentPolicy.on(new QuantityCheckedOut(UUID.randomUUID(), 10, 2));
        assertThat(spyCommandGateway.sendCommands().size()).isEqualTo(1);
    }

    @Test
    public void onTheLevel() {
        final StockReplenishmentPolicy replenishmentPolicy = new StockReplenishmentPolicy(spyCommandGateway, 6);
        replenishmentPolicy.on(new QuantityCheckedOut(UUID.randomUUID(), 10, 6));
        assertThat(spyCommandGateway.sendCommands().size()).isEqualTo(0);
    }

    public class SpyCommandGateway implements CommandGateway {
        private Object lastCommand;

        @Override
        public <C, R> void send(C command, CommandCallback<? super C, R> callback) {

        }

        @Override
        public <R> R sendAndWait(Object command) {
            return null;
        }

        @Override
        public <R> R sendAndWait(Object command, long timeout, TimeUnit unit) {
            return null;
        }

        @Override
        public <R> CompletableFuture<R> send(Object command) {
            lastCommand = command;
            return null;
        }

        public Collection<Object> sendCommands() {
            if (lastCommand == null)
                return Collections.emptyList();
            return Arrays.asList(lastCommand);
        }
    }
}