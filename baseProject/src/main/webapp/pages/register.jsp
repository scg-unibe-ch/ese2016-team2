<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Advance to Premium..." />
	</c:import>

	<div class="container">

		<div class="row">

			<div class="span-half">
				<div class="form form-search form-filter form-max-height">

					<c:choose>
						<c:when test="${currentUser.account eq 'Normal' || currentUser.account eq NULL}">
							<form:form
								method="post"
								modelAttribute="registerForm"
								action="/profile/registerProfile"
								id="registerForm"
								autocomplete="off">

								<div class="container-scroll">

									<form:input
										type="text"
										name="street"
										path="street"
										placeholder="Street"
										tabindex="1"
										id="field-street" />
									<form:errors path="street" cssClass="validationErrorText" /></td>

									<form:input
										type="text"
										name="city"
										id="city"
										path="city"
										tabindex="2"
										placeholder="City / Zip" />
									<form:errors path="city" cssClass="validationErrorText" /></td>

									<form:input style="display:none" id="user-name" path="username" value="${currentUser.getUsername()}"/>
									<form:errors path="username" cssClass="validationErrorText" /></td>

									<form:checkbox style="display:none" name="account" id="account" path="account" value="PREMIUM"/></td>


								</div>

								<button type="submit">Advance</button>

							</form:form>
						</c:when>
						<c:otherwise>
							<h3>You are already a Premium User!</h3>
						</c:otherwise>
					</c:choose>

				</div> <%-- .form END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="register" />
</c:import>
