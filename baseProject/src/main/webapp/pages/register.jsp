<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<c:import url="template/header.jsp" />

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<pre><a href="/">Home</a>   &gt;   Register as Premium User</pre>
<script>
	$(document).ready(function() {
		$("#city").autocomplete({
			minLength : 2
		});
		$("#city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});
	});
</script>
<c:choose>
	<c:when test="${currentUser.account eq 'Normal' || currentUser.account eq NULL}">
		<h1>Registration as Premium User</h1>
		<form:form method="post" modelAttribute="registerForm" action="/profile/registerProfile"
			id="registerForm" autocomplete="off">
			<fieldset>
				<legend>Enter additional Information</legend>
				<p>We need additional information to complete your registration as premium user.</p>
				<table>
					<tr>
						<td class="registerDescription"><label for="field-street">Street:</label></td>
						<td><form:input  type="text" name="street" path="street" id="field-street" />
						<form:errors path="street" cssClass="validationErrorText" /></td>						
					</tr>
					<tr>
						<td class="registerDescription"><label for="city">City / zip code:</label></td>
						<td><form:input type="text" name="city" id="city" path="city" placeholder="e.g. Bern" tabindex="3" />
						<form:errors path="city" cssClass="validationErrorText" /></td>
					</tr>
					<tr>
						<td><form:input style="display:none" id="user-name" path="username" value="${currentUser.getUsername()}"/>
						<form:errors path="username" cssClass="validationErrorText" /></td>
					</tr>
					<tr>
						<td><form:checkbox style="display:none" name="account" id="account" path="account" value="PREMIUM"/></td>
					</tr>
				</table>
				<br />
				<button type="submit">Register as Premium User</button>
			</fieldset>
		</form:form>
	</c:when>
	<c:otherwise>
		<h1>You are already a Premium User!</h1>
		<hr class="slim">
	</c:otherwise>
</c:choose>
<c:import url="template/footer.jsp" />
