<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main"> <c:import url="template/~top_bar.jsp">
	<c:param name="instr" value="My rooms..." />
</c:import>

<div class="container">
	<div class="row">
		<div class="span-half list-max-height">
			<h3 class="list-title">Ads</h3>
			<div class="container-scroll">
				<c:choose>
					<c:when test="${empty ownAds}">
						<p class="container-pad">You have not advertised anything yet.</p>
					</c:when>
					<c:otherwise>
						<ul class="resultsDiv resultsDiv-small">
							<c:forEach var="ad" items="${ownAds}">
								<li class="resultAd" data-price="${ad.prize}"
									data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}">
									<div class="row">
										<div class="tile tile-third">
											<div class="list-image">
												<a class="list-image-link"
													href="<c:url value='/ad?id=${ad.id}' />"
													style="background-image: url(${ad.pictures[0].filePath})">
												</a>
											</div>
										</div>
										<div class="tile tile-two-thirds">
											<div class="resultAd-text resultAd-title">
												<h2>
													<a class="link" href="<c:url value='/ad?id=${ad.id}' />">
														${ad.title } </a>
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
												<div class="row">
													<div class="tile tile-three-quarter">
														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />
														<p>Move-in date: ${formattedMoveInDate }</p>
													</div>
													<div class="tile tile-quarter">
														<span
															title="Delete this ad. You will need to confirm your action."
															class="list-delete-link fa fa-times fa-2x base-color-opposite">
														</span>
													</div>
													<div class="tile tile-full">
														<div class="row deletion-confirm">
															<div class="tile tile-half action action-tile">
																<a href="<c:url value='/deleteAd?id=${ad.id}' />"
																	class="action-confirm"> Delete this ad </a>
															</div>
															<div class="tile tile-half action action-tile">
																<button class="action-cancel">Cancel</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
			</div><%-- .container-scroll END --%>
		</div><%-- .span-third END --%>

		<div class="span-half list-max-height">
			<h3 class="list-title">Auctions</h3>
			<div class="container-scroll">
				<c:choose>
					<c:when test="${empty ownAuctions}">
						<p class="container-pad">You have no auctions set yet.</p>
					</c:when>
					<c:otherwise>
						<ul class="resultsDiv resultsDiv-small">
							<c:forEach var="ad" items="${ownAuctions}">
								<li class="resultAd" data-price="${ad.prize}"
									data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}">
									<div class="row">
										<div class="tile tile-third">
											<div class="list-image">
												<a class="list-image-link"
													href="<c:url value='/auction?id=${ad.id}' />"
													style="background-image: url(${ad.pictures[0].filePath})">
												</a>
											</div>
										</div>
										<div class="tile tile-two-thirds">
											<div class="resultAd-text">
												<h2>
													<a class="link"
														href="<c:url value='/auction?id=${ad.id}' />">
														${ad.title } </a>
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
												<div class="row">
													<div class="tile tile-three-quarter">
														<fmt:formatDate value="${ad.moveInDate}"
															var="formattedMoveInDate" type="date"
															pattern="dd.MM.yyyy" />
														<p>Move-in date: ${formattedMoveInDate }</p>
														<p>Auction end-date: ${ad.endTime}</p>
													</div>
													<c:if test="${ad.auctionEnded}">
														<div class="tile tile-quarter">
															<span
																title="Delete this auction. You will need to confirm your action."
																class="list-delete-link fa fa-times fa-2x base-color-opposite">
															</span>
														</div>
														<div class="tile tile-full">
															<div class="row deletion-confirm">
																<div class="tile tile-half action action-tile">
																	<a href="<c:url value='/deleteAuction?id=${ad.id}' />"
																		class="action-confirm"> Delete this auction </a>
																</div>
																<div class="tile tile-half action action-tile">
																	<button class="action-cancel">Cancel</button>
																</div>
															</div>
														</div>
													</c:if>
												</div>
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

<%-- .container END --%> <%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="myRooms" />
</c:import>
