# avanscoperta-orchard-playground


# Aggregates

Aggregates - the **Yellow** large sticky notes - are little state machines in our flow. They respond to a finite set of commands producing a well predictable output.


## Pros :-)

The state machine management should be really solid now.

## Cons :-(



## Weird stuff :-/

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

### Specific business logic

Interesting example during a modelling session: _"An appointment shouldn't be rescheduled for more than 3 times"_.

We might be tempted to add a counter inside the `Appointment` aggregate to block further attempts to reschedule a given one. ...but this might actually be a local _policy_ instead: an appointment can be in fact rescheduled indefinitely, but our rule will try to prevent this from happening.

In practice, we'll have to meet somewhere in the middle, maybe have the aggregate allow a `blockRescheduling(...)` command, and have the business logic listen and count the instances of `AppointmentRescheduled`.

## Testing Aggregates
Testing aggregates in a UnitTesting-like fashion is performed using an **Axon provided test fixture** which allows testing in a 

`given([events]).when(command).then([events])` 

style.

### Awkward stuff

We're not peeking into the aggregate state with this Event-based testing style.

This means that **WE CAN'T BE SURE ABOUT THE AGGRECATE STATE UNTIL WE TEST THE EFFECT** with more tests.

# Projections

This is where we turn the event flow into readable information for the reader. 
No big deal: a `Projection` class will subscribe/listen to specific events and then write the interesting contents into a peristence storage.

## Pros

### Easy to test
We don't need anything specific for testing projections.

## Cons
We are expecting some _"ouch this is so boring!"_ feeling when dealing with projections.
We moved the _descriptors_ outside of the aggregate logic, because there is no logic associated with the descriptors. 

## Awkward stuff :-/
Apparently we need a new data definition, _which looks like the old style entities, which are now the aggregates ...is that it?_

In practice, if we have only one aggregate, and only one projection, ...we'll be writing more code. Things start to get interesting when the `*View` class starts getting different from the aggregate.

Please keep in mind that the `View` is a decision support tool for human beings, that will need to decide upon a slightly different data set.

## Testing Projections

Testing projections happens in a different way, there is no specific framework support hare and the testing pattern is 

`given([events]).when(event).then([data])`.

# Policies
Policies span in complexity from human activities to simple _Listeners_ which can be essentially stateless - to more complex process managers and sagas.

## Awkward Stuff
Web resources can be contraddictory when describing _Sagas_ and _Process managers_, and we won't be able to correct the web. So expect some inconsistencies.

## References
The most interesting documentation for these patterns can be found in: **Enterprise Integration Patterns** from Gregor Hohpe and Bobby Woolf.


