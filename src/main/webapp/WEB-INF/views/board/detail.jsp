<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 상세</title>
<link href="<c:url value='/resources/css/board/detail.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../include/header.jsp"/>
	<jsp:include page="../include/nav.jsp"/>
	<section>	
		<div id="section_wrap">
			<div class="word">
				<h3>게시글 상세 보기</h3>
			</div>
			<div class="board_detail">
				<ul>
					<li>
						<figure>
							<figcaption>
								<c:out value="${board.ori_thumbnail }"/>
							</figcaption>
						</figure>
						<img src="<c:url value='/boardUploadImg/${board.new_thumbnail }'/>" >

					</li>
					<li>
						<table>
							<tr>
								<td>게시글 제목</td>
								<td><c:out value='${board.board_title }'/></td>
							</tr>
							<tr>
								<td>게시글 내용</td>
								<td><c:out value='${board.board_content }'/></td>
							</tr>
							<tr>
								<td>등록일</td>
								<td>
									<fmt:formatDate value="${board.reg_date }"
											pattern="yy-MM-dd"/>
								</td>
							</tr>
						</table>
					</li>
				</ul>
				
			</div>
			
			<div class="buttons">
				<a class="update_board_button" href="<c:url value='/board/update/${board.board_no }'/>">게시글 수정</a>
				<a class="delete_board_button" href="#" onclick="boardDelete()">게시글 삭제</a>			
			</div>
		</div>
	</section>
	<script type="text/javascript">
	const boardDelete = function() {
    if (confirm("정말 삭제하시겠습니까?")) {
        fetch('/board/${board.board_no }', {
            method: 'delete' ,
            headers : {
				"Content-Type" : "application/json;charset=utf-8",
				"Accept" : "application/json",
				"X-CSRF-TOKEN" : "${_csrf.token}"
            	}
			})
        .then(response => response.json())
        .then(data => {
            alert(data.res_msg);
            if (data.res_code == '200') {
                location.href = "<%=request.getContextPath()%>/board";  
            } else {
                alert("게시글 삭제에 실패하였습니다.");
            }
        })
    }
}
	</script>	
</body>
</html>