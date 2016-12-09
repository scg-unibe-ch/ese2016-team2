<%@ page import="ch.unibe.ese.team1.model.Ad"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:choose>
		<c:when test="${principalID != null}">
			<c:if test="${principalID eq user.id}">
				<c:import url="template/~top_bar.jsp">
					<c:param name="instr" value="Your public profile..." />
				</c:import>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:import url="template/~top_bar.jsp">
				<c:param name="instr" value="${user.firstName} ${user.lastName}" />
			</c:import>
		</c:otherwise>
	</c:choose>

	<div class="container">
		<div class="row">
			<div class="span-half page-max-height">
				<div class="container-scroll">
					<div class="row container-pad">

						<div class="span-half">
							<c:choose>
								<c:when test="${user.picture.filePath != null}">
									<img class="only-child" src="${user.picture.filePath}">
								</c:when>
								<c:otherwise>
									<img class="only-child" src="/img/avatar.png">
								</c:otherwise>
							</c:choose>
						</div>

						<div class="span-half">
							<c:choose>
								<c:when test="${user.account eq 'Premium'}">
									<img src='/img/crone.png' width ="45" height="35"/>
								</c:when>
							</c:choose>
							<h5>Username</h5>${user.email}<p>
							<h5>Name</h5>${user.firstName}
							${user.lastName}


						</div>
					</div>


					<div class="row">

						<div class="span-full container-pad">
							<h5>About me</h5>
							${user.aboutMe}
						</div>

						<div class="span-full container-pad">
							<c:choose>
								<c:when test="${principalID != null}">
									<c:choose>
										<c:when test="${principalID eq user.id}">
											<a class="button" href="/profile/editProfile">Edit Profile</a>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>

								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

				</div> <%-- .container-scroll END --%>
			</div> <%-- .span-half END --%>

			<div class="span-half form-minus-3-height">
				<div class="row">
					<div class="tile tile-full">
						<c:choose>
							<c:when test="${loggedIn}">

								<h3 class="section-title">Leave a message...</h3>
								<form class="form form-message">
									<div class="container-scroll">

										<input type="text" id="msgSubject" placeholder="Subject *" />
										<div class="validator error-msgSubject">
											<span class="validationErrorText"></span>
										</div>

										<textarea id="msgTextarea" placeholder="Message *" rows="8"></textarea>
										<div class="validator error-msgTextarea">
											<span class="validationErrorText"></span>
										</div>

									</div> <%-- .container-scroll END --%>

									<div class="row">
										<div class="tile tile-half">
											<button class="submit-state-before" type="button" id="messageSend">
												<span class="submit-before">Send</span>
												<span class="submit-after">Delivered</span>
												<span class="fa fa-circle-o-notch fa-spin fa-fw submitting"></span>
												<span class="sr-only">Sending...</span>
											</button>
										</div>
										<div class="tile tile-half">
											<button type="reset" id="messageCancel">Cancel</button>
										</div>
									</div>
								</form>

							</c:when>
							<c:otherwise>
								<div class="container-pad">
									<p>Please log in to contact this user.</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>

</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="user" />
</c:import>
