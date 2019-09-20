package com.practicalddd.cargotracker.rabbitmqadaptor;


import com.practicalddd.cargotracker.rabbitmqadaptor.consumer.MessageConsumer;


import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Consumes AMQP messages and fires CDI events for these messages.
 *
 * @author christian.bick
 */
public class EventConsumer extends MessageConsumer {

    private Event<Object> eventControl;
    private Instance<Object> eventPool;

    public EventConsumer(Event<Object> eventControl, Instance<Object> eventPool) {
        this.eventControl = eventControl;
        this.eventPool = eventPool;
    }

    @Override
    public void handleMessage(Message message) {
        Object event = buildEvent(message);
        eventControl.fire(event);
    }

    /**
     * Builds a CDI event from a message. The CDI event instance is retrieved
     * from the injection container.
     *
     * @param message The message
     * @return The CDI event
     */
    @SuppressWarnings("unchecked")
    Object buildEvent(Message message) {
        Object event = eventPool.get();
        if (event instanceof ContainsData) {
            ((ContainsData) event).setData(message.getBodyContent());
        } else if (event instanceof ContainsContent) {
            Class<?> parameterType = getParameterType(event, ContainsContent.class);
            ((ContainsContent) event).setContent(message.getBodyAs(parameterType));
        } else if (event instanceof ContainsId) {
            Class<?> parameterType = getParameterType(event, ContainsId.class);
            ((ContainsId) event).setId(message.getBodyAs(parameterType));
        }
        return event;
    }

    /**
     * Gets the type parameter of the expected generic interface which is
     * actually used by the class of the given object. The generic
     * interface can be implemented by an class or interface in the object's
     * class hierarchy,
     *
     * @param object The object
     * @param expectedType The expected generic interface
     * @return The actually used parameter of the expected generic interface
     */
    @SuppressWarnings("unchecked")
    static Class<?> getParameterType(Object object, Class<?> expectedType) {
        Collection<Class<?>> extendedAndImplementedTypes =
                getExtendedAndImplementedTypes(object.getClass(), new LinkedList<Class<?>>());

        for  (Class<?> type : extendedAndImplementedTypes) {
            Type[] implementedInterfaces  = type.getGenericInterfaces();
            for (Type implementedInterface : implementedInterfaces) {
                if (implementedInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedCandidateType = (ParameterizedType) implementedInterface;
                    if (parameterizedCandidateType.getRawType().equals(expectedType)) {
                        Type[] typeArguments = parameterizedCandidateType.getActualTypeArguments();
                        Type typeArgument;
                        if (typeArguments.length == 0) {
                            typeArgument = Object.class;
                        } else {
                            typeArgument = parameterizedCandidateType.getActualTypeArguments()[0];
                        }
                        return (Class<?>) typeArgument;
                    }
                }
            }
        }
        // This may never happen in case the caller checked if object instanceof expectedType
        throw new RuntimeException("Expected type " + expectedType +
                " is not in class hierarchy of " + object.getClass());
    }

    /**
     * Gets all classes and interfaces in the class hierarchy of the given class including
     * the class itself.
     *
     * @param clazz The class
     * @param hierarchy The current list of all classes and interfaces found so far
     * @return All classes and interfaces in the class's hierarchy
     */
    static List<Class<?>> getExtendedAndImplementedTypes(Class<?> clazz, List<Class<?>> hierarchy) {
        hierarchy.add(clazz);
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            hierarchy = getExtendedAndImplementedTypes(superClass, hierarchy);
        }
        for (Class<?> implementedInterface : clazz.getInterfaces()) {
            hierarchy = getExtendedAndImplementedTypes(implementedInterface, hierarchy);
        }
        return hierarchy;
    }
}
