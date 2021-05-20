package oaktrees.db;

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
public class DbWorker {
	
	private final UserRepository userRepository;
	private final ChatRepository chatRepository;
	
	@Autowired
	public DbWorker(ChatRepository c, UserRepository u) {
		this.chatRepository = c;
		this.userRepository = u;
	}
	
	// USER
	
	public User findUserById(long id) {
		return userRepository.findById(id);
	}
	
	public User findUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	public void insert(User user) {
		userRepository.save(user);
	}
	
	public boolean isUserExists(String login) {
		return userRepository.exist(login) == 0 ? false : true;
	}
	
	// CHAT
	
	public void createChat(String login1, String login2) {
		Chat chat = new Chat(Set.of(userRepository.findByLogin(login1), 
				userRepository.findByLogin(login2)));
		chatRepository.save(chat);
	}
	
	public List<Long> findAllChatsByLogin(String login) {
		long userId = userRepository.findByLogin(login).getId();
		return chatRepository.findAllByParticipantId(userId);
	}
	
	public List<Long> findAllParticipantsById(long id) {
		return chatRepository.findAllparticipantsById(id);
	}
	public List<Long> findAllParticipantsWithoutMe(long chatId, long myId) {
		return chatRepository.findAllparticipantsWithoutMe(chatId, myId);
	}
	
	public boolean isChatExist(String login1, String login2) {
		ArrayList<Long> chats = (ArrayList<Long>) findAllChatsByLogin(login1);
		User me = userRepository.findByLogin(login1);
		for(long chat : chats) {
			ArrayList<Long> participants = (ArrayList<Long>) findAllParticipantsWithoutMe(chat, me.getId());
			for(long participant : participants) {
				User user = userRepository.findById(participant);
				if(user.getLogin().equals(login2)) return true;
			}
		}
		return false;
	}
	
	public ChatsJson getChatsJson(String login) {
		User user = userRepository.findByLogin(login);
		ArrayList<Long> chats = (ArrayList<Long>) findAllChatsByLogin(login);
		
		ChatsJson ans = new ChatsJson(chats.size());
		
		for(int i = 0; i < chats.size(); ++i) {
			Chat chat = chatRepository.findById( chats.get(i).longValue() );
			Long chatWithHimId = findAllParticipantsWithoutMe(chat.getId(), user.getId()).get(0);
			User chatWithHim = userRepository.findById(chatWithHimId.longValue());
			ans.configureChat(i, chatWithHim.getLogin(), chatWithHim.getName(),
					chat.getLastTime(), chat.getLastMessage(), chat.getLastWriter());
		}
		return ans;
	}

}
