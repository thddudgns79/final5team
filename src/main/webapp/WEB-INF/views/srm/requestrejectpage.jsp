<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="ko">

<head>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/request.css" rel="stylesheet" type="text/css">
<style>
a {
	text-decoration: none;
}

.wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
}

form {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 800px;
	width: 1050px;
	position: relative;
	background-color: #fff;
	border-radius: 5px;
	border: 1px solid rgba(0, 0, 0, 0.125);
	border-radius: 0.25rem;
	-webkit-box-shadow: 1px 1px 5px rgba(126, 142, 159, 0.1);
	box-shadow: 1px 1px 5px rgba(126, 142, 159, 0.1);
	margin-bottom: 50px;
}

.section1 {
	width: 1047px;
	overflow: hidden;
	text-align: start;
	align-items: center;
	position: absolute;
	top: 0;
	left: 0;
	background-color: #f8f9fc;
	border-bottom: 1px solid #e3e6f0;
	height: 54px;
}

.section1 h4 {
	color: #5a5c69;
	font: bold;
	margin: 15px 15px;
}

.section1 .step {
	width: 1047px;
	overflow: hidden;
	text-align: start;
	justify-content: center;
}

.section1 button {
	margin: 0px 10px;
	background-color: var(--gray-dark);
	color: white;
	border: none;
}

.section1 .step span {
	display: inline-block;
	background-color: #5a5c69;
	border-color: #5a5c69;
	width: 8px;
	height: 6px;
}

.section2 .label {
	position: absolute;
	width: 100px;
	left: 8%;
	top: 15%;
	overflow: hidden;
	text-align: start;
}

.section2 h6 {
	margin-top: 3px;
	margin-bottom: 24px;
	font-size: 15px;
	font: bold;
}

.section2 .inputData {
	position: absolute;
	width: 250px;
	height: 200px;
	left: 18%;
	top: 15%;
	overflow: hidden;
	text-align: start;
}

.section2 .label2 {
	position: absolute;
	width: 110px;
	left: 45%;
	top: 15%;
	overflow: hidden;
	text-align: start;
	margin-bottom: 24px;
}

.section2 .inputData2 {
	position: absolute;
	width: 250px;
	height: 130px;
	left: 55%;
	top: 15%;
	overflow: hidden;
	text-align: start;
}

.section2 .inputData2 input {
	height: 30px;
	width: 200px;
	font-size: 13px;
}

.section2 .inputData input {
	height: 30px;
	width: 200px;
	font-size: 13px;
}

.section2 .titleLabel {
	position: absolute;
	width: 50px;
	left: 8%;
	top: 38%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
}

.section2 .titleLabel h6 {
	font: bold;
}

.section2 .titleInput {
	position: absolute;
	width: 590px;
	left: 18%;
	top: 38%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}

.section2 .titleInput input {
	width: 590px;
	height: 25px;
}

.section2 .titleBody {
	position: absolute;
	width: 80px;
	left: 8%;
	top: 43%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.section2 .titleBody_reject {
	position: absolute;
	width: 80px;
	left: 8%;
	top: 38%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}

.section2 .bodyInput {
	position: absolute;
	width: 590px;
	left: 18%;
	top: 43%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.section2 .bodyInput_reject {
	position: absolute;
	width: 590px;
	left: 18%;
	top: 38%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.section2 .bodyInput textarea {
	width: 590px;
	font-size: 15px;
	font: bold;
	height: 150px;
	resize: none;
}
.section2 .bodyInput_reject textarea {
	width: 590px;
	font-size: 15px;
	font: bold;
	height: 150px;
	resize: none;
}



.section2 .fileTitle {
	position: absolute;
	width: 80px;
	left: 8%;
	top: 63%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.section2 .fileTitle_reject {
	position: absolute;
	width: 80px;
	left: 8%;
	top: 63%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}

.section2 .fileBody {
	position: absolute;
	width: 590px;
	left: 18%;
	height: 200px;
	top: 63%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.section2 .fileBody_reject {
	position: absolute;
	width: 590px;
	left: 18%;
	height: 200px;
	top: 63%;
	overflow: hidden;
	text-align: start;
	font-size: 15px;
	font: bold;
}
.upload_name {
	position: absolute;
	left: 15%;
	z-index: 2;
	display: inline-block;
	height: 28px;
	width: 495px;
	vertical-align: middle;
	border: 1px solid #d1d3e2;
	border-radius: 5px;
	color: #999999;
}

.filebox label {
	position: absolute;
	left: -1%;
	display: inline-block;
	padding: 5px 5px;
	color: #fff;
	vertical-align: middle;
	background-color: #999999;
	border-radius: 5px;
	cursor: pointer;
	height: 20px;
	margin-left: 10px;
}

.filebox input[type="file"] {
	position: absolute;
	width: 0;
	height: 0;
	padding: 0;
	overflow: hidden;
	border: 0;
}

.exist_file {
	border: 1px solid #d1d3e2;
	position: absolute;
	top: 20%;
	width: 588px;
	height: 100px;
	border: 1px solid #d1d3e2;
	border-radius: 5px;
}
.exist_file_reject {
	border: 1px solid #d1d3e2;
	position: absolute;
	top: 2%;
	width: 588px;
	height: 100px;
	border: 1px solid #d1d3e2;
	border-radius: 5px;
}
.section2 .submit-button {
	position: absolute;
	width: 80px;
	height: 50px;
	left: 40%;
	top: 82%;
}

.section2 .return-button {
	position: absolute;
	width: 80px;
	height: 50px;
	left: 45%;
	top: 82%;
}

.section2 .item .select-group select {
	width: 200px;
	height: 35px;
	font-size: 13px;
	padding-left: 37px;
	padding-top: 0;
	padding-bottom: 0;
}

.section2 .include {
	position: absolute;
	width: 240px;
	height: 500px;
	left: 76%;
	top: 15%;
	z-index: 5;
	width: 240px;
	height: 500px;
	left: 76%;
	top: 15%;
}

[type="text"]:focus::placeholder {
	visibility: hidden;
}

textarea:focus::placeholder {
	visibility: hidden;
}

.item select, .item textarea, .item input {
	border: 1px solid lightgray;
	height: 40px;
	width: inherit;
	border-radius: 5px;
	padding: 10px;
	box-sizing: border-box;
	padding-left: 40px;
	outline: none;
	transition: 0.3s;
}

.item {
	/* border: 1px solid red; */
	width: 250px;
	margin-bottom: 10px;
	position: relative;
}

.item input {
	height: 40px;
	width: inherit;
	border-radius: 5px;
	padding: 10px;
	box-sizing: border-box;
	padding-left: 40px;
	outline: none;
	transition: 0.3s;
}

.item .fa {
	position: absolute;
	top: -4px;
	left: -5px;
	color: gray;
	font-size: 20px;
	width: 40px;
	height: 40px;
	/* border: 1px solid blue; */
	text-align: center;
	line-height: 40px;
	transition: 0.3s;
	z-index: 3;
}

.item input:focus {
	border: 1px solid #5a5c69;
	box-shadow: 0 0 5px #5a5c69;
}

.item input:focus::placeholder {
	visibility: hidden;
}

.item textarea:focus {
	border: 1px solid #5a5c69;
	box-shadow: 0 0 5px #5a5c69;
}

.item input:focus {
	box-shadow: 0 0 5px #5a5c69;
}

.item input:focus+.fa {
	color: #5a5c69;
}

.item select:focus {
	box-shadow: 0 0 5px #5a5c69;
}

.include {
	margin: 0;
	font-size: 15px;
	padding: 10px;
}

article.include div {
	margin: 0px 50px;
	box-sizing: border-box;
}

.include .circle {
	background-color: #fff;
	text-align: center;
	color: #999;
	height: 28px;
	width: 120px;
	font-size: 15px;
	border: 3px solid #e0e0e0;
	transition: 0.4s ease;
}

.include .bar {
	margin: 0px 110px;
	background-color: #fff;
	color: #999;
	height: 30px;
	width: 1px;
	align-items: center;
	justify-content: center;
	border: 3px solid #e0e0e0;
	transition: 0.4s ease;
}

.include .circle.done {
	border-color: #5a5c69;
	color: #5a5c69;
}

.include .circle.now {
	border-color: white;
	color: white;
	background-color: #5a5c69;
}

.include .bar.active {
	border-color: #5a5c69;
}
.navigation {
	margin-bottom : 15px;
	margin-left : 10px;
}


</style>
</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<%@ include file="/WEB-INF/views/common/topbar.jsp"%>
				<!-- End of Topbar -->

				<!-- 여기에 내용 담기 start -->

				<div class="navigation">
					<ul class="nav nav-tabs">
						<li class="nav-item">
							<button id="requestInfoNav" class="btn nav-link" onclick="openRequestInfo()">요청 내용</button>
						</li>
						<li class="nav-item">
							<button id="rejectInfoNav" class="btn nav-link" onclick="rejectInfoNav()">반려 사유</button>
						</li>
					</ul>
				</div>

				<div class="wrapper" id="requestInfo">
					<form method="post" class="border-left-dark" action="${pageContext.request.contextPath}/customer/request" enctype="multipart/form-data">
						<section class="section1">
							<h4>요청 내용</h4>

						</section>
						<section class="section2">
							<article class="label item">
								<h6>작성자</h6>
								<h6>전화번호</h6>
								<h6>직급</h6>
								<h6>시스템</h6>
							</article>
							<article class="inputData">
								<div class="item">
									<input type="text" class="form-control form-control-user" id="clientName" name="clientName" placeholder="${sessionScope.member.mname}" value="${sessionScope.member.mname}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="phone" name="phone" placeholder="${sessionScope.member.phone}" value="${sessionScope.member.phone}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="position" name="position" placeholder="${sessionScope.member.position}" value="${sessionScope.member.position}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<div class="select-group">
										<select class="custom-select" id="sno" name="sno">
											<option selected>시스템 선택</option>
											<c:forEach var="system" items="${systemList}">
												<option value="${system.sno}">${system.systemName}</option>
											</c:forEach>
										</select>
										<i class="fa fa-user"></i>
									</div>
								</div>
							</article>
							<article class="label2">
								<h6>소속기관</h6>
								<h6>이메일</h6>
								<h6>완료 희망 일자</h6>
							</article>
							<article class="inputData2">
								<div class="item">
									<input type="text" class="form-control form-control-user" id="organ" name="organ" placeholder="${sessionScope.member.organ}" value="${sessionScope.member.organ}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="email" name="email" placeholder="${sessionScope.member.email}" value="${sessionScope.member.email}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="date" class="form-control form-control-user" id="reqExpectDate" name="reqExpectDate" value="<fmt:formatDate value="${request.reqExpectDate}" pattern="yyyy-MM-dd" />"> <i class="fa fa-phone"></i>
								</div>
							</article>
							<article class="titleLabel">
								<h6>제목</h6>
							</article>
							<article class="titleInput">
								<div class="item">
									<input type="text" id="reqTitle" name="reqTitle" placeholder="제목" value="${request.reqTitle}">
								</div>
							</article>
							<article class="titleBody">
								<h6>내용</h6>
							</article>
							<article class="bodyInput">
								<div class="item">
									<textarea id="reqContent" cols="30" name="reqContent" placeholder="내용">${request.reqContent}</textarea>
								</div>
							</article>
							<article class="fileTitle">
								<h6>파일첨부</h6>
							</article>
							<article class="fileBody">
								<div class="file-item">
									<input class="upload_name" value="첨부파일" placeholder="첨부파일">
									<div class="filebox">
										<label for="mfile">파일찾기</label> <input multiple="multiple" type="file" id="mfile" name="mfile[]" />
									</div>
									<div class="exist_file"></div>
								</div>
							</article>
							<article class="submit-button">
								<button class="btn btn-dark btn-sm" onclick="location.href='${pageContext.request.contextPath}/customer/myrequestlist'">확인</button>
							</article>
							<article class="include">
								<%@ include file="/WEB-INF/views/srm/restatus/step_request.jsp"%>
							</article>
						</section>
					</form>
				</div>
				
				
				<!-- 반려 정보 출력 부분 -->
				
				<div class="wrapper" id="rejectInfo" style="display:none;">
					<form method="post" class="border-left-dark" action="${pageContext.request.contextPath}/customer/request" enctype="multipart/form-data">
						<section class="section1">
							<h4>반려 사유</h4>

						</section>
						<section class="section2">
							<article class="label item">
								<h6>작성자</h6>
								<h6>전화번호</h6>
								<h6>직급</h6>
								<h6>시스템</h6>
							</article>
							<article class="inputData">
								<div class="item">
									<input type="text" class="form-control form-control-user" id="clientName" name="clientName" placeholder="${sessionScope.member.mname}" value="${sessionScope.member.mname}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="phone" name="phone" placeholder="${sessionScope.member.phone}" value="${sessionScope.member.phone}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="position" name="position" placeholder="${sessionScope.member.position}" value="${sessionScope.member.position}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<div class="select-group">
										<select class="custom-select" id="sno" name="sno">
											<option selected>시스템 선택</option>
											<c:forEach var="system" items="${systemList}">
												<option value="${system.sno}">${system.systemName}</option>
											</c:forEach>
										</select>
										<i class="fa fa-user"></i>
									</div>
								</div>
							</article>
							
							<article class="label2">
								<h6>소속기관</h6>
								<h6>이메일</h6>
							</article>
							<article class="inputData2">
								<div class="item">
									<input type="text" class="form-control form-control-user" id="organ" name="organ" placeholder="${sessionScope.member.organ}" value="${sessionScope.member.organ}" readonly> <i class="fa fa-phone"></i>
								</div>
								<div class="item">
									<input type="text" class="form-control form-control-user" id="email" name="email" placeholder="${sessionScope.member.email}" value="${sessionScope.member.email}" readonly> <i class="fa fa-phone"></i>
								</div>
							</article>
							<article class="titleBody_reject">
								<h6>반려 사유</h6>
							</article>
							<article class="bodyInput_reject">
								<div class="item">
									<textarea id="reqContent" cols="30" name="reqContent" placeholder="내용"></textarea>
								</div>
							</article>
							<article class="fileTitle_reject">
								<h6>파일첨부</h6>
							</article>
							<article class="fileBody_reject">
								<div class="file-item">
									<div class="exist_file_reject"></div>
								</div>
								
							</article>
							<article class="submit-button">
								<button class="btn btn-dark btn-sm" type="submit">수정</button>
							</article>
							<article class="return-button">
								<button class="btn btn-dark btn-sm" onclick="javascript:history.go(-1); return false;">취소</button>
							</article>
							<article class="include">
								<%@ include file="/WEB-INF/views/srm/restatus/step_request.jsp"%>
							</article>
						</section>
					</form>
				
				
				
				
				</div>
			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<%@ include file="/WEB-INF/views/common/footer.jsp"%>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i class="fas fa-angle-up"></i>
	</a>

	<script>
	
		$("#mfile").on('change', function() {

			var fileName = $("#mfile").val();
			$(".upload_name").val(fileName);

		});
		function openRequestInfo(){
			$('#requestInfoNav').addClass("active");
			$('#rejectInfoNav').removeClass("active");
			$('#requestInfo').show();
			$('#rejectInfo').hide();
		}
		
		function rejectInfoNav(){
			$('#rejectInfoNav').addClass("active");
			$('#requestInfoNav').removeClass("active");
			$('#requestInfo').hide();
			$('#rejectInfo').show();
			
		}
		
		
		
		
	</script>

</body>

</html>