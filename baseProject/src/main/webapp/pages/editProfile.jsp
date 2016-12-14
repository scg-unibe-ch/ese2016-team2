<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Edit your profile..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half container-pad">
				<c:choose>
					<c:when test="${loggedIn}">
						<img
							id="profile-picture"
							src="${currentUser.picture.filePath}"
							alt="${currentUser.firstName} ${currentUser.lastName}">
							<%-- <h3 class="edit-section-title">
								Drag and drop a new profile image...<br>
								<span>
									To change your profile image, just drag and drop your new image
									onto the window.
								</span>
							</h3> --%>
					</c:when>
					<c:otherwise>
						<a href="/login">Login</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="span-half">
				<div class="form form-search form-profile form-max-height">
					<form:form
						method="post"
						modelAttribute="editProfileForm"
						action="/profile/editProfile"
						id="editProfileForm"
						autocomplete="off"
						enctype="multipart/form-data">
						<div class="container-scroll">
							<input
								style="display:none"
								type="file"
								id="field-pictures"
								accept="image/*"
								multiple="multiple" />
							<form:input
								type="hidden"
								id="picture"
								path="imagePath"
								value="${currentUser.picture.filePath}" />
              					<div class="row dates">
                					<div class="tile tile-full">
                  						<label>User name</label>
               	 					</div>
                					<div class="tile tile-full">
										<form:input
											class="js-has-label"
											id="user-name"
											path="username"
											value="${currentUser.username}" />
									</div>
								</div>
									<div class="row dates">
                						<div class="tile tile-full">
                  							<label>First name</label>
                						</div>
                						<div class="tile tile-full">
											<form:input
												id="first-name"
												path="firstName"
												value="${currentUser.firstName}" />
										</div>
									</div>
									<div class="row dates">
						                <div class="tile tile-full">
						                  	<label>Last name</label>
						                </div>
						                <div class="tile tile-full">
											<form:input
												id="last-name"
												path="lastName"
												value="${currentUser.lastName}" />
										</div>
									</div>
									<div class="row dates">
                						<div class="tile tile-full">
                  							<label>Password</label>
               	 						</div>
                						<div class="tile tile-full">
											<form:input
												type="password"
												id="password"
												path="password"
												value="${currentUser.password}" />
										</div>
									</div>
									<div class="row dates">
                						<div class="tile tile-full">
                  							<label>About</label>
                						</div>
                						<div class="tile tile-full">
											<form:textarea
												id="about-me"
												path="aboutMe"
												rows="10" />
										</div>
									</div>
								</div> <%-- .container-scroll END --%>
								<button type="submit">Update</button>
					</form:form>
				</div> <%-- .form END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="editProfile" />
</c:import>
