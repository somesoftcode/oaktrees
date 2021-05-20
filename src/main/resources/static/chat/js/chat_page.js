let myLogin, toLogin;
let myName, toName;

let isInChat = true;
isInDialogList = false;

let chatId;

let lastMessageTime = new Date();

let firstLoad = true;
let loadedMessages = 0;
let lastPortionOfMessages = 0;
let allMessages = new Array();


document.addEventListener('DOMContentLoaded', () => {
	setLogin();
	setMyName();
	toLogin = document.location.pathname.substr(6, document.location.pathname.length);
	setToNameToTitle();
	setChatId(); // load messages in it
});

const setMyName = () => {
	const request = new XMLHttpRequest();
	const url = "/chat/myname";
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			myName = request.responseText;
		}
	});
	request.send();
}
const setChatId = () => {
	const request = new XMLHttpRequest();
	const url = "/chat/chat_id?toLogin=" + toLogin;
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			chatId = request.responseText;
			
			//load messages
			loadMessages();
		}
	});
	request.send();
}
const setToNameToTitle = () => {
	const request = new XMLHttpRequest();
	const url = "/chat/myname/" + toLogin;
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			toName = request.responseText;
			document.getElementById("title").innerHTML = toName;
		}
	});
	request.send();
}

const setLogin = () => {
	const request = new XMLHttpRequest();
	const url = "/chat/mylogin";
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			myLogin = request.responseText;
		}
	});
	request.send();
}


const sendMessage = () => {
	let now = new Date();
	let text = document.getElementById("textarea").value;
	stompClient.send("/send/message", {}, JSON.stringify({
		fromLogin: myLogin,
		fromName: myName,
		toLogin: toLogin,
		text: text,
		date: now,
		chatId: chatId
	}));
	
	let messagesArea = document.getElementById("messages_area");
	
	let newMessage = document.createElement("div");
	newMessage.classList.add("message", "to");
	
	
	let messageText = document.createElement("div");
	messageText.classList.add("text");
	messageText.innerHTML = text;
	
	timeDifference = new Date().getTime() - lastMessageTime.getTime();
			console.log("time difference " + timeDifference);
	
	if(messagesArea.lastElementChild == null ||
	messagesArea.lastElementChild.classList.contains("from") ||
	timeDifference > 1000*60) {
		let messageName = document.createElement("span");
		messageName.classList.add("name");
		messageName.innerHTML = myName;
		
		let messageTime = document.createElement("span");
		messageTime.classList.add("time");
		messageTime.innerHTML = now.getHours() + ":" + now.getMinutes();
		
		newMessage.appendChild(messageName);
		newMessage.appendChild(messageTime);
	}
	
	messagesArea.appendChild(newMessage);
	newMessage.appendChild(messageText);
	
	lastMessageTime = now;
	messagesArea.scrollTop = messagesArea.scrollHeight;
}

const send = () => {
	let ta = document.getElementById("textarea");
	if (ta.value != "" && ta.value != "\n") {
		sendMessage();
		ta.value = "";
	} else if(ta.value = '\n'){
		console.log("enter");
		ta.value = "";
	}
}
	
const clearEmptyInput = () => {
	let ta = document.getElementById("textarea");
	if(ta.value == '\n') {
		ta.value = "";
	}
}


const fixInputAreaSize = () => {
	let inputArea = document.getElementById("dialog_input");
	let center = document.getElementById("center");
	
	inputArea.style.width = window.getComputedStyle(center).width;
}

setInterval(fixInputAreaSize , 1000);


setInterval(clearEmptyInput, 500);

const printScrollDegree = () => {
	let scrollArea = document.getElementById("messages_area");
	const hasVerScroll = scrollArea.scrollHeight > scrollArea.clientHeight;
	console.log(scrollArea.scrollHeight + " " + scrollArea.clientHeight + " " + scrollArea.scrollTop + " " + hasVerScroll);
	//console.log(scrollArea.scroll);
	if( (scrollArea.scrollTop == 0) && (scrollArea.clientHeight < scrollArea.scrollHeight))  {
		loadMessages();
	}
}
setInterval(printScrollDegree, 1000);

const loadMessages = () => {
	const request = new XMLHttpRequest();
	const par = "chatId=" + chatId + "&loaded=" + loadedMessages;
	const url = "/chat/load_messages?" + par;
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			if(request.responseText != "thatisall") {
				lastPortionOfMessages = JSON.parse(request.responseText).messages.length;
				allMessages = allMessages.concat(JSON.parse(request.responseText).messages);
				loadedMessages = allMessages.length;
				console.log("loaded messages: " + loadedMessages);
				addMessages();
			} else {
				console.log("that is all");
			}
			
		}
	});
	request.send();
}
const addMessages = () => {
	const messagesArea = document.getElementById("messages_area");
	messagesArea.innerHTML = "";
	for(let i = allMessages.length-1; i >= 0 ; --i) {
		
		let mes = document.createElement("div");
		if(allMessages[i].fromLogin == myLogin) {
			mes.classList.add("to");
		}
		
		let weNeedName = (i < allMessages.length-1) &&
		(allMessages[i+1].fromName != allMessages[i].fromName) ||
		(i == allMessages.length-1) ||
		(i < allMessages.length-1) &&
		(new Date(allMessages[i].date).getTime() - new Date(allMessages[i+1].date).getTime() > 1000); // one second
		
		if(weNeedName) {
			let messageName = document.createElement("span");
			messageName.classList.add("name");
			messageName.innerHTML = allMessages[i].fromName;;
			
			let messageTime = document.createElement("span");
			messageTime.classList.add("time");
			let time = new Date(allMessages[i].date);
			messageTime.innerHTML = time.getHours() + ":" + time.getMinutes();
			
			mes.appendChild(messageName);
			mes.appendChild(messageTime);
		}
		
		let messageText = document.createElement("div");
		messageText.classList.add("text");
		messageText.innerHTML = allMessages[i].text;
		
		mes.appendChild(messageText);
		mes.classList.add("message");
		
		messagesArea.appendChild(mes);
		
	}
	
	// scroll
	let messageHeight = window.getComputedStyle(document.getElementsByClassName("message")[0]).height.replace("px", "");
	if(firstLoad == true) {
		firstLoad = false;
		messagesArea.scrollTop = messagesArea.scrollHeight;
	} else {
		messagesArea.scrollTop = messageHeight*lastPortionOfMessages;
	}
	/*messagesArea.scrollTop = messagesArea.scrollHeight - (messagesArea.clientHeight + messageHeight)
	- ((loadedMessages) * scr0);
	console.log("scr0 = " + scr0);*/
}
