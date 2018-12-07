package avanscoperta.inventory;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
