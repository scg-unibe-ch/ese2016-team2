<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<script>
	$(document).ready(function() {
	});

</script>


<pre><a href="/">Home</a>   &gt;   My Rooms</pre>

<c:choose>
	<c:when test="${empty ownAds}">
		<h1>My Advertisements</h1>
		<p>You have not advertised anything yet.</p>
		<br /><br />
	</c:when>
	<c:otherwise>

		<div id="resultsDiv" class="resultsDiv">
		<h1>My Advertisements</h1>
			<c:forEach var="ad" items="${ownAds}">
				<div class="resultAd" data-price="${ad.prize}"
								data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}">
					<div class="resultLeft">
						<a href="<c:url value='/ad?id=${ad.id}' />"><img
							src="${ad.pictures[0].filePath}" /></a>
						<h2>
							<a href="<c:url value='/ad?id=${ad.id}' />">${ad.title }</a>
						</h2>
						<p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
						<br />
						<p>
							<i>${ad.roomType}</i>
						</p>
					</div>
					<div class="resultRight">
						<h2>CHF ${ad.prize}</h2>
						<br /> <br />
						<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
							type="date" pattern="dd.MM.yyyy" />
						<p>Move-in date: ${formattedMoveInDate}</p>
					</div>
				</div>
			</c:forEach>
			<br /> <br />
		</div>
	</c:otherwise>
</c:choose>

<hr class="clearBoth">

<c:choose>

	<c:when test="${empty ownAuctions}">
		<h1>My Auctions</h1>
		<p>You have not advertised anything yet.</p>
		<br /><br />
	</c:when>
	<c:otherwise>
		<div id="resultsDiv" class="resultsDiv">
		<h1>My Auctions</h1>
			<c:forEach var="auction" items="${ownAuctions}">
				<div class="resultAuction" data-price="${auction.prize}"
								data-moveIn="${auction.moveInDate}" data-age="${auction.moveInDate}">
					<div class="resultLeft">
						<!-- <a href="<c:url value='/ad?id=${ad.id}' />"><img src="${ad.pictures[0].filePath}" /></a> -->
						<h2>
							<a href="<c:url value='/auction?id=${auction.id}' />">${auction.title}</a>
						</h2>
						<p>${auction.street}, ${auction.zipcode} ${auction.city}</p>
						<br />
						<p>
							<i>${auction.roomType}</i>
						</p>
					</div>
					<div class="resultRight">
						<h2>CHF ${auction.prize}</h2>
						<fmt:formatDate value="${auction.moveInDate}" var="formattedMoveInDate"
							type="date" pattern="dd.MM.yyyy" />
						<p>Move-in date: ${formattedMoveInDate}</p>
						<fmt:formatDate value="${auction.endTime}" var="formattedEndTime" 
							type="date" pattern="dd.MM.yyyy" />
						<p>Auction end-date: ${formattedEndTime}</p>
					</div>
				</div>
			</c:forEach>
			<br /> <br />
		</div>
	</c:otherwise>

</c:choose>

<hr class="clearBoth">

<c:choose>
	<c:when test="${empty bookmarkedAdvertisements}">
		<h1>My Bookmarks</h1>
		<p>You have not bookmarked anything yet.</p><br /><br />
	</c:when>
	<c:otherwise>

		<div id="resultsDiv" class="resultsDiv">
		<h1>My Bookmarks</h1>
			<c:forEach var="ad" items="${bookmarkedAdvertisements}">
				<div class="resultAd" data-price="${ad.prize}"
								data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}">
					<div class="resultLeft">
						<a href="<c:url value='/ad?id=${ad.id}' />"><img
							src="${ad.pictures[0].filePath}" /></a>
						<h2>
							<a href="<c:url value='/ad?id=${ad.id}' />">${ad.title }</a>
						</h2>
						<p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
						<br />
						<p>
							<i>${ad.roomType}</i>
						</p>
					</div>
					<div class="resultRight">
						<h2>CHF ${ad.prize}</h2>
						<br /> <br />
						<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
							type="date" pattern="dd.MM.yyyy" />
						<p>Move-in date: ${formattedMoveInDate}</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
	
</c:choose>


<c:import url="template/footer.jsp" />
