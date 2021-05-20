let connectUrl = "/ws"

let stompClient;

const connect = () => {
	console.log("connecting to chat");
	let socket = new SockJS(connectUrl);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, (frame) => {
		console.log("connected");
		stompClient.subscribe("/listen/" + myLogin, (response) => {
			
			let messageJson = JSON.parse(response.body);
			
			if(isInChat && toLogin == messageJson.fromLogin) {
				let messagesArea = document.getElementById("messages_area");
			
				let newMessage = document.createElement("div");
				newMessage.classList.add("message", "from");
				
				let messageText = document.createElement("div");
				messageText.classList.add("text");
				messageText.innerHTML = messageJson.text;
				
				timeDifference = new Date().getTime() - lastMessageTime.getTime();
				console.log("time difference " + timeDifference);
				
				if(messagesArea.lastElementChild == null ||
				messagesArea.lastElementChild.classList.contains("to") ||
				timeDifference > 1000*60) {
					let messageName = document.createElement("span");
					messageName.classList.add("name");
					messageName.innerHTML = messageJson.fromName;
					
					let messageTime = document.createElement("span");
					messageTime.classList.add("time");
					let time = new Date(messageJson.date);
					messageTime.innerHTML = time.getHours() + ":" + time.getMinutes();
					
					newMessage.appendChild(messageName);
					newMessage.appendChild(messageTime);
				}
				
				messagesArea.appendChild(newMessage);
				newMessage.appendChild(messageText);
				
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if(isInDialogList) {
				refreshDialogs(messageJson); // then show dialogs
			} else {
				let chatsCount = document.getElementById("chats_count");
				if (chatsCount.innerHTML == "")
					chatsCount.innerHTML = "1";
				else
					chatsCount.innerHTML = parseInt(chatsCount.innerHTML) + 1;
			}
			soundMsgFrom();
		});
	});
}

const soundMsgFrom = () => {
  var audio = new Audio();
  audio.src = 'snd/msg_from.mp3';
  audio.autoplay = true;
}

const refreshDialogs = (message) => {
	let dialogExists = false;
	for(let i = 0; i < dialogs.length; ++i) {
		if(dialogs[i].toLogin == message.fromLogin) {
			dialogs[i].lastTime = message.date;
			dialogs[i].lastWriter = message.fromName;
			dialogs[i].lastMessage = message.text;
			dialogExists = true;
		}
	}
	if(!dialogExists) {
		console.log("length before: " + dialogs.length);
		dialogs.length += 1;
		console.log("length after: " + dialogs.length);
		dialogs[dialogs.length-1] = JSON.parse(JSON.stringify(dialogs[0]));
		console.log("0 = " + JSON.stringify(dialogs[0]));
		
		dialogs[dialogs.length-1].toLogin = message.fromLogin;
		dialogs[dialogs.length-1].toName = message.fromName;
		dialogs[dialogs.length-1].lastTime = message.date;
		dialogs[dialogs.length-1].lastWriter = message.fromName;
		dialogs[dialogs.length-1].lastMessage = message.text;
		console.log("last = " + JSON.stringify(dialogs[length-1]));
	}
	showDialogs();
}

connect();