package com.practicalddd.cargotracker.rabbitmqadaptor.consumer;

import com.practicalddd.cargotracker.rabbitmqadaptor.ConnectionListener;
import com.practicalddd.cargotracker.rabbitmqadaptor.SingleConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ShutdownListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Singleton;
import java.io.IOException;
import java.sql.Time;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * A consumer container hosts consumers and manages their
 * lifecycle.
 * 
 * @author christian.bick
 * @author uwe.janner
 * @author soner.dastan
 *
 */
public class ConsumerContainer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerContainer.class);
    private static final int DEFAULT_AMOUNT_OF_INSTANCES = 1;

    ConnectionFactory connectionFactory;
    List<ConsumerHolder> consumerHolders = Collections.synchronizedList(new LinkedList<ConsumerHolder>());
    
    private final Object activationMonitor = new Object();
    
    /**
     * Creates the container using the given connection factory (re-)establish 
     * the connection to the broker.
     * 
     * @param connectionFactory The connection factory
     */
    public ConsumerContainer(ConnectionFactory connectionFactory) {
        super();
        this.connectionFactory = connectionFactory;
        if (connectionFactory instanceof SingleConnectionFactory) {
            ContainerConnectionListener connectionListener = new ContainerConnectionListener();
            ((SingleConnectionFactory)connectionFactory).registerListener(connectionListener);
        }
    }

    /**
     * Adds a consumer to the container and binds it to the given queue with auto acknowledge disabled.
     * Does NOT enable the consumer to consume from the message broker until the container is started.
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     */
    public void addConsumer(Consumer consumer, String queue) {
        addConsumer(consumer, new ConsumerConfiguration(queue), DEFAULT_AMOUNT_OF_INSTANCES);
    }

    /**
     * Adds a consumer to the container, binds it to the given queue and sets whether the consumer auto acknowledge
     * or not. Does NOT enable the consumer to consume from the message broker until the container is started.
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     * @param autoAck whether the consumer shall auto ack or not
     */
    public void addConsumer(Consumer consumer, String queue, boolean autoAck) {
        addConsumer(consumer, new ConsumerConfiguration(queue, autoAck), DEFAULT_AMOUNT_OF_INSTANCES);
    }

    /**
     * <p>Adds a consumer to the container, binds it to the given queue with auto acknowledge disabled.
     * Does NOT enable the consumer to consume from the message broker until the container is started.</p>
     *
     * <p>Registers the same consumer N times at the queue according to the number of specified instances.
     * Use this for scaling your consumers locally. Be aware that the consumer implementation must
     * be stateless or thread safe.</p>
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     * @param prefetchMessageCount The number of message the client keep in buffer before processing them.
     * @param instances the amount of consumer instances
     */
    public void addConsumer(Consumer consumer, String queue, int prefetchMessageCount, int instances) {
        addConsumer(consumer, new ConsumerConfiguration(queue, prefetchMessageCount), instances);
    }

    /**
     * <p>Adds a consumer to the container, binds it to the given queue with auto acknowledge disabled.
     * Does NOT enable the consumer to consume from the message broker until the container is started.</p>
     *
     * <p>Registers the same consumer N times at the queue according to the number of specified instances.
     * Use this for scaling your consumers locally. Be aware that the consumer implementation must
     * be stateless or thread safe.</p>
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     * @param instances the amount of consumer instances
     */
    public void addConsumer(Consumer consumer, String queue, int instances) {
        addConsumer(consumer, new ConsumerConfiguration(queue), instances);
    }

    /**
     * <p>Adds a consumer to the container, binds it to the given queue and sets whether the consumer auto acknowledge
     * or not. Does NOT enable the consumer to consume from the message broker until the container is started.</p>
     *
     * <p>Registers the same consumer N times at the queue according to the number of specified instances.
     * Use this for scaling your consumers locally. Be aware that the consumer implementation must
     * be stateless or thread safe.</p>
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     * @param autoAck whether the consumer shall auto ack or not
     * @param prefetchMessageCount The number of message the client keep in buffer before processing them.
     * @param instances the amount of consumer instances
     */
    public void addConsumer(Consumer consumer, String queue, boolean autoAck, int prefetchMessageCount, int instances) {
        addConsumer(consumer, new ConsumerConfiguration(queue, autoAck, prefetchMessageCount), instances);
    }

    /**
     * <p>Adds a consumer to the container, binds it to the given queue and sets whether the consumer auto acknowledge
     * or not. Does NOT enable the consumer to consume from the message broker until the container is started.</p>
     *
     * <p>Registers the same consumer N times at the queue according to the number of specified instances.
     * Use this for scaling your consumers locally. Be aware that the consumer implementation must
     * be stateless or thread safe.</p>
     *
     * @param consumer The consumer
     * @param queue The queue to bind the consume to
     * @param autoAck whether the consumer shall auto ack or not
     * @param instances the amount of consumer instances
     */
    public void addConsumer(Consumer consumer, String queue, boolean autoAck, int instances) {
        addConsumer(consumer, new ConsumerConfiguration(queue, autoAck), instances);
    }

    /**
     * Adds a consumer to the container and configures it according to the consumer
     * configuration. Does NOT enable the consumer to consume from the message broker until the container is started.
     *
     * <p>Registers the same consumer N times at the queue according to the number of specified instances.
     * Use this for scaling your consumers locally. Be aware that the consumer implementation must
     * be stateless or thread safe.</p>
     *
     * @param consumer The consumer
     * @param configuration The consumer configuration
     * @param instances the amount of consumer instances
     */
    public synchronized void addConsumer(Consumer consumer, ConsumerConfiguration configuration, int instances) {
        for (int i=0; i < instances; i++) {
            this.consumerHolders.add(new ConsumerHolder(consumer, configuration));
        }
    }

    /**
     * <p>Starts all consumers managed by the container being an instance,
     * extending or implementing the given class or interface.</p>
     *
     * @see {@link #startAllConsumers()}
     *
     * @param consumerClass The consumer class or interface
     * @throws IOException if a consumer registration at the message broker fails
     */
    public synchronized void startConsumers(Class<? extends Consumer> consumerClass) throws IOException,TimeoutException {
        List<ConsumerHolder> consumerHolderSubList = filterConsumersForClass(consumerClass);
        enableConsumers(consumerHolderSubList);
    }

    /**
     * <p>Starts all consumers managed by the container.</p>
     *
     * <p>A started consumer consumes from the broker and is
     * re-registered at the broker after a connection was lost and
     * reestablished afterwards.</p>
     *
     * @throws IOException
     */
    public synchronized void startAllConsumers() throws IOException,TimeoutException {
        enableConsumers(consumerHolders);
    }

    /**
     * <p>Stops all consumers managed by the container being an instance,
     * extending or implementing the given class or interface.</p>
     *
     * @see {@link #stopAllConsumers()}
     *
     * @param consumerClass The consumer class or interface
     */
    public synchronized void stopConsumers(Class<? extends Consumer> consumerClass) {
        List<ConsumerHolder> consumerHolderSubList = filterConsumersForClass(consumerClass);
        disableConsumers(consumerHolderSubList);
    }

    /**
     * <p>Stops all consumers managed by the container.</p>
     *
     * <p>A stopped consumer does not consume from the broker</p>
     *
     */
    public synchronized void stopAllConsumers() {
        disableConsumers(consumerHolders);
    }

    /**
     * Resets the container, stopping all consumers and removing them from
     * the container.
     */
    public synchronized void reset() {
        disableConsumers(consumerHolders);
        consumerHolders.clear();
    }
    
    /**
     * <p>Gets all enabled consumers managed by the container.</p>
     * 
     * <p>Enabled consumers have been started once and not 
     * been stopped since that. An enabled consumer is (re-)registered 
     * by the container on connection (re-)establishing.</p>
     * 
     * @return The list of enabled consumers
     */
    public List<ConsumerHolder> getEnabledConsumers() {
        return filterConsumersForEnabledFlag(true);
    }
    
    /**
     * <p>Gets all disabled consumers managed by the container.</p> 
     * 
     * <p>Disabled consumers have either never been started or have been stopped
     * at some time and not been started again.</p>
     * 
     * @see {@link #getEnabledConsumers()}
     * 
     * @return The list of disabled consumers.
     */
    public List<ConsumerHolder> getDisabledConsumers() {
        return filterConsumersForEnabledFlag(false);
    }
    
    /**
     * <p>Gets all active consumers managed by the container</p>
     * 
     * <p>Active consumers must are also enabled consumers and are 
     * currently consuming from the broker. This means they
     * have an active channel via an open connection to the broker and
     * are registered at the broker.</p>
     * 
     * <p>Note: This method is only for use of information as it is
     * not exact in terms of concurrency and time.</p>
     * 
     * @return The list of active consumers
     */
    public List<ConsumerHolder> getActiveConsumers() {
        return filterConsumersForActiveFlag(true);
    }
    
    /**
     * Gets all inactive consumers managed by the container</p>
     * 
     * <p>Inactive consumers may also be disabled consumers and
     * are currently not consuming from the broker. In case a
     * consumer is disabled this is an expected state. In
     * case the consumer is enabled, the reason for the consumers
     * inactivity is either a channel problem, a loss of connection
     * or an unfinished registration at the broker.</p>
     * 
     * <p>Note: This method is only for use of information as it is
     * not exact in terms of concurrency and time.</p>
     * 
     * @return The list of inactive consumers
     */
    public List<ConsumerHolder> getInactiveConsumers() {
        return filterConsumersForActiveFlag(false);
    }
    
    /**
     * Filters the consumers being an instance, extending or implementing the given 
     * class from the list of managed consumers.
     * 
     * @param consumerClass The consumer class
     * @return The filtered consumers
     */
    protected List<ConsumerHolder> filterConsumersForClass(Class<? extends Consumer> consumerClass) {
        List<ConsumerHolder> consumerHolderSubList = new LinkedList<ConsumerHolder>();
        for (ConsumerHolder consumerHolder : consumerHolders) {
            if (consumerClass.isAssignableFrom(consumerHolder.getConsumer().getClass())) {
                consumerHolderSubList.add(consumerHolder);
            }
        }
        return consumerHolderSubList;
    }
    
    /**
     * Filters the consumers matching the given enabled flag from the list of managed consumers.
     * 
     * @param enabled Whether to filter for enabled or disabled consumers
     * @return The filtered consumers
     */
    protected List<ConsumerHolder> filterConsumersForEnabledFlag(boolean enabled) {
        List<ConsumerHolder> consumerHolderSubList = new LinkedList<ConsumerHolder>();
        for (ConsumerHolder consumerHolder : consumerHolders) {
            if (consumerHolder.isEnabled() == enabled) {
                consumerHolderSubList.add(consumerHolder);
            }
        }
        return consumerHolderSubList;
    }
    
    /**
     * Filters the consumers matching the given active flag from the list of managed consumers.
     * 
     * @param active Whether to filter for active or inactive consumers
     * @return The filtered consumers
     */
    protected List<ConsumerHolder> filterConsumersForActiveFlag(boolean active) {
        List<ConsumerHolder> consumerHolderSubList = new LinkedList<ConsumerHolder>();
        for (ConsumerHolder consumerHolder : consumerHolders) {
            if (consumerHolder.isActive() == active) {
                consumerHolderSubList.add(consumerHolder);
            }
        }
        return consumerHolderSubList;
    }
    
    /**
     * Enables all consumers in the given list and hands them over for activation afterwards.
     * 
     * @param consumerHolders The consumers to enable
     * @throws IOException if the activation process fails for a consumer
     */
    protected void enableConsumers(List<ConsumerHolder> consumerHolders) throws IOException,TimeoutException {
        checkPreconditions(consumerHolders);
        try {
            for (ConsumerHolder consumerHolder : consumerHolders) {
                consumerHolder.enable();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to enable consumers - disabling already enabled consumers");
            disableConsumers(consumerHolders);
            throw e;
        }
    }
    
    /**
     * Disables all consumers in the given list after deactivating them.
     * 
     * @param consumerHolders The consumers to disable
     */
    protected void disableConsumers(List<ConsumerHolder> consumerHolders) {
        for (ConsumerHolder consumerHolder : consumerHolders) {
            consumerHolder.disable();
        }
    }
    
    /**
     * Activates all consumers in the given list.
     * 
     * @param consumerHolders The list of consumers to activate
     * @throws IOException if the activation process fails for a consumer
     */
    protected void activateConsumers(List<ConsumerHolder> consumerHolders) throws IOException,TimeoutException {
        synchronized (activationMonitor) {
            for (ConsumerHolder consumerHolder : consumerHolders) {
                try {
                    consumerHolder.activate();
                } catch (IOException e) {
                    LOGGER.error("Failed to activate consumer - deactivating already activated consumers");
                    deactivateConsumers(consumerHolders);
                    throw e;
                }
            }
        }
    }
    
    /**
     * Deactivates all consumers in the given list.
     * 
     * @param consumerHolders The list of consumers to deactivate.
     */
    protected void deactivateConsumers(List<ConsumerHolder> consumerHolders) {
        synchronized (activationMonitor) {
            for (ConsumerHolder consumerHolder : consumerHolders) {
                consumerHolder.deactivate();
            }
        }
    }
    
    /**
     * Checks if all preconditions are fulfilled on the broker to
     * successfully register a consumer there. One important precondition
     * is the existence of the queue the consumer shall consume from.
     * 
     * @param consumerHolders The consumer holders
     * @throws IOException if the precondition check fails
     */
    protected void checkPreconditions(List<ConsumerHolder> consumerHolders) throws IOException, TimeoutException {
        Channel channel = createChannel();
        for (ConsumerHolder consumerHolder : consumerHolders) {
            String queue = consumerHolder.getConfiguration().getQueueName();
            try {
                channel.queueDeclarePassive(queue);
                LOGGER.debug("Queue {} found on broker", queue);
            } catch (IOException e) {
                LOGGER.error("Queue {} not found on broker", queue);
                throw e;
            }
        }
        channel.close();
    }

    /**
     * Creates a channel to be used for consuming from
     * the broker.
     * 
     * @return The channel
     * @throws IOException if the channel cannot be created due to a connection problem
     */
    protected Channel createChannel() throws IOException, TimeoutException {
        LOGGER.debug("Creating channel");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        LOGGER.debug("Created channel");
        return channel;
    }
    
    /**
     * A container connection listener to react on events of
     * a {@link SingleConnectionFactory} if used.
     * 
     * @author christian.bick
     *
     */
    protected class ContainerConnectionListener implements ConnectionListener {
        

        public void onConnectionEstablished(Connection connection) {
            String hostName = connection.getAddress().getHostName();
            LOGGER.info("Connection established to {}", hostName);
            List<ConsumerHolder> enabledConsumerHolders = filterConsumersForEnabledFlag(true);
            LOGGER.info("Activating {} enabled consumers", enabledConsumerHolders.size());
            try {
                activateConsumers(enabledConsumerHolders);
                LOGGER.info("Activated enabled consumers");
            } catch (IOException e) {
                LOGGER.error("Failed to activate enabled consumers", e);
                deactivateConsumers(enabledConsumerHolders);
            }catch(TimeoutException te){
                LOGGER.error("Failed to activate enabled consumers", te);
                deactivateConsumers(enabledConsumerHolders);
            }
        }


        public void onConnectionLost(Connection connection) {
            LOGGER.warn("Connection lost");
            LOGGER.info("Deactivating enabled consumers");
            List<ConsumerHolder> enabledConsumerHolders = filterConsumersForEnabledFlag(true);
            deactivateConsumers(enabledConsumerHolders);
        }


        public void onConnectionClosed(Connection connection) {
            LOGGER.warn("Connection closed for ever");
            LOGGER.info("Deactivating enabled consumers");
            List<ConsumerHolder> enabledConsumerHolders = filterConsumersForEnabledFlag(true);
            deactivateConsumers(enabledConsumerHolders);
        }
    }
    
    /**
     * A holder of a consumer attaching additional state to the consumer.
     * 
     * @author christian.bick
     *
     */
    public class ConsumerHolder {

        Channel channel;
        Consumer consumer;
        ConsumerConfiguration configuration;
        ShutdownListener channelShutdownListener;

        boolean enabled = false;
        boolean active = false;
        
        public ConsumerHolder(Consumer consumer, ConsumerConfiguration configuration) {
            this.consumer = consumer;
            this.configuration = configuration;
            if ( consumer instanceof ManagedConsumer) {
                ((ManagedConsumer) consumer).setConfiguration(configuration);
            }
        }
        
        public Consumer getConsumer() {
            return consumer;
        }

        public ConsumerConfiguration getConfiguration() {
            return configuration;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isActive() {
            return active;
        }

        public void enable() throws IOException,TimeoutException {
            enabled = true;
            activate();
        }

        public void disable() {
            enabled = false;
            deactivate();
        }

        void deactivate() {
            LOGGER.info("Deactivating consumer of class {}", consumer.getClass());
            if (channel != null) {
                try {
                    LOGGER.info("Closing channel for consumer of class {}", consumer.getClass());
                    channel.close();
                    LOGGER.info("Closed channel for consumer of class {}", consumer.getClass());
                } catch (Exception e) {
                    LOGGER.info("Aborted closing channel for consumer of class {} (already closing)", consumer.getClass());
                    // Ignore exception: In this case the channel is for sure not usable any more
                }
                channel = null;
            }
            active = false;
            LOGGER.info("Deactivated consumer of class {}", consumer.getClass());
        }

        void activate() throws IOException,TimeoutException {
            LOGGER.info("Activating consumer of class {}", consumer.getClass());
            // Make sure the consumer is not active before starting it
            if (isActive()) {
                deactivate();
            }
            // Start the consumer
            try {
                channel = createChannel();
                if (consumer instanceof ManagedConsumer) {
                    ((ManagedConsumer) consumer).setChannel(channel);
                }
                channel.basicConsume(configuration.getQueueName(), configuration.isAutoAck(), consumer);
                channel.basicQos(configuration.getPrefetchMessageCount());
                active = true;
                LOGGER.info("Activated consumer of class {}", consumer.getClass());
            } catch (IOException e) {
                LOGGER.error("Failed to activate consumer of class {}", consumer.getClass(), e);
                throw e;
            }
        }
    }

    public static abstract class ManagedConsumer implements Consumer {

        private Channel channel;
        private ConsumerConfiguration configuration;

        void setChannel(Channel channel) {
            this.channel = channel;
        }

        protected Channel getChannel() {
            return channel;
        }

        void setConfiguration(ConsumerConfiguration configuration) {
            this.configuration = configuration;
        }

        protected ConsumerConfiguration getConfiguration() {
            return configuration;
        }
    }

}
