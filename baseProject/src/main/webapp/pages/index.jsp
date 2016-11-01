<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to FlatFindr</title>
</head>
<body>

<pre>Home</pre>

<h1>Welcome to FlatFindr!</h1>

<c:choose>
	<c:when test="${empty newestAds}">
		<h2>No ads placed yet</h2>
	</c:when>
	<c:otherwise>
		<div id="resultsDiv" class="resultsDiv">	
			<h2>Our newest ads:</h2>		
			<c:forEach var="advertisement" items="${newestAds}">
				<div class="resultAd">
				<c:choose>
					<c:when test="${advertisement.auction}">
						<div class="resultLeft">
								<a href="<c:url value='/auction?id=${advertisement.id}' />"><img
								src="${advertisement.pictures[0].filePath}" /></a>
							<h2>
								<a class="link" href="<c:url value='/auction?id=${advertisement.id}' />">${advertisement.title}</a>
							</h2>
							<p>${advertisement.street}, ${advertisement.zipcode} ${advertisement.city}</p>
							<br />
							<p>
								<i>${advertisement.roomType}</i>
							</p>
						</div>
						<div class="resultRight">
							<h2>CHF ${advertisement.prize}</h2>

							<fmt:formatDate value="${advertisement.moveInDate}" var="formattedMoveInDate"
								type="date" pattern="dd.MM.yyyy" />

							<p>Move-in date: ${formattedMoveInDate }</p>

							<p>Auction end-date: ${advertisement.endTime}</p>
						</div>
					</c:when>
					<c:otherwise>
						<div class="resultLeft">
								<a href="<c:url value='/ad?id=${advertisement.id}' />"><img
								src="${advertisement.pictures[0].filePath}" /></a>
							<h2>
								<a class="link" href="<c:url value='/ad?id=${advertisement.id}' />">${advertisement.title}</a>
							</h2>
							<p>${advertisement.street}, ${advertisement.zipcode} ${advertisement.city}</p>
							<br />
							<p>
								<i>${advertisement.roomType}</i>
							</p>
						</div>
						<div class="resultRight">
							<h2>CHF ${advertisement.prize}</h2>
							<br /> <br />	

							<fmt:formatDate value="${advertisement.moveInDate}" var="formattedMoveInDate"
								type="date" pattern="dd.MM.yyyy" />

							<p>Move-in date: ${formattedMoveInDate }</p>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>

<c:import url="template/footer.jsp" /><br />