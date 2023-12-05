<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
    />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;200;300;400;500;600;700;800;900&display=swap"
      rel="stylesheet"
    />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link rel="stylesheet" href="main.css" />
    <title>Enjoy Trip</title>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a09d5bfdde11573ff49888044537b34d&libraries=services,clusterer,drawing"
    ></script>
  </head>
  
  <body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
      <div class="container">
        <input type="hidden" id="login-check" name="login" value="false" />
        <a href="/" class="navbar-brand text-primary fw-bold" href="#">
          <img src="img/EnjoyTrip.png" alt="" width="120" />
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <!-- 로그인 전 -->
          <ul id="before-login" class="navbar-nav mb-2 me-2 mb-lg-0 ms-auto">
            <li class="nav-item">
              <a href="board.html" class="nav-link">여행 정보 공유</a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                aria-current="page"
                data-bs-toggle="modal"
                data-bs-target="#loginModal"
                >로그인</a
              >
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                aria-current="page"
                data-bs-toggle="modal"
                data-bs-target="#registerModal"
                >회원가입</a
              >
            </li>
          </ul>
          <!-- 로그인 후 -->
          <ul
            id="after-login"
            class="navbar-nav mb-2 me-2 mb-lg-0 ms-auto"
            style="display: none"
          >
            <li class="nav-item">
              <a href="board.html" class="nav-link">여행 정보 공유</a>
            </li>
            <li class="nav-item" id="logout">
              <a class="nav-link" aria-current="page">로그아웃</a>
            </li>
            <li class="nav-item" id="mypage">
              <a
                class="nav-link"
                aria-current="page"
                data-bs-toggle="modal"
                data-bs-target="#infoModal"
                >회원정보수정</a
              >
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- 회원가입 Modal -->
    <div class="modal fade" id="registerModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">회원가입</h4>
          </div>
          <!-- Modal body -->
          <div class="modal-body mx-3">
            <form action="#">
              <div class="form-group">
                <label for="userId" class="col-form-label">
                  아이디
                  <input
                    type="text"
                    class="form-control validate"
                    id="joinId"
                    name="userId"
                    placeholder="Enter ID"
                    required="required"
                  />
                </label>
                <span id="showMemberIdConfirm" class="ms-2"></span>
              </div>
              <div class="form-group">
                <label for="userPw">
                  비밀번호
                  <input
                    type="password"
                    class="form-control validate"
                    name="joinPw"
                    id="joinPw"
                    placeholder="Password"
                    required="required"
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="confirmPw">
                  비밀번호 확인
                  <input
                    type="password"
                    class="form-control validate"
                    name="joinPwConfirm"
                    id="joinPwConfirm"
                    required="required"
                  />
                </label>
                <!-- <button type="button" class="btn btn-outline-success" id="btnCheckPw">확인</button> -->
                <span id="showMemberPwConfirm"></span>
              </div>
              <div class="form-group">
                <label for="userName">
                  이름
                  <input
                    type="text"
                    class="form-control validate"
                    name="joinName"
                    id="joinName"
                    required="required"
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="userEmail"
                  >이메일
                  <input
                    type="email"
                    id="joinEmail"
                    class="form-control validate"
                    required="required"
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="userPhoneNumber" class="my-2"
                  >휴대폰 번호
                  <input
                    type="number"
                    class="form-control"
                    id="joinPhone"
                    placeholder="휴대전화 번호 입력"
                    required="required"
                  />
                </label>
              </div>

              <div class="modal-footer d-flex justify-content-center">
                <button
                  type="submit"
                  id="btnJoin"
                  class="btn btn-outline-primary"
                >
                  회원가입
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- 회원 정보 수정 Modal -->
    <div class="modal fade" id="infoModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="container my-5 py-2">
            <h2>회원 정보 수정</h2>
            <!-- 회원 정보 테이블 -->
            <table class="table my-5">
              <tbody>
                <tr>
                  <td>ID</td>
                  <td id="infoId"></td>
                  <td></td>
                </tr>
                <tr>
                  <td>PW</td>
                  <td>
                    <input
                      type="password"
                      id="infoPw"
                      disabled
                      placeholder="변경 버튼 클릭 후 수정 가능"
                    />
                  </td>

                  <td>
                    <button
                      type="button"
                      class="btnbtn-outline-success"
                      id="btnUpdatePw"
                    >
                      변경
                    </button>
                  </td>
                </tr>
                <tr>
                  <td>이름</td>
                  <td id="infoName"></td>
                  <td></td>
                </tr>
                <tr>
                  <td>이메일</td>
                  <td><input type="email" id="infoEmail" disabled /></td>
                  <td>
                    <button
                      type="button"
                      class="btnbtn-outline-success"
                      id="btnUpdateEmail"
                    >
                      변경
                    </button>
                  </td>
                </tr>
                <tr>
                  <td>휴대폰번호</td>
                  <td><input type="tel" id="infoPhone" disabled /></td>
                  <td>
                    <button
                      type="button"
                      class="btnbtn-outline-success"
                      id="btnUpdatePhone"
                    >
                      변경
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="modal-footer d-flex justify-content-center">
              <button
                type="button"
                class="btn btn-outline-danger"
                id="btnDelete"
              >
                회원탈퇴
              </button>
              <button
                type="button"
                class="btn btn-outline-success"
                id="btnUpdate"
                disabled
              >
                수정
              </button>
              <button
                type="button"
                class="btn btn-outline-primary"
                id="btnClose"
              >
                취소
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 로그인 모달 -->
    <div class="modal fade" id="loginModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">로그인</h4>
          </div>
          <!-- Modal body -->
          <div class="modal-body">
            <div>
              <div class="form-group">
                <label for="userId" class="col-form-label">
                  아이디
                  <input
                    type="text"
                    class="form-control validate"
                    id="loginId"
                    placeholder="Enter ID"
                    name="userId"
                    required
                  />
                </label>
              </div>

              <div class="form-group">
                <label for="userPw" class="my-2">
                  비밀번호
                  <input
                    type="password"
                    class="form-control validate"
                    name="memberPw"
                    id="loginPw"
                    placeholder="Password"
                    required="required"
                  />
                </label>
              </div>
              <div class="modal-footer d-flex justify-content-center">
                <button id="btnLogin" class="btn btn-outline-primary">
                  로그인
                </button>
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  data-bs-toggle="modal"
                  data-bs-target="#findIdModal"
                >
                  아이디찾기
                </button>
                <button
                  type="button"
                  class="btn btn-outline-success"
                  data-bs-toggle="modal"
                  data-bs-target="#findPwModal"
                >
                  비밀번호찾기
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 아이디 찾기 모달 -->
    <div class="modal fade" id="findIdModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">아이디 찾기</h4>
          </div>
          <!-- Modal body -->
          <div class="modal-body">
            <form action="">
              <div class="form-group">
                <label for="userName">
                  이름
                  <input
                    type="text"
                    class="form-control validate"
                    name=" findIdName "
                    id="findIdName"
                    placeholder="이름"
                    required="required"
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="userEmail">
                  이메일
                  <input
                    type="email"
                    class="form-control validate"
                    name="findIdEmail"
                    id="findIdEmail"
                    placeholder="Enter email"
                    required="required"
                  />
                </label>
              </div>
              <div class="modal-footer d-flex justify-content-center">
                <button
                  type="button"
                  id="btnFindId"
                  class="btn btn-outline-success"
                >
                  아이디 찾기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- 비밀번호 찾기 모달 -->
    <div class="modal fade" id="findPwModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">비밀번호 찾기</h4>
          </div>
          <!-- Modal body -->
          <div class="modal-body">
            <form action="">
              <div class="form-group">
                <label for="userId" class="col-form-label">
                  아이디
                  <input
                    type="text"
                    class="form-control validate"
                    id="findPwId"
                    placeholder="Enter ID"
                    name="userId"
                    required
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="userName">
                  이름
                  <input
                    type="text"
                    class="form-control validate"
                    name="findPwName"
                    id="findPwName"
                    placeholder=" 이름"
                    required="required"
                  />
                </label>
              </div>
              <div class="form-group">
                <label for="userEmail">
                  이메일
                  <input
                    type="email"
                    id="findPwEmail"
                    class="form-control validate"
                    placeholder="Enter email"
                    required="required"
                  />
                </label>
              </div>
              <div class="modal-footer d-flex justify-content-center">
                <button
                  type="button"
                  id="btnFindPw"
                  class="btnbtn-outline-success"
                >
                  비밀번호 찾기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div
      class="container-fluid p-5 mt-5 text-white d-flex flex-column justify-content-center align-items-center flex-sm-row"
      id="content-main"
    >
      <div id="search" class="w-50 p-5 my-5 border">
        <h1 class="mb-4 ms-auto">어디를 여행하시나요 ?</h1>
        <div id="search-main" class="input-group input-group-lg mb-3">
          <input
            id="keyword"
            type="text"
            class="form-control"
            placeholder="지역, 여행지 이름을 검색해 !"
            aria-describedby="button-addon2"
          />
          <button
            class="btn bg-white text-black"
            type="button"
            id="button-addon2"
          >
            Search
          </button>
        </div>
      </div>
      <div class="d-flex flex-row"></div>
    </div>
    <div
      id="search-result"
      class="container-fluid d-flex flex-column align-items-center"
    >
      <div class="mx-3 mt-5 mb-3">
        <h1>여행지 주변을 탐색해보세요</h1>
      </div>
      <div class="w-75 d-flex flex-row">
        <form class="d-flex w-100 my-3" onsubmit="return false;" role="search">
          <select id="search-area" class="form-select me-2">
            <option value="0" selected>검색 할 지역 선택</option>
          </select>
          <select id="search-content-id" class="form-select me-2">
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
          <input
            id="search-keyword"
            class="form-control me-2"
            type="search"
            placeholder="검색어"
            aria-label="검색어"
          />
          <button id="btn-search" class="btn btn-success" type="button">
            Search
          </button>
        </form>
      </div>
      <div class="d-flex w-75 flex-column">
        <div id="map" class="w-100" style="height: 500px"></div>
        <div class="row mt-5">
          <table
            id="place-list"
            class="table table-striped"
            style="display: none"
          >
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
        class="d-flex flex-wrap justify-content-center py-3 my-4 border-top"
      >
        <div class="col-md-4 d-flex justify-content-center align-items-center">
          <a
            href="/"
            class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1"
          >
            <h6>© EnjoyTrip - SSAFY</h6>
          </a>
        </div>
      </footer>
    </div>

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
      const joinId = document.querySelector("#joinId");
      const joinPw = document.querySelector("#joinPw");
      const joinPwConfirm = document.querySelector("#joinPwConfirm");
      const joinName = document.querySelector("#joinName");
      const joinEmail = document.querySelector("#joinEmail");
      const joinPhone = document.querySelector("#joinPhone");

      var count = 0;
      var curUser = "";

      //  아이디 중복확인
      joinId.addEventListener("keydown", function () {
        document.querySelector("#showMemberIdConfirm").innerText = "";
      });
      joinId.addEventListener("blur", function () {
        // let users = JSON.parse(localStorage.getItem("users"));
        // let flag = true;
        const isExist = localStorage.getItem(joinId.value);

        // if(users === null){
        //   flag = false;
        // }
        // console.log(flag);
        // if(flag){
        //   for(i = 0; i < users.length; i++){
        //     if(users[i].id == joinId.value){
        //       isExist = true;
        //       break;
        //     }
        //   }
        // }
        // console.log(isExist);

        if (isExist) {
          document.getElementById("showMemberIdConfirm").innerText =
            "해당 아이디 사용 불가";
          showMemberIdConfirm.style.color = "red";
          joinId.focus();
        } else if (joinId.value.length === 0) {
          document.getElementById("showMemberIdConfirm").innerText =
            "1자리 이상이어야 합니다.";
        } else {
          document.getElementById("showMemberIdConfirm").innerText =
            "해당 아이디 사용 가능";
          showMemberIdConfirm.style.color = "blue";
          joinPw.focus();
        }
      });

      /* 비밀번호와 비밀번호 확인 매핑 이벤트 처리 */
      joinPw.addEventListener("keydown", function () {
        document.querySelector("#showMemberPwConfirm").innerText = "";
      });

      joinPwConfirm.addEventListener("keydown", function () {
        document.querySelector("#showMemberPwConfirm").innerText = "";
      });

      joinPwConfirm.addEventListener("blur", function () {
        let showMemberPwConfirm = document.getElementById(
          "showMemberPwConfirm"
        );
        if (joinPw.value.length !== 0 && joinPw.value == joinPwConfirm.value) {
          showMemberPwConfirm.innerHTML = "일치";
          showMemberPwConfirm.style.color = "blue";
          joinName.focus();
        } else {
          showMemberPwConfirm.innerHTML = "비밀번호 불일치";
          showMemberPwConfirm.style.color = "red";
          // joinPw.focus();
        }
      });

      // 공백이 있나 없나 체크
      function checkSpace(str) {
        if (str.search(/\s/) != -1) {
          return true;
        } else {
          return false;
        }
      }

      // 특수 문자가 있나 없나 체크
      function checkSpecial(str) {
        var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

        if (special_pattern.test(str) == true) {
          return true;
        } else {
          return false;
        }
      }

      // 회원가입 버튼 눌렀을 때
      document.getElementById("btnJoin").addEventListener("click", function () {
        //let localMembers = window.localStorage.getItem("members"); // 로컬스토리지에는 문자열 형태로 저장
        // user 객체에 저장 후, 문자열로 바꿔서 로컬 스트리지에 저장
        var user = {
          id: joinId.value,
          password: joinPw.value,
          name: joinName.value,
          email: joinEmail.value,
          phone: joinPhone.value,
        };

        const exists = localStorage.getItem(joinId.value);

        if (
          !exists &&
          joinId.value != "" &&
          joinPw.value != "" &&
          joinPwConfirm != "" &&
          joinName.value != "" &&
          joinEmail.value != "" &&
          joinPhone.value != ""
        ) {
          alert(`${user.name}님 회원 가입을 축하합니다!`);
          localStorage.setItem(user.id, JSON.stringify(user));
          //document.getElementById("registerModal").style.display = "none";
        } else {
          alert("회원 가입 실패");
        }

        // localStorage.setItem(`${user.id}`, JSON.stringify(user));
        // let localUser = localStorage.getItem(users);
        // if(localUser){
        //   localUser = [user];
        // }else{
        //   localUser = JSON.parse(localUser);
        //   localUser.push(user);
        // }
      });

      // 로그인
      document
        .getElementById("btnLogin")
        .addEventListener("click", function () {
          let id = document.getElementById("loginId").value;
          let pw = document.getElementById("loginPw").value;

          // const user = JSON.parse(localStorage.getItem("users"));
          let user = JSON.parse(localStorage.getItem(id));
          let index = 0;
          let isExist = false;
          console.log(user);
          // console.log(user);
          // console.log(user.id);
          // console.log(user.password)

          // let user = JSON.parse(localStorage.getItem(id));
          //console.log(user.password);
          if (user && user.id == id && user.password == pw) {
            curUser = user.id; // 현재 로그인한 사용자의 아이디 저장
            console.log(curUser);
            document.getElementById("after-login").style.display = "";
            document.getElementById("before-login").style.display = "none";
            alert(`${user.name}님 환영합니다`);
            sessionStorage.setItem("isLogin", "true");
            // document.getElementById("login-check").value = "true";

            // console.log(document.getElementById("login-check").value);
            $("#loginModal").modal("hide");
          } else {
            alert("로그인 실패");
          }
        });

      // 로그아웃
      document.getElementById("logout").addEventListener("click", function () {
        document.getElementById("after-login").style.display = "none";
        document.getElementById("before-login").style.display = "";
        sessionStorage.setItem("isLogin", "false");
        alert("로그아웃 완료");
      });

      // 아이디 찾기
      document
        .getElementById("btnFindId")
        .addEventListener("click", function () {
          let name = document.getElementById("findIdName").value;
          let email = document.getElementById("findIdEmail").value;
          console.log(name);
          console.log(email);

          let flag = true;
          for (idx = 0; idx < localStorage.length; idx++) {
            let key = localStorage.key(idx);
            let user = JSON.parse(localStorage.getItem(key));
            console.log(user);
            if (user && user.name === name && user.email === email) {
              flag = false;
              alert(`${user.name}님의 아이디는 ${user.id} 입니다.`);
              document.getElementById("loginId").focus();
              break;
            }
          }

          if (flag) {
            alert("아이디가 존재하지 않습니다.");
          }
        });

      // 비밀번호 찾기
      document
        .getElementById("btnFindPw")
        .addEventListener("click", function () {
          let id = document.getElementById("findPwId").value;
          let name = document.getElementById("findPwName").value;
          let email = document.getElementById("findPwEmail").value;

          const user = JSON.parse(localStorage.getItem(curUser));

          if (
            user &&
            user.id === id &&
            user.name === name &&
            user.email === email
          ) {
            alert(`${user.name}님의 비밀번호는 ${user.pw} 입니다.`);
          } else {
            alert("비밀번호가 존재하지 않습니다.");
          }
        });

      // 비밀번호 변경
      document
        .getElementById("btnUpdatePw")
        .addEventListener("click", function () {
          // input 활성화
          document.getElementById("infoPw").disabled = false;
          if (document.getElementById("btnUpdate").disabled) {
            document.getElementById("btnUpdate").disabled = false;
          }
        });

      // 이메일 변경
      document
        .getElementById("btnUpdateEmail")
        .addEventListener("click", function () {
          // input 활성화
          document.getElementById("infoEmail").disabled = false;
          if (document.getElementById("btnUpdate").disabled) {
            document.getElementById("btnUpdate").disabled = false;
          }
        });

      // 연락처 변경
      document
        .getElementById("btnUpdatePhone")
        .addEventListener("click", function () {
          // input 활성화
          document.getElementById("infoPhone").disabled = false;
          if (document.getElementById("btnUpdate").disabled) {
            document.getElementById("btnUpdate").disabled = false;
          }
        });

      // 회원 정보 최종 수정
      document
        .getElementById("btnUpdate")
        .addEventListener("click", function () {
          // 현재 회원의 회원정보를 로컬 스토리지에서 가져오기
          const user = JSON.parse(localStorage.getItem(curUser));

          if (!document.getElementById("infoPw").disabled) {
            user.password = document.getElementById("infoPw").value;
          }
          if (!document.getElementById("infoEmail").disabled) {
            user.email = document.getElementById("infoEmail").value;
          }
          if (!document.getElementById("infoPhone").disabled) {
            user.phone = document.getElementById("infoPhone").value;
          }

          // 로컬 스토리지에 저장
          localStorage.setItem(user.id, JSON.stringify(user));
          alert("회원정보가 수정되었습니다.");
        });

      // 마이페이지
      document.getElementById("mypage").addEventListener("click", function () {
        console.log(curUser);
        const user = JSON.parse(localStorage.getItem(curUser)); // 현재 로그인한 사용자의 아이디로 로컬 스토리지에서 가져오기
        console.log(user);
        // console.log(user.id);
        document.getElementById("infoId").innerText = user.id;
        // document.getElementById("infoPw").innerText = user.password;
        document.getElementById("infoName").innerText = user.name;
        document.getElementById("infoEmail").value = user.email;
        document.getElementById("infoPhone").value = user.phone;
      });

      // 회원탈퇴
      document
        .getElementById("btnDelete")
        .addEventListener("click", function () {
          localStorage.removeItem(curUser); // 현재 로그인한 사용자의 정보 삭제
          alert("회원탈퇴가 완료 되었습니다.");
          //document.getElementById("infoModal").style.display = "none";
          document.getElementById("after-login").style.display = "none";
          document.getElementById("before-login").style.display = "";
        });
    </script>
    <script src="js/main.js"></script>
  </body>
  <%@ include file="/include/footer.jsp" %>
</html>
