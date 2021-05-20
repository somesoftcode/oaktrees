package oaktrees.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketListener {

	@EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("new connection");
    }
	
	@EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		System.out.println("connection is closed");
    }
}
