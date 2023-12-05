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
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp"%>
<div class="container" id="main_container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="orange">회원정보 조회 및 수정</mark>
          </h2>
        </div>
        
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
          <input type="hidden" name="action" value="modify" />
          <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userId"
                name="userId"
                value="${userinfo.userId}"
                readOnly
              />
            </div>
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="userName"
                name="userName"
                value="${userinfo.userName} "
              />
            </div>
            
            <div id="result-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="userPassword" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userPassword"
                name="userPassword"
                placeholder="비밀번호..."
              />
            </div>
            <div class="mb-3">
              <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
              <input type="text" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
            </div>
            <div id="password-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="userEmail"
                  name="userEmail"
                  value="${userinfo.userEmail}"
                />
              </div>
            </div>
            <div id="email-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="userPhone" class="form-label">휴대전화번호 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="userPhone"
                  name="userPhone"
                  value="${userinfo.userPhone}"
                />
              </div>
            </div>
            <div id="phone-view" class="mb-3"></div>
            <div class="col-auto text-center">
              <button type="button" id="btn-delete" class="btn btn-outline-success mb-3">
                회원 탈퇴
              </button>
              <button type="button" id="btn-join" class="btn btn-outline-primary mb-3">
                회원 정보 수정
              </button>
              <button type="button" id="btn-main" class="btn btn-outline-primary mb-3">메인으로</button>
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
	// 비밀번호 일치 여부 확인
	let passwordDiv = document.querySelector("#password-view");
	document.querySelector("#pwdcheck").addEventListener("keyup", function () {
		let pwd = document.querySelector("#userPassword").value;
		let cpwd = document.querySelector("#pwdcheck").value;
		if (document.querySelector("#userPassword").value != document.querySelector("#pwdcheck").value){
			passwordDiv.setAttribute("class", "mb-3 fw-bold text-danger");
			passwordDiv.innerHTML = "비밀번호가 일치하지 않습니다.";
		}else{
			passwordDiv.setAttribute("class", "mb-3 fw-bold text-primary");
			passwordDiv.innerHTML = "비밀번호 일치";
		}
      });
	
	// 이메일 양식 검증
	let emailDiv = document.querySelector("#email-view");
	document.querySelector("#userEmail").addEventListener("keyup", function () {
		let userEamil = document.querySelector("#userEmail").value;
		let valid_email = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if(userEamil == '' || userEamil == 'undefined' || !valid_email.test(userEamil)){
			emailDiv.setAttribute("class", "mb-3 fw-bold text-danger");
			emailDiv.innerHTML = "이메일 양식을 확인해주세요";
		}else{
			emailDiv.setAttribute("class", "mb-3 fw-bold text-primary");
			emailDiv.innerHTML = "이메일 양식이 맞습니다.";
		}
      });
	
	// 휴대폰 번호 양식 검증
	let phoneDiv = document.querySelector("#phone-view");
	document.querySelector("#userPhone").addEventListener("keyup", function () {
		let userPhone = document.querySelector("#userPhone").value;
		let valid_phone = /01[016789]-[^0][0-9]{2,3}-[0-9]{3,4}/;
		if(userPhone == '' || userPhone == 'undefined' || !valid_phone.test(userPhone)){
			phoneDiv.setAttribute("class", "mb-3 fw-bold text-danger");
			phoneDiv.innerHTML = "휴대전화번호 양식을 확인해주세요. : 010-1234-5678";
		}else{
			phoneDiv.setAttribute("class", "mb-3 fw-bold text-primary");
			phoneDiv.innerHTML = "휴대전화번호 양식이 맞습니다.";
		}
      });
	
	
	
		// 회원수정
		document.querySelector("#btn-join").addEventListener("click", function() {
			if (!document.querySelector("#userPassword").value) {
	          alert("비밀번호 입력!!");
	          return;
	        } else if (document.querySelector("#userPassword").value != document.querySelector("#pwdcheck").value) {
	          alert("비밀번호 확인!!");
	          return;
	        } else if (!document.querySelector("#userEmail").value) {
	        	alert("이메일 입력!!");
	        	return;
	        }else if (!document.querySelector("#userPhone").value) {
	        	alert("휴대전화번호 입력!!");
	        	return;
	        }else {
	          let form = document.querySelector("#form-modify");
	          form.setAttribute("action", "${root}/member/modify");
	          form.submit();
	        }
		});
		// 글 삭제
		document.querySelector("#btn-delete").addEventListener("click", function () {
	        alert("회원탈퇴합니다.");
	        location.href = "${root}/member/delete?userId=${userinfo.userId}";
	      });
		
		// 메인 화면으로
		document.querySelector("#btn-main").addEventListener("click", function() {
			location.href = "${root}/";
		});
		
	</script>

</body>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</html>