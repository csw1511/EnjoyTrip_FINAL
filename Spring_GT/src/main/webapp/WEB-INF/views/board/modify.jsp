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
      <div class="row justify-content-center" id="main_container">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">글수정</mark>
          </h2>
        </div>
        
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
          	<!-- 원본 : value="${pgno}" 으로 고치기 -->
          	<input type="hidden" name="pgno" value="1">
		    <input type="hidden" name="key" value="${key}">
		   	<input type="hidden" name="word" value="${word}">
          	<input type="hidden" name="articleNo" value="${article.articleNo}">
          	
            <div class="mb-3">
              <label for="subject" class="form-label">제목 : </label>
              <input type="text" class="form-control" id="subject" name="subject" value="${article.subject}" />
            </div>
            
            <div class="mb-3">
              <label for="content" class="form-label">내용 : </label>
              <textarea class="form-control" id="content" name="content" rows="7">${article.content}</textarea>
            </div>
            
            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3">
                글수정
              </button>
              <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
                목록으로이동...
              </button>
            </div>
          </form>
        </div>
        
      </div>
    </div>
    <form id="form-param" method="get" action="">
      <input type="hidden" id="pgno" name="pgno" value="${pgno}">
      <input type="hidden" id="key" name="key" value="${key}">
      <input type="hidden" id="word" name="word" value="${word}">
    </form>
    <script>
    // 회원 수정
      document.querySelector("#btn-modify").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-modify");
          form.setAttribute("action", "${root}/board/modify");
          form.submit();
        }
      });

    // 글목록으로
      document.querySelector("#btn-list").addEventListener("click", function () {
        location.href = "${root}/board/list?pgno=1&key=&word=";
      });
    </script>
</body>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</html>
