package oaktrees.db.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import oaktrees.data.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

	@Query(value="select * from messages where chat_id = :chatId order by date desc limit :from, :count",
			nativeQuery=true)
	List<ChatMessage> loadMessages(long chatId, int from, int count);
	
	@Query(value="select count(*) from messages where chat_id = :chatId",
			nativeQuery=true)
	long size(long chatId);
	
}
