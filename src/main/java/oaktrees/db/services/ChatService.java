package oaktrees.db.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oaktrees.data.Chat;
import oaktrees.data.ChatsJson;
import oaktrees.data.User;
import oaktrees.db.interfaces.ChatRepository;
import oaktrees.db.interfaces.UserRepository;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Chat findById(long id) {
		return chatRepository.findById(id);
	}
	
	public void save(Chat chat) {
		chatRepository.save(chat);
	}
	
	public void create(String login1, String login2) {
		
		User u1 = userRepository.findByLogin(login1);
		User u2 = userRepository.findByLogin(login2);
		
		Chat chat = new Chat(Set.of(u1, u2));
		chatRepository.save(chat);
	}
	
	public List<Long> usersChats(String login) {
		long userId = userRepository.findByLogin(login).getId();
		return chatRepository.findAllByParticipantId(userId);
	}
	
	public List<Long> participants(long id) {
		return chatRepository.findAllparticipantsById(id);
	}
	public List<Long> participantsWithoutMe(long chatId, long myId) {
		return chatRepository.findAllparticipantsWithoutMe(chatId, myId);
	}
	
	public boolean exist(String login1, String login2) {
		
		ArrayList<Long> chats = (ArrayList<Long>) usersChats(login1);
		User me = userRepository.findByLogin(login1);
		
		for(long chat : chats) {
			ArrayList<Long> participants = (ArrayList<Long>) participantsWithoutMe(chat, me.getId());
			for(long participant : participants) {
				User user = userRepository.findById(participant);
				if(user.getLogin().equals(login2)) return true;
			}
		}
		return false;
	}
	
	public long getChatId(String login1, String login2) {
		ArrayList<Long> chats1 = (ArrayList<Long>) usersChats(login1);
		ArrayList<Long> chats2 = (ArrayList<Long>) usersChats(login2);
		for(long i : chats1) {
			for(long j : chats2) {
				if (i == j) return i;
			}
		}
		return -1;
	}
	
	public void updateLastMessage(long chatId, String lastMessage) {
		chatRepository.updateLastMessage(chatId, lastMessage);
		System.out.println("chatId to update = " + chatId);
		System.out.println("LastMessage to update = " + lastMessage);
	}
	
	public ChatsJson MyChatsJson(String login) {
		
		User user = userRepository.findByLogin(login);
		ArrayList<Long> chats = (ArrayList<Long>) usersChats(login);
		
		ChatsJson ans = new ChatsJson(chats.size());
		
		for(int i = 0; i < chats.size(); ++i) {
			Chat chat = chatRepository.findById( chats.get(i).longValue() );
			Long chatWithHimId = participantsWithoutMe(chat.getId(), user.getId()).get(0);
			User chatWithHim = userRepository.findById(chatWithHimId.longValue());
			ans.configureChat(i, chatWithHim.getLogin(), chatWithHim.getName(),
					chat.getLastTime(), chat.getLastMessage(), chat.getLastWriter());
		}
		return ans;
	}
	
}
