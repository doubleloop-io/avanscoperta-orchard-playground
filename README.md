# avanscoperta-orchard-playground


# Aggregates


## Weird stuff

### Two methods for every operation

Yep! We'll need to separate the guard logic (in the `@CommandHandler` part), from the state update logic.

Even more awkwardly: we'll have to 
* perform calculations in the command handler part,
* write the calculation result in the event.
* in the _event handling_ part we'll read our own event and _copy the result_ into our current state.

### Apparently empty
A lot of attributes - mostly `String` and descriptive fields are not taking part in any state-management business logic.
They start to look _grey_ in the IDE because they're unused. These apparently useless attributes are called _descriptors_ and they don't really belong in software driven business logic.

Still, we need to pass them to the outside world in the Event, to make it avalable to some human being: software doesn't usually make decisions based on names, surnames and pictures, but humans do, so we need to make this information available to the users somehow.

Something else is going to take care of it: the `Projection`.

## Testing Aggregates
Testing aggregates in a UnitTesting-like fashion is performed using an **Axon provided test fixture** which allows testing in a 

`given([events]).when(command).then([events])` 

style.

# Projections

This is where we turn the event flow into readable information for the reader. 
No big deal: a `Projection` class will subscribe/listen to specific events and then write the interesting contents into a peristence storage.

## Pros

### Easy to test
We don't need anything specific for testing projections.

## Cons
We are expecting some _"ouch this is so boring!"_ feeling when dealing with projections.
We moved the _descriptors_ outside of the aggregate logic, because there is no logic associated with the descriptors. 

## Testing Projections

Testing projections happens in a different way, there is no specific framework support hare and the testing pattern is 

`given([events]).when(event).then([data])`.

