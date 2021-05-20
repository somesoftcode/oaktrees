package oaktrees.db.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oaktrees.data.ChatMessage;
import oaktrees.db.interfaces.ChatMessageRepository;

@Service
public class ChatMessageService {
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	public void save(ChatMessage message) {
		chatMessageRepository.save(message);
	}
	
	public List<ChatMessage> loadMessages(long chatId, int from, int count) {
		return chatMessageRepository.loadMessages(chatId, from, count);
	}
	
	public long size(long chatId) {
		return chatMessageRepository.size(chatId);
	}

}
