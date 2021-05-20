package oaktrees.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="chats")
public class Chat {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="title")
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="chat_user",
			joinColumns = @JoinColumn(name="chat_id"),
			inverseJoinColumns = @JoinColumn(name="user_id")
	)
	private Collection<User> participants;
	
	@OneToMany
	@JoinTable(
			name="chat_message",
			joinColumns = @JoinColumn(name="chat_id"),
			inverseJoinColumns = @JoinColumn(name="message_id")
	)
	private List<ChatMessage> messages;
	
	@Column(name="is_group")
	private boolean isGroup;
	
	@Column(name="last_message")
	private String lastMessage;
	
	@Column(name="last_writer")
	private String lastWriter;
	
	@Column(name="last_time")
	private Date lastTime;
	
	public Chat() {}
	
	public Chat(Collection<User> participants) {
		this.participants = participants;
		if(participants.size() == 2) this.isGroup = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getLastWriter() {
		return lastWriter;
	}

	public void setLastWriter(String lastWriter) {
		this.lastWriter = lastWriter;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}
	
}
