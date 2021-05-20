package oaktrees.data;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatsJson {

	@JsonProperty("dialogs")
	private Dialog[] dialogs;
	
	class Dialog {
		@JsonProperty("toLogin")
		public String toLogin;
		@JsonProperty("toName")
		public String toName;
		@JsonProperty("lastTime")
		public Date lastTime;
		@JsonProperty("lastMessage")
		public String lastMessage;
		@JsonProperty("lastWriter")
		public String lastWriter;
		public String getToLogin() {
			return toLogin;
		}
		public void setToLogin(String toLogin) {
			this.toLogin = toLogin;
		}
		public String getToName() {
			return toName;
		}
		public void setToName(String toName) {
			this.toName = toName;
		}
		public Date getLastTime() {
			return lastTime;
		}
		public void setLastTime(Date lastTime) {
			this.lastTime = lastTime;
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
		
	}
	
	public ChatsJson() {}
	
	public ChatsJson(int n) {
		dialogs = new Dialog[n];
	}

	public Dialog[] getDialogs() {
		return dialogs;
	}
	
	public void configureChat(
			int i,
			String toLogin, String toName,
			Date lastTime, String lastMessage, String lastWriter) {
		this.dialogs[i] = new Dialog();
		this.dialogs[i].toLogin = toLogin;
		this.dialogs[i].toName = toName;
		this.dialogs[i].lastTime = lastTime;
		this.dialogs[i].lastMessage = lastMessage;
		this.dialogs[i].lastWriter = lastWriter;
	}

	@Override
	public String toString() {
		return "ChatsJson [dialogs=" + Arrays.toString(dialogs) + "]";
	}
	
	
}
