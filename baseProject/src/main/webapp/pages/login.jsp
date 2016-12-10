<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Sign in..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half">
				<div class="row">
			    	<div class="tile tile-full">
			      		<div class="form form-search form-login form-max-height">
							<c:choose>
								<c:when test="${loggedIn}">
									<p>You are already logged in!</p>
								</c:when>
								<c:otherwise>
									<form
										id="login-form"
										method="post"
										action="/j_spring_security_check">
										<div class="container-scroll">
											<input name="j_username" id="field-email" placeholder="Email" />
											<input name="j_password" id="field-password" type="password" placeholder="Password" />
											<c:if test="${!empty param.error}">
												<p class="validationErrorText">
													Incorrect email or password. Please retry using correct email
													and password.
												</p>
											</c:if>
										</div>
										<button type="submit">Sign in</button>
									</form>
								</c:otherwise>
							</c:choose>
			      		</div> <%-- .form END --%>
			    	</div> <%-- .tile.tile-full END --%>
			  	</div> <%-- .row END --%>
			</div> <%-- .span-half END --%>
			<div class="span-half">
				<div class="container-pad">
					<h3>Test users</h3>
					<ul>
						<li>Email: <i>ese@unibe.ch</i>, password: <i>ese</i></li>
						<li>Email: <i>jane@doe.com</i>, password: <i>password</i></li>
						<li>Email: <i>user@bern.com</i>, password: <i>password</i></li>
						<li>Email: <i>oprah@winfrey.com</i>, password: <i>password</i></li>
					</ul>
					<br>
					<p>
						Or <a class="link" href="<c:url value="/signup" />">sign up</a> as a new user.
					</p>
				</div>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="login" />
</c:import>
