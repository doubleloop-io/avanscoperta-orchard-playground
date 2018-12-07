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
They start to look _grey_ in the IDE because they're unused.

Still, we need to pass them to the outside world in the Event. Someone else is going to take care of it: the `Projection`.

## Testing Aggregates
Testing aggregates in a UnitTesting-like fashion is performed using an **Axon provided test fixture** which allows testing in a `given([events]).when(command).then([events])` style.

# Projections

This is where we turn the event flow into readable information for the reader. 
No big deal: a `Projection` class will subscribe/listen to specific events and then write the interesting contents into a peristence storage.

## Pros

### Easy to test
We don't need anything specific for testing projections.

## Cons
We are expecting some _"ouch this is so boring!"_ feeling when dealing with projections.
We moved the _descriptors_ outside of the aggregate logic, because there is no logic associated with the descriptors. 
