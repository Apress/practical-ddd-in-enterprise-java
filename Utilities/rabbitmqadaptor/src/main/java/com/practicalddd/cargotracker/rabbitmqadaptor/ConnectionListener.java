package com.practicalddd.cargotracker.rabbitmqadaptor;


import com.rabbitmq.client.Connection;

/**
 * A connection listener is used by a connection factory
 * to notify clients about a change in connection state.
 * 
 * @author christian.bick
 *
 */
public interface ConnectionListener {
    
    /**
     * Called when a connection was established the first time.
     * 
     * @param connection The established connection
     */
    void onConnectionEstablished(Connection connection);
    
    /**
     * Called when a connection was lost and the connection
     * factory is trying to reestablish the connection.
     * 
     * @param connection The lost connection
     */
    void onConnectionLost(Connection connection);
    
    /**
     * Called when a connection was ultimately closed and
     * no new connection is going to be established in the
     * future (this the case if the connection factory was
     * teared down).
     * 
     * @param connection The closed connection
     */
    void onConnectionClosed(Connection connection);

}
