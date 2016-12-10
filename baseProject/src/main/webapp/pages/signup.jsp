<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Sign up..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half">
				<div class="row">
			    	<div class="tile tile-full">
			      		<div class="form form-search form-signup form-max-height">
							<form:form
								id="signupForm"
								method="post"
								modelAttribute="signupForm"
								action="signup">
								<div class="container-scroll">
									<form:input
										path="firstName"
										id="field-firstName"
										placeholder="Firstname" />
									<form:errors path="firstName" cssClass="validationErrorText" />
									<form:input
										path="lastName"
										id="field-lastName"
										placeholder="Lastname" />
									<form:errors path="lastName" cssClass="validationErrorText" />
									<form:input
										path="password"
										id="field-password"
										type="password"
										placeholder="Password" />
									<div class="validator error-field-password">
										<span class="validationErrorText"></span>
									</div>
									<form:errors path="password" cssClass="validationErrorText" />
									<form:input
										path="email"
										id="field-email"
										placeholder="Email" />
									<div class="validator error-field-email">
										<span class="validationErrorText"></span>
									</div>
									<form:errors path="email" cssClass="validationErrorText" />
									<div class="row multi-select">
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full action action-tile">
													<form:select path="gender">
														<form:option value="Choose gender" disabled="true" selected="selected" />
														<form:option value="FEMALE" label="Female" />
														<form:option value="MALE" label="Male" />
													</form:select>
													<form:errors htmlEscape="false" path="gender" cssClass="validationErrorText" />
												</div>
											</div>
										</div>
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full action action-tile">
													<form:select path="account">
														<form:option value="Choose account type" disabled="true" selected="selected" />
														<form:option value="Normal" label="Normal" />
														<form:option value="Premium" label="Premium" />
													</form:select>
													<form:errors path="account" cssClass="validationErrorText" />
												</div>
											</div>
										</div>
									</div>
								</div> <%-- .container-scroll END --%>
								<button type="submit">Sign up</button>
							</form:form>
						</div> <%-- .form END --%>
					</div> <%-- .tile.tile-full END --%>
				</div> <%-- .row END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="signup" />
</c:import>
