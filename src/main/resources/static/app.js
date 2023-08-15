const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});
stompClient.activate();

stompClient.onConnect = (frame) => {
    // setConnected(true);
    // alert('Connected: ' + frame);
    stompClient.subscribe('/topic/tasks', (tasks) => {
        // alert('Received: ' + tasks.body);
    });
};


var dropDownList = document.querySelector('#dropDownList');

var selected = function () {
    return dropDownList.options[dropDownList.selectedIndex].value;
};

dropDownList.addEventListener('change', function() {
    console.log(selected()); // Выводим значение в консоль при изменении выбора
});

function sendTask() {
    stompClient.publish({
        destination: "/app/task",
        body: JSON.stringify({
            'task': $("#task").val(),
            'worker': selected()
        })
    });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#send" ).click(() => sendTask());
});


// stompClient.onWebSocketError = (error) => {
//     console.error('Error with websocket', error);
// };
//
// stompClient.onStompError = (frame) => {
//     console.error('Broker reported error: ' + frame.headers['message']);
//     console.error('Additional details: ' + frame.body);
// };
//
// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }
//
// function connect() {
//     stompClient.activate();
// }
//
// function disconnect() {
//     stompClient.deactivate();
//     setConnected(false);
//     console.log("Disconnected");
// }
//
// function sendName() {
//     stompClient.publish({
//         destination: "/app/hello",
//         body: JSON.stringify({'name': $("#name").val()})
//     });
// }
//
// function showGreeting(message) {
//     $("#greetings").append("<tr><td>" + message + "</td></tr>");
// }
//
// $(function () {
//     $("form").on('submit', (e) => e.preventDefault());
//     $( "#connect" ).click(() => connect());
//     $( "#disconnect" ).click(() => disconnect());
//     $( "#send" ).click(() => sendName());
// });