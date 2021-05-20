package oaktrees.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import oaktrees.security.Permission;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override 
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) { 
	    messages
	    .simpDestMatchers("/send/**", "/ws/**", "/listen/**").hasAuthority(Permission.USER_P.getPermission())
	    .anyMessage().authenticated(); 
	}
	
	@Override
	protected boolean sameOriginDisabled() {
	    return true;
	}
}
