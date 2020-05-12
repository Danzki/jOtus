let stompClient = null;

const setConnected = (connected) => {
  if (connected) {
    $("#usersList").show();
  } else {
    $("#usersList").hide();
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
  stompClient.send("/app/userList", {}, "getUserList");
}


const showUserList = (jsonUser) => {
  var tbody = $("#usersListTbl");
  for (var i = 0; i < jsonUser.length; i++) {
    let userTbl = '';
    userTbl += '<tr>';
    userTbl += '<td>' + jsonUser[i].name + '</td>';
    userTbl += '<td>' + jsonUser[i].age + '</td>';
    userTbl += '<td>' + jsonUser[i].login + '</td>';
    userTbl += '</tr>';
    tbody.append(userTbl);
  }
}

$(document).ready(connect());

$(function () {
  $("#getUserList").click(sendMessage);
});
