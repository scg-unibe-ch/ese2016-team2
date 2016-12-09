<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Your schedule..." />
	</c:import>

	<div class="container page-max-height">
		<div class="container-scroll container-pad">

			<h2>Your presentations</h2>

			<div id="presentationsDiv">
			<c:choose>
				<c:when test="${empty presentations}">
					<p>You currently haven't scheduled any presentations.
				</c:when>
				<c:otherwise>
					<table class="styledTable" id="visits">
						<thead>
						<tr>
							<th>Address</th>
							<th>Date</th>
							<th>Time</th>
							<th>Visit Advertisement</th>
							<th>Visitors</th>
						</tr>
						</thead>
					<c:forEach var="presentation" items="${presentations}">
						<tr><c:choose>
							<c:when test="${not empty presentation.ad}">
							<td>${presentation.ad.street}, ${presentation.ad.zipcode} ${presentation.ad.city}</td>
							</c:when>
							<c:otherwise>
							<td>${presentation.auction.street}, ${presentation.auction.zipcode} ${presentation.auction.city}</td>
							</c:otherwise>
							</c:choose>
							<td>
								<fmt:formatDate value="${presentation.startTimestamp}" var="formattedVisitDay"
									type="date" pattern="dd.MM.yyyy" />
								${formattedVisitDay}
							</td>
							<td>
								<fmt:formatDate value="${presentation.startTimestamp}" var="formattedStartTime"
									type="date" pattern="hh.mm" />
								<fmt:formatDate value="${presentation.endTimestamp}" var="formattedEndTime"
									type="date" pattern="hh.mm" />
								${formattedStartTime} - ${formattedEndTime}
							</td>
							<td><c:choose><c:when test="${not empty presentation.ad}">
							<a href="/ad?id=${presentation.ad.id}"><button>Visit</button></a>
							</c:when><c:otherwise>
							<a href="/auction?id=${presentation.auction.id}"><button>Visit</button></a>
							</c:otherwise></c:choose></td>

							<td><a href="/profile/visitors?visit=${presentation.id}"><button>See List</button></a></td>
						</tr>
					</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			</div><br />

			<h2>Your visits</h2>

			<div id="visitsDiv">
			<c:choose>
				<c:when test="${empty visits}">
					<p>You currently haven't scheduled any visits.
				</c:when>
				<c:otherwise>
					<table class="styledTable" id="visits">
						<thead>
						<tr>
							<th>Address</th>
							<th>Date</th>
							<th>Time</th>
							<th>Visit Advertisement</th>
						</tr>
						</thead>
					<c:forEach var="visit" items="${visits}">
						<tr>
							<td>
							<c:choose><c:when test="${not empty visit.ad}">
							${visit.ad.street}, ${visit.ad.zipcode} ${visit.ad.city}</c:when>
							<c:otherwise>
							${visit.auction.street}, ${visit.auction.zipcode} ${visit.auction.city}</c:otherwise></c:choose></td>
							<td>
								<fmt:formatDate value="${visit.startTimestamp}" var="formattedVisitDay"
									type="date" pattern="dd.MM.yyyy" />
								${formattedVisitDay}
							</td>
							<td>
								<fmt:formatDate value="${visit.startTimestamp}" var="formattedStartTime"
									type="date" pattern="hh.mm" />
								<fmt:formatDate value="${visit.endTimestamp}" var="formattedEndTime"
									type="date" pattern="hh.mm" />
								${formattedStartTime} - ${formattedEndTime}
							</td>
							<td><c:choose><c:when test="${not empty visit.ad}">
							<a href="/ad?id=${visit.ad.id}"><button>Visit</button></a>
							</c:when><c:otherwise>
							<a href="/auction?id=${visit.auction.id}"><button>Visit</button></a>
							</c:otherwise></c:choose></td>
						</tr>
					</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			</div>

		</div>
	</div>

</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="schedule" />
</c:import>
