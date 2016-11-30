<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<%-- check if user is logged in --%>
<security:authorize var="loggedIn" url="/profile" />


<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Place a bid..." />
	</c:import>

		<div class="container">
			<div class="row">

				<div class="span-half">
					<div class="form form-search form-place form-max-height">

						<form:form
							method="post"
							modelAttribute="placeBidForm"
							action="/auction/placeBid"
							id="placeBidForm"
							autocomplete="off">

							<div class="container-scroll">

								<fmt:parseNumber var="formattedId" integerOnly="true" type="number"
									value="${shownAuction.id}" />

								<c:if test="${loggedIn}">
									<form:input
										id="bid-prize"
										type="number"
										path="prize"
										placeholder="${shownAuction.prize}" step="50" />

									<form:checkbox
										style="display:none"
										id="id" name="id"
										path="id"
										value="${formattedId}"
										checked="checked" />
								</c:if>

								<div class="row">
									<div class="tile tile-full">
											<c:choose>
												<c:when test="${empty shownAuction.bidderName}">
													<div class="row">
														<div class="tile tile-half">
															<p class="pad">Minimal bid prize:</p>
														</div>
														<div class="tile-tile-half">
															<p class="pad">${shownAuction.prize}&#32;CHF</p>
														</div>
													</div>
													<c:if test="${loggedIn}">
														<div class="row">
															<div class="tile tile-full action action-tile">
																<a href="/login">
																	Login to place new bid
																</a>
															</div>
														</div>
													</c:if>

												</c:when>
												<c:otherwise>
													<div class="row">
														<div class="tile tile-half">
															<p class="pad">Highest bid:</p>
														</div>
														<div class="tile-tile-half">
															<p class="pad">${shownAuction.prize}&#32;CHF</p>
														</div>
													</div>
													<c:choose>
														<c:when test="${loggedIn}">
															<c:choose>
																<c:when test="${loggedInUserEmail != shownAuction.user.username}">
																</c:when>
																<c:otherwise>
																By: ${shownAuction.bidderName}
															</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div class="row">
																<div class="tile tile-full action action-tile">
																	<a href="/login">
																		Login to place new bid
																	</a>
																</div>
															</div>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</table>
									</div>
								</div>


								<c:if test="${not empty warningMessage }">
									<div class="row">
										<div class="tile tile-full">
											<p class="validationErrorText">${warningMessage}</p>
										</div>
									</div>
								</c:if>

							</div> <%-- .container-scroll END --%>


							<c:if test="${loggedIn}">
								<div class="row">
									<div class="tile tile-half">
										<button type="submit">Bid</button>
									</div>
									<div class="tile tile-half">
										<a href="/auction?id=${shownAuction.id}"><button type="button">Cancel</button></a>
									</div>
								</div>
							</c:if>

						</form:form>

					</div> <%-- .form END --%>
				</div> <%-- .span-half END --%>

				<div class="span-half page-max-height">
					<div class="container-scroll container-pad">
						<div class="row">
							<div class="tile tile-full">
								<h2>
									<%--
									@Jerome
									TODO: Has an ad a premium property??
									<c:if test="">
										<span class="fa fa-gavel" aria-hidden="true"></span>
									</c:if> --%>
									${shownAuction.title}
								</h2>
							</div>
						</div>
						<div class="row">
							<div class="tile tile-full">
								<img style="margin-bottom: 16px;" src="${shownAuction.pictures[0].filePath}" alt="${picture.name}">
							</div>
						</div>
						<div class="row">
							<div class="tile tile-full">
								<h3>Description</h3>
								<p>${shownAuction.roomDescription}</p>
							</div>
						</div>

						<div style="margin-bottom: 73px" class="row">
							<div class="tile tile-full">
								<h3>Preferences</h3>
								<p>${shownAuction.preferences}</p>
							</div>
						</div>
					</div>
				</div> <%-- .span-half END --%>

		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

</main>

<%-- <script src="/js/adDescription.js"></script> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="placeBid" />
</c:import>
