<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>채팅목록</title>
<link href='<c:url value="/resources/css/board/list.css"/>' rel="stylesheet" type="text/css">
<link href='<c:url value="/resources/css/include/paging.css"/>' rel="stylesheet" type="text/css">
<style>
.my_chat {
  margin-top: 10px;
  margin-right: 20px;
  margin-bottom:20px;
  border-radius: 20px;
  border:1px solid #ea5936;
  width:200px;
}
#chat_container {
  display: inline-block;
  position: relative;
  color: #fff;
  padding: 7px 12px;
  margin-bottom: 10px;
  max-width: 230px;
}
</style>
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	<jsp:include page="../include/nav.jsp"/>
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3><sec:authentication property="principal.member.user_name"/>님의 채팅 목록</h3>
			</div><br>
			<div style="text-align:center;">
				<!-- 
					1. 반드시 사용자 중에서 본인을 제외한 사용자만 조회하여 출력하세요!! 
					2. 대화상대의 user_no 정보를 value에 작성하세요.
				-->
				<label for="receiver_no">대화상대</label>
				<input type="text" value="${receiver_no}" id="receiver_no"/>
				<%-- <select id="receiver_no">
					<sec:authentication property="principal.member.user_no" var="sender_no"/>
					<c:out value="${sender_no}"/>
					<c:if test="${sender_no == '1'}">
						<option value="2">user01</option>
					</c:if>
					<c:if test="${sender_no == '2'}">
						<option value="1">admin</option>
					</c:if>
				</select> --%>
				<br>
				<textarea id="txt_msg" rows="3" placeholder="채팅 입력하기"></textarea>
				<br>
				<input type="hidden" id="sender_no" value="<sec:authentication property="principal.member.user_no"/>">
				<input type="button" id="send_btn" value="보내기">
				<br>
				<div id="chat_container">
					
				</div>
			</div>
		</div>
	</section>
	<script>
		// 1. 웹소켓 서버에 연결하기
		// http 프로토콜을 이용 : ws
		// https 프로토콜을 이용 : wss
		const websocket 
		= new WebSocket("ws://localhost:8089/<%=request.getContextPath()%>chatting");
	
		
		// 3. 서버로부터 받아온 데이터를 처리하는 함수
		websocket.onmessage = (response) =>{
			//console.log("=== 응답 ===");
			const resp = JSON.parse(response.data);
			if(resp.res_code == '200'){
				if(resp.res_type == 'open'){
					printMsg(resp.res_msg,'center');
				} else if(resp.res_type == 'msg'){
					// 메시지 보낸 사람과 지금 화면의 주인이 같은지
					const senderInfo = resp.sender_info;
					const hostPage = document.getElementById("sender_no").value;
					
					if(Number(senderInfo) == Number(hostPage)){
						printMsg(resp.res_msg,'right');
					}else{
						printMsg(resp.res_msg,'left');
					}
					
				}
			} else{
				alert(res_msg);
			}	
		}
		
		// 화면에 메시지 출력하는 함수
		const printMsg = function(msg,loc){
			const div = document.createElement("div");
			const textNode = document.createTextNode(msg);
			div.appendChild(textNode);
			div.className += 'my_chat';
			div.style.textAlign = loc;
			document.getElementById("chat_container").appendChild(div);
		}
		
		// 4. 서버에 접속이 끊겼을 때 실행될 함수
		websocket.onclose = () =>{
			//console.log("=== 끊김 ===");
		}
		
		// 5. 버튼을 클릭하면 양방향 통신 시작
		document.getElementById("send_btn").addEventListener('click',function(){
			/* const msg = document.getElementById("txt_msg").value;
			const sender = document.getElementById("sender_no").value;
			const receiver = document.getElementById("receiver_no").value;
			const obj = {
				chat_msg : msg,
				sender_no : sender,
				receiver_no : receiver
			};
			websocket.send(JSON.stringify(obj)); */
			
			const msg = document.getElementById("txt_msg").value;
			const obj = setMsgObj('msg',msg);
			websocket.send(JSON.stringify(obj));
		});
		// 2. 서버에 접속이 되었을 때 실행될 함수
		websocket.onopen = (data) =>{
			// console.log("=== 접속 ===");
			const obj = setMsgObj("open",'');
			websocket.send(JSON.stringify(obj));	
		}
		
		// 객체 생성자 함수
		let setMsgObj = function(chatType, chatMsg){
			const sender = document.getElementById("sender_no").value;
			const receiver = document.getElementById("receiver_no").value;
			const obj = {
				chat_type : chatType,
				chat_msg : chatMsg,
				sender_no : sender,
				receiver_no : receiver
			}
			return obj;
		}
		
	
	</script>
</body>
</html>