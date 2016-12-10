<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- format the dates -->
<fmt:formatDate value="${enquiries[0].dateSent}" var="formattedDateSent"
	type="date" pattern="HH:mm, dd.MM.yyyy" />

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Manage enquiries..." />
	</c:import>
	<div class="container page-max-height">
		<div class="container-scroll container-pad">
			<div id="enquiryList">
				<table class="styledTable">
					<tr>
						<th>Sender</th>
						<th>Advertisement</th>
						<th>Date of the visit</th>
						<th>Date sent</th>
						<th>Actions</th>
					</tr>
					<c:forEach items="${enquiries}" var="enquiry">
						<fmt:formatDate value="${enquiry.dateSent}"
							var="singleFormattedDateSent" type="date"
							pattern="HH:mm, dd.MM.yyyy" />
						<fmt:formatDate value="${enquiry.visit.startTimestamp}"
							var="startTime" type="date" pattern="HH:mm" />
						<fmt:formatDate value="${enquiry.visit.endTimestamp}"
							var="endTime" type="date" pattern="HH:mm" />
						<fmt:formatDate value="${enquiry.visit.startTimestamp }" var="date" type="date" pattern= "dd.MM.yyyy" />
						<tr>
							<td><a href="/user?id=${enquiry.sender.id}">${enquiry.sender.email}</a></td>
							<c:choose>
							<c:when test="${not empty enquiry.visit.ad}">
							<td><a href="/ad?id=${enquiry.visit.ad.id }">${enquiry.visit.ad.street },
									${enquiry.visit.ad.zipcode } ${enquiry.visit.ad.city }</a></td>
							</c:when>
							<c:otherwise>
							<td><a href="/auction?id=${enquiry.visit.auction.id }">${enquiry.visit.auction.street },
									${enquiry.visit.auction.zipcode } ${enquiry.visit.auction.city }</a></td>
							</c:otherwise>
							</c:choose>
							<td>${date},&#32;${startTime}&#32;to&#32;${endTime }</td>
							<td>${singleFormattedDateSent}</td>
							<td><c:choose>
									<c:when test="${enquiry.state == 'ACCEPTED'}">
										<p>Accepted</p>
									</c:when>
									<c:when test="${enquiry.state == 'DECLINED' }">
										<p>Declined</p>
									</c:when>
									<c:otherwise>
										<button class="acceptButton" data-id="${enquiry.id}">Accept</button>
										<button class="declineButton" data-id="${enquiry.id}">Decline</button>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="enquiries" />
</c:import>
