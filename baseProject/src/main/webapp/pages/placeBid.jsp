<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<pre>
	<a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;  <a
		href="/auction?id=${shownAuction.id}">Auction Description</a>  &gt; Place Bid</pre>

<h1>Place a Bid</h1>
<hr />

<c:if test="${not empty warningMessage }">
	<div class="confirmation-message">
		<img src="/img/check-mark-negative.png" />
		<p>${warningMessage}</p>
	</div>
</c:if>

<script src="/js/adDescription.js"></script>

<form:form method="post" modelAttribute="placeBidForm"
	action="/auction/placeBid" id="placeBidForm" autocomplete="off">

	<fmt:parseNumber var="formattedId" integerOnly="true" type="number"
		value="${shownAuction.id}" />

	<fieldset>
		<table class="placeAdTable">
			<tr>
				<td><label for="bid-prize">New bid</label></td>
				<td><form:input id="bid-prize" type="number" path="prize"
						placeholder="${shownAuction.prize}" step="50" />
			</tr>
			<td><form:checkbox style="display:none" id="id" name="id"
					path="id" value="${formattedId}" checked="checked" /></td>
		</table>
	</fieldset>

	<div>
		<button type="submit">Submit</button>
		<a href="/"><button type="button">Cancel</button></a>
	</div>

</form:form>

<c:import url="template/footer.jsp" />