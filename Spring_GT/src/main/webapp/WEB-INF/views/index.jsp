<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
</head>

<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<c:if test="${!empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
	<div
		class="container-fluid p-5 mt-5 text-white d-flex flex-column justify-content-center align-items-center flex-sm-row"
		id="content-main">
		<div id="search" class="w-50 p-5 my-5 border">
			<h1 class="mb-4 ms-auto">어디를 여행하시나요 ?</h1>
			<div id="search-main" class="input-group input-group-lg mb-3">
				<input id="keyword" type="text" class="form-control"
					placeholder="지역, 여행지 이름을 검색해 !" aria-describedby="button-addon2" />
				<button class="btn bg-white text-black" type="button"
					id="button-addon2">Search</button>
			</div>
		</div>
		<div class="d-flex flex-row"></div>
	</div>
	<div id="search-result"
		class="container-fluid d-flex flex-column align-items-center">
		<div class="mx-3 mt-5 mb-3">
			<h1>여행지 주변을 탐색해보세요</h1>
		</div>
		<div class="w-75 d-flex flex-row">
			<form class="d-flex w-100 my-3" onsubmit="return false;"
				role="search">
				<select id="search-area" class="form-select me-2">
					<option value="0" selected>검색 할 지역 선택</option>
				</select> <select id="search-content-id" class="form-select me-2">
					<option value="0" selected>관광지 유형</option>
					<option value="12">관광지</option>
					<option value="14">문화시설</option>
					<option value="15">축제공연행사</option>
					<option value="25">여행코스</option>
					<option value="28">레포츠</option>
					<option value="32">숙박</option>
					<option value="38">쇼핑</option>
					<option value="39">음식점</option>
				</select> <input id="search-keyword" class="form-control me-2" type="search"
					placeholder="검색어" aria-label="검색어" />
				<button id="btn-search" class="btn btn-success" type="button">
					Search</button>
			</form>
		</div>
		<div class="d-flex w-75 flex-column">
			<div id="map" class="w-100" style="height: 500px"></div>
			<div class="row mt-5">
				<table id="place-list" class="table table-striped"
					style="display: none">
					<thead>
						<tr class="text-center">
							<th>대표이미지</th>
							<th>관광지명</th>
							<th>주소</th>
							<th>위도</th>
							<th>경도</th>
						</tr>
					</thead>
					<tbody id="trip-list"></tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="container">
		<footer
			class="d-flex flex-wrap justify-content-center py-3 my-4 border-top">
			<div
				class="col-md-4 d-flex justify-content-center align-items-center">
				<a href="/"
					class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
					<h6>© EnjoyTrip - SSAFY</h6>
				</a>
			</div>
		</footer>
	</div>

	<!-- Latest compiled JavaScript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
	<script type="text/javascript">console.log("hellohello")</script>
	<script src="/assets/js/main.js" type="text/javascript"></script>
</body>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</html>
