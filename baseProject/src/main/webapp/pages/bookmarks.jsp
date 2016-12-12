<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Your bookmarks..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half list-max-height">
				<h3 class="list-title">Bookmarked Ads</h3>
				<div class="container-scroll">
					<c:choose>
						<c:when test="${empty bookmarkedAdvertisements}">
							<p class="container-pad">You have not bookmarked any ads yet.</p>
						</c:when>
						<c:otherwise>
							<ul class="resultsDiv resultsDiv-small">
								<c:forEach var="ad" items="${bookmarkedAdvertisements}">
									<li
										class="resultAd"
										data-price="${ad.prize}"
										data-moveIn="${ad.moveInDate}"
										data-age="${ad.moveInDate}">
										<div class="row">
											<div class="tile tile-third">
												<div class="list-image">
													<a
														class="list-image-link"
														href="<c:url value='/ad?id=${ad.id}' />"
														style="background-image: url(${ad.pictures[0].filePath})">
													</a>
												</div>
											</div>
											<div class="tile tile-two-thirds">
												<div class="resultAd-text">
													<h2>
														<a class="link" href="<c:url value='/ad?id=${ad.id}' />">
															${ad.title }
														</a>
													</h2>
													<h3>CHF ${ad.prize}</h3>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="tile tile-full">
												<div class="resultAd-text resultAd-details">
													<p>${ad.street},${ad.zipcode} ${ad.city}</p>
													<p>${ad.roomType}</p>
													<fmt:formatDate value="${ad.moveInDate}"
														var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />
													<p>Move-in date: ${formattedMoveInDate }</p>
												</div>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
						</c:otherwise>
					</c:choose>
				</div> <%-- .container-scroll END --%>
			</div> <%-- .span-third END --%>
			<div class="span-half list-max-height">
				<h3 class="list-title">Bookmarked Auctions</h3>
				<div class="container-scroll">
					<c:choose>
						<c:when test="${empty bookmarkedAuctions}">
							<p class="container-pad">You have not bookmarked any auctions yet.</p>
						</c:when>
						<c:otherwise>
							<ul class="resultsDiv resultsDiv-small">
								<c:forEach var="ad" items="${bookmarkedAuctions}">
									<li
										class="resultAd"
										data-price="${ad.prize}"
										data-moveIn="${ad.moveInDate}"
										data-age="${ad.moveInDate}">
										<div class="row">
											<div class="tile tile-third">
												<div class="list-image">
													<a
														class="list-image-link"
														href="<c:url value='/ad?id=${ad.id}' />"
														style="background-image: url(${ad.pictures[0].filePath})">
													</a>
												</div>
											</div>
											<div class="tile tile-two-thirds">
												<div class="resultAd-text">
													<h2>
														<a class="link" href="<c:url value='/auction?id=${ad.id}' />">
															${ad.title }
														</a>
													</h2>
													<h3>CHF ${ad.prize}</h3>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="tile tile-full">
												<div class="resultAd-text resultAd-details">
													<p>${ad.street},${ad.zipcode} ${ad.city}</p>
													<p>${ad.roomType}</p>
													<fmt:formatDate value="${ad.moveInDate}"
														var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />
													<p>Move-in date: ${formattedMoveInDate }</p>
													<p>Auction end-date: ${ad.endTime}</p>
												</div>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
						</c:otherwise>
					</c:choose>
				</div> <%-- .container-scroll END --%>
			</div> <%-- .span-third END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="myRooms" />
</c:import>
