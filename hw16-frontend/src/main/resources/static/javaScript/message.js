let stompClient = null;

const setConnected = (connected) => {
  if (connected) {
    $("#userList").show();
  } else {
    $("#userList").hide();
  }
}

const connect = () => {
  var loc = window.location;
  var url = '//' + loc.host + loc.pathname + '/userSaveWebsocket';
  stompClient = Stomp.over(new SockJS(url));
  stompClient.connect({}, (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/DBServiceResponse.',
      (DBServiceMessage) => showUserList(JSON.parse(DBServiceMessage.body)));
  });
}

const disconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

const sendMessage = () => {
  let newUser = {
    name: $("#name").val,
    age: $("#age").val,
    login: $("#login").val
  };
  stompClient.send("/app/userSaveMessage", {}, JSON.stringify({'messageStr': newUser}));
}


const showUserList = (jsonUser) => {
  let userTbl = '';
  userTbl += '<tr>';
  userTbl += '<td>' + jsonUser.name + '</td>';
  userTbl += '<td>' + jsonUser.age + '</td>';
  userTbl += '<td>' + jsonUser.login + '</td>';
  userTbl += '</tr>';
  $("#userList").append(userTbl);
}

$(document).ready(connect());

$(function () {
  $("form").on('submit', (event) => {event.preventDefault();});
  $("#connect").click(connect);
  $("#disconnect").click(disconnect);
  $("#saveUserButton").click(sendMessage);
});
