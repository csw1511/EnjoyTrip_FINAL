<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style type="text/css">
#main_container {
	margin-top: 100px;
}
 * {
  font-family: "Noto Sans KR", sans-serif;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>

	<c:if test="${!empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>

	<div class="container" id="main_container">
		<div class="row justify-content-center">
		
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="orange">로그인</mark>
				</h2>
			</div>
			
			<div class="col-lg-8 col-md-10 col-sm-12">
				<form id="form-login" method="POST" action="">
					<input type="hidden" name="action" value="login" />
					<div>
						<label for="userId" class="col-form-label"> 아이디 <input
							type="text" class="form-control validate" id="userId"
							name="userId" placeholder="Enter ID" /></label>
					</div>
					<div>
						<label for="userPw" class="my-2"> 비밀번호 <input
							type="password" class="form-control validate" id="userPassword"
							name="userPassword" placeholder="Password" />
						</label>
					</div>
					<div class="col-auto text-center">
						<button type="button" id="btn-login"
							class="btn btn-outline-primary mb-3">로그인</button>
						<button type="button" id="btn-join"
							class="btn btn-outline-success mb-3">회원가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
	<script>
		// 로그인 버튼 이벤트 처리

		const btnLogin = document.querySelector("#btn-login");
		console.log(btnLogin);

		btnLogin.addEventListener("click", function() {
			console.log("dfdf");
			if (!document.querySelector("#userId").value) {
				alert("아이디 입력!!");
				return;
			} else if (!document.querySelector("#userPassword").value) {
				alert("비밀번호 입력!!");
				return;
			} else {
				console.log("dd");
				let form = document.querySelector("#form-login");
				form.setAttribute("action", "${root}/member/login");
				form.submit();
			}
		});

		// 회원가입 버튼 이벤트 처리
		document.querySelector("#btn-join").addEventListener("click",
				function() {
					location.href = "${root}/member/join";
				});
	</script>

</body>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</html>
