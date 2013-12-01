package org.jeecqrs.events;


/**
 * Provides the ability to listen to events that have been published
 * on the {@link EventBus}.
 * 
 * @param <E>  the base event type
 */
public interface EventBusListener<E> {

    /**
     * Receives an event that has been dispatched on the event bus.
     * 
     * @param event  the event to receive
     */
    void receiveEvent(E event);

    /**
     * Gets the event types this listener is interested in.
     * 
     * @return  the event bus interest
     */
    EventBusInterest<E> interest();
    
}