package oaktrees.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import oaktrees.data.Chat;
import oaktrees.data.ChatMessage;
import oaktrees.db.services.ChatMessageService;
import oaktrees.db.services.ChatService;

@RestController
public class ChatWebSocket {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private ChatService chatService;
	
	@MessageMapping("/message")
	public void send(ChatMessage message) {
		
		messagingTemplate.convertAndSend("/listen/" + message.getToLogin(), message);
		
		Chat chat = chatService.findById(message.getChatId());
		message.setChat(chat);
		chatMessageService.save(message);
		
		chat.setLastMessage(message.getText());
		chat.setLastTime(message.getDate());
		chat.setLastWriter(message.getFromName());
		chatService.save(chat);
	}
}
