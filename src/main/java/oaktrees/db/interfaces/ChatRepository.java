package oaktrees.db.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import oaktrees.data.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	@Query(value="select chat_id from chat_user where user_id = :id",
			nativeQuery=true)
	public List<Long> findAllByParticipantId(Long id);
	
	@Query(value="select user_id from chat_user where chat_id = :id",
			nativeQuery=true)
	public List<Long> findAllparticipantsById(Long id);
	
	@Query(value="select user_id from chat_user where chat_id = :chatId and user_id != :myId",
			nativeQuery=true)
	public List<Long> findAllparticipantsWithoutMe(Long chatId, Long myId);
	
	@Query(value="select chat_id from chat_user where user_id = :id",
			nativeQuery=true)
	public List<Long> getChats(Long id);
	
	public Chat findById(long id);
	
	@Query(value="update chats set last_message = :lastMessage where id = :chatId",
			nativeQuery = true)
	public void updateLastMessage(long chatId, String lastMessage);
	
}
