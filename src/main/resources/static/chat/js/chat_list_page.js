let myLogin;
let myName, toName;

let isInChat = false;
isInDialogList = true;

let dialogs = new Array();

document.addEventListener('DOMContentLoaded', () => {
	setLogin();
	setMyName();
	getDialogs();
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

const getDialogs = () => {
	const request = new XMLHttpRequest();
	const url = "/chat/dialogs";
	request.open("get", url);
	request.addEventListener('readystatechange', () => {
		if( (request.readyState == 4) && (request.status == 200) ) {
			dialogs = JSON.parse(request.responseText).dialogs;
			showDialogs();
		}
	});
	request.send();
}
const showDialogs = () => {
	let center = document.getElementById("center");
	center.innerHTML = '<div class="dialog_info">' +
					'<span id="title">Переписки</span>' +
				'</div>';
				
	//sort
	for(let border = 0; border < dialogs.length; ++border) {
		let value = dialogs[border];
		let i = border -1;
		for(; i >= 0; --i) {
			if(new Date(value.lastTime).getTime() > new Date(dialogs[i].lastTime).getTime()) dialogs[i+1] = dialogs[i];
			else break;
		}
		dialogs[i+1] = value;
	}
	
	for(let i = 0; i < dialogs.length; ++i) {
		if(dialogs[i].lastWriter != null) {
			let newDialog = document.createElement('div');
			newDialog.classList.add("dialog");
			
			let newDialogNameArea = document.createElement('div');
			newDialogNameArea.classList.add("dialog_name");
			
			let newDialogName = document.createElement('span');
			newDialogName.classList.add("name");
			let newDialogTime = document.createElement('span');
			newDialogTime.classList.add("time");
			
			newDialogName.innerHTML = dialogs[i].toName;
			let time = new Date(dialogs[i].lastTime);
			newDialogTime.innerHTML = time.getHours() + ":" + time.getMinutes();
			
			newDialogNameArea.appendChild(newDialogName);
			newDialogNameArea.appendChild(newDialogTime);
			
			let messageArea = document.createElement('div');
			
			let lastWriter = document.createElement('span');
			lastWriter.classList.add("last_writer");
			lastWriter.innerHTML = dialogs[i].lastWriter + ": ";
			
			let lastMessage = document.createElement('span');
			lastMessage.classList.add("dialog_message");
			lastMessage.innerHTML = dialogs[i].lastMessage;
			
			messageArea.appendChild(lastWriter);
			messageArea.appendChild(lastMessage);
			
			newDialog.appendChild(newDialogNameArea);
			newDialog.appendChild(messageArea);
			center.appendChild(newDialog);
		}
	}
}
