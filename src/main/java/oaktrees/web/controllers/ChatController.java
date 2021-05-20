package oaktrees.web.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import oaktrees.data.ChatMessage;
import oaktrees.data.ChatsJson;
import oaktrees.data.MessagesJson;
import oaktrees.db.services.ChatMessageService;
import oaktrees.db.services.ChatService;
import oaktrees.db.services.UserService;

@Controller
@RequestMapping("/chat")
@PreAuthorize("hasAuthority('user_p')")
public class ChatController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@GetMapping("/create")
	public String create() {
		return "chat/create";
	}
	
	@PostMapping("/creation_processing")
	public ModelAndView creationPrecessing(
			@RequestParam("withwho") String login
			) {
		
		String creatorLogin = ((UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();
		
		if(creatorLogin.equals(login)) System.out.println("логины совпадают");
		else {
			if(userService.exist(login)) {
				if(!chatService.exist(creatorLogin, login)) {
					chatService.create(login, creatorLogin);
				}
			}
		}
		return new ModelAndView("redirect:" + "/chat/" + login);
	}
	
	@GetMapping("/{chatWith}")
	public String chatTo(
			@PathVariable String chatWith
			) {
		
		System.out.println("чат с " + chatWith);
		
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		String toReturn = "chat/chat_page";
		
		if(!chatService.exist(login,  chatWith)) {
			toReturn = "chat/chat_doesnt_exist";
		}
		
		return toReturn;
	}
	
	@GetMapping("/mylogin")
	@ResponseBody
	public String myLogin() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@GetMapping("/myname")
	@ResponseBody
	public String myName() {
		return userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getName();
	}
	@GetMapping("/chat_id")
	@ResponseBody
	public String chatId(
			@RequestParam("toLogin") String toLogin) {
		String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("toLogin = " + toLogin);
		return String.valueOf(chatService.getChatId(myLogin, toLogin));
	}
	
	@GetMapping("/myname/{login}")
	@ResponseBody
	public String myNameLogin(@PathVariable String login) {
		return userService.findByLogin(login).getName();
	}
	
	@GetMapping("/list")
	public String list() {
		return "chat/chats_list";
	}
	
	@GetMapping("/dialogs")
	@ResponseBody
	public String getDialogs() throws JsonProcessingException {
		
		String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		ChatsJson chats = chatService.MyChatsJson(myLogin);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chats);
		
		return json;
	}
	
	@GetMapping("/load_messages")
	@ResponseBody
	public String load_messages(
			@RequestParam("chatId") long chatId,
			@RequestParam("loaded") int loaded
			) throws JsonProcessingException {
		
		System.out.println("loaded = " + loaded);
		System.out.println("size = " + chatMessageService.size(chatId));
		if(loaded >= chatMessageService.size(chatId)) {
			return "thatisall";
		}
		
		ArrayList<ChatMessage> messages =
				(ArrayList<ChatMessage>) chatMessageService.loadMessages(chatId, loaded, 50);
		
		MessagesJson ans = new MessagesJson(messages);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ans);
		return json;
		
	}
	
}
