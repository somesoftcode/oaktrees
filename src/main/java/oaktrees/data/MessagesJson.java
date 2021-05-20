package oaktrees.data;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessagesJson {
	
	@JsonProperty("messages")
	private Message[] messages;

	public MessagesJson(ArrayList<ChatMessage> messages) {
		this.messages = new Message[messages.size()];
		for(int i = 0; i < this.messages.length; ++i) {
			this.messages[i] = new Message();
			this.messages[i].setFromName(messages.get(i).getFromName());
			this.messages[i].setFromLogin(messages.get(i).getFromLogin());
			this.messages[i].setDate(messages.get(i).getDate());
			this.messages[i].setText(messages.get(i).getText());
		}
	}
	
	class Message {
		@JsonProperty("fromLogin")
		private String fromLogin;
		@JsonProperty("fromName")
		private String fromName;
		@JsonProperty("date")
		private Date date;
		@JsonProperty("text")
		private String text;
		public String getFromLogin() {
			return fromLogin;
		}
		public void setFromLogin(String fromLogin) {
			this.fromLogin = fromLogin;
		}
		public String getFromName() {
			return fromName;
		}
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

}
