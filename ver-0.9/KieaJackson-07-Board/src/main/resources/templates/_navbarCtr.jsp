<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

	<!-- ////////////////////////////////////////////////////////////////////////////////////////////// -->
	<!-- navigation -->
	<!-- ////////////////////////////////////////////////////////////////////////////////////////////// -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${staticPATH}/">ACSS</a>
			</div>
			<div class="navbar-collapse collapse" id="bs-example-navbar-collapse-1">
				<!-- navbar menu -->
				<ul class="nav navbar-nav navbar-left">
					<li><a data-target="#modalSelfInfo" data-toggle="modal" href="#" onclick="fn_console('toggle event after click event.....');">내 정보</a></li>

					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">가게계좌<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/account/createAcntListPage.do','가게계좌 생성');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;가게계좌 생성</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/account/selectAcntListPage.do','가게계좌 목록');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;가게계좌 목록</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/account/selectAcntInOutListPage.do','가게계좌 입출금내역');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;가게계좌 입출금내역</a></li>
						</ul>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">쿠폰발행<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/coupon/createCampFormPage.do','캠페인 생성');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;캠페인 생성</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/coupon/executeCampListPage.do','캠페인 실행');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;캠페인 실행</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/coupon/resultCampListPage.do','캠페인 결과');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;캠페인 결과</a></li>
						</ul>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">쿠폰정산<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/payment/paymentCpnListPage.do','정산요청처리');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;정산요청처리</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/payment/completeCalcListPage.do','정산완료(6)목록');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;정산완료(6)목록</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/payment/notUsedCpnListPage.do','고객미사용쿠폰(3)목록정산(7)');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;고객미사용쿠폰(3)목록정산(7)</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/payment/settlementDiscardListPage.do','쿠폰결산(8)/폐기(9)');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;쿠폰결산(8)/폐기(9)</a></li>
						</ul>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">센터관리<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/manage/manageForTestPage.do','테스트 관리');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;테스트 관리</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/manage/manageForTestPage.do','아라쿠폰 운영관리');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;아라쿠폰 운영관리</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/manage/selectStrListPage.do','가게목록 관리');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;가게목록 관리</a></li>
							<li><a href="javascript:fn_loadPostPage('#tempForm', '${staticPATH}/ctr2/manage/selectUsrListPage.do','고객목록 관리');"><span class="glyphicon glyphicon-list-all"></span>&nbsp;고객목록 관리</a></li>
						</ul>
					</li>
					<li class="hide"><a href="/sample05/">통계</a></li>
				</ul>

				<!-- login menu -->
				<ul class="nav navbar-nav navbar-right hide">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${staticPATH}/ctr/login/login.do"><span class="glyphicon glyphicon-log-in"></span>&nbsp;로그인</a></li>
							<li><a href="${staticPATH}/ctr/login/register.do"><span class="glyphicon glyphicon-edit"></span>&nbsp;회원등록</a></li>
							<li><a href="${staticPATH}/ctr/login/logout.do"><span class="glyphicon glyphicon-log-out"></span>&nbsp;로그아웃</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- gap -->
	<div style="height:70px;"></div>
