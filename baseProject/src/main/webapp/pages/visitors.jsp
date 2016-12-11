<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Your visitors..." />
	</c:import>

	<div class="container page-max-height">
		<div class="container-scroll container-pad">

			<p>Information about the property:
			<c:choose><c:when test="${not empty ad}">
				<a href="/ad?id=${ad.id }">${ad.street }, ${ad.zipcode } ${ad.city }</a>
				</c:when><c:otherwise>
				<a href="/auction?id=${auction.id }">${auction.street }, ${auction.zipcode } ${auction.city }</a>
				</c:otherwise>
			</c:choose></p>

			<div id="visitorsDiv">
			<c:choose>
				<c:when test="${empty visitors}">
					<p>This property doesn't have any scheduled visitors at the moment.
				</c:when>
				<c:otherwise>
					<table class="styledTable" id="visitors">
						<thead>
						<tr>
							<th>Name</th>
							<th>Username</th>
							<th>Profile</th>
							<th>Rating</th>
						</tr>
						</thead>
					<c:forEach var="visitor" items="${visitors}">
						<tr>
							<td>${visitor.firstName} ${visitor.lastName }</td>
							<td>${visitor.username}</td>
							<td><a href="/user?id=${visitor.id}"><button>Visit</button></a></td>
							<td>
							<div class="rating" id="${visitor.id}">
								<%-- <script>ratingFor(${visitor.id})</script> --%>
							</div>
							</td>
						</tr>
					</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			</div>

		</div> <%-- .container-scroll END --%>
	</div> <%-- .container END --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="visitors" />
</c:import>
