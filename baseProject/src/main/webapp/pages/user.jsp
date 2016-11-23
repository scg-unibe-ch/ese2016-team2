<%@page import="ch.unibe.ese.team1.model.Ad"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<c:import url="template/~top.jsp" />
<c:import url="template/~header.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="My public profile..." />
	</c:import>

	<div class="container container-pad">
		<div class="row">
			<div class="span-half">

				<c:choose>
					<c:when test="${user.picture.filePath != null}">
						<img src="${user.picture.filePath}">
					</c:when>
					<c:otherwise>
						<img src="/img/avatar.png">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${user.account eq 'Premium'}">
						<img src='/img/crone.png' width ="45" height="35"/>
					</c:when>
				</c:choose>
				<p>
				<h2>Username</h2>${user.email}<p>
				<h2>Name</h2>${user.firstName}
				${user.lastName}
				<p>
				<hr class="slim">
				<form>
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
							<p>Please log in to contact this person.</p>
						</c:otherwise>
					</c:choose>
				</form>

			</div> <%-- .span-half END --%>

			<div class="span-half">
				<h2>About me</h2>${user.aboutMe}



				<div class="row">
					<div class="tile tile-full">
						<form class="form form-message">
							<input type="text" id="msgSubject" placeholder="Subject *" />
							<textarea id="msgTextarea" placeholder="Message"></textarea>

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
