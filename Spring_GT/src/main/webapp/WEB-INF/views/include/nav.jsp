<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 지시어 태그 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

 <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
<div class="container">
	<a href="${root}" class="navbar-brand text-primary fw-bold" href="#"> <img
		src="img/EnjoyTrip.png" alt="" width="120" />
	</a>
	<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
		data-bs-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<!-- 로그인 전  -->
		<c:if test="${empty userinfo}">
			<nav class="navbar navbar-expand-sm bg-light navbar-light">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link"
						href="${root}/member/join">회원가입</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/member/login">로그인</a></li>
				</ul>
			</nav>
		</c:if>

		<c:if test="${not empty userinfo}">
			<nav class="navbar navbar-expand-sm bg-light navbar-light">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><strong>${userinfo.userName}</strong>
						(${userinfo.userId})님 안녕하세요. <a
						href="${root}/member/logout">로그아웃</a></li>
				</ul>
			</nav>

			<nav class="navbar navbar-expand-sm bg-light navbar-light">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link"
						href="${root}/board/list?pgno=1&key=&word=">여행정보 공유</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/member/modify">회원정보조회및수정</a></li>
				</ul>
			</nav>
		</c:if>



	</div>
</nav>
