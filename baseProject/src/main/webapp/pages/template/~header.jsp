<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<%-- check if user is logged in --%>
<security:authorize var="loggedIn" url="/profile" />


<header class="container header-primary">
  <div class="row">
    <div class="tile tile-full">
      <div class="action action-icon action-menu-primary">
				<span id="js-menu-icon" class="fa fa-bars fa-2x"></span>
				<span id="js-menu-form-search-icon" class="fa fa-search fa-2x"></span>
			</div>
    </div>
  </div>

  <div class="row">
    <div class="tile tile-full">

			<div class="form form-search">
				<form:form
					method="post"
          modelAttribute="searchForm"
					action="/results"
					id="searchForm"
					autocomplete="off">


					<form:input
						type="text"
						name="city"
						id="city"
						path="city"
						placeholder="Find..."
						tabindex="1" />
					<form:errors path="city" cssClass="validationErrorText" />


					<form:input
            id="radiusInput"
            type="number"
            path="radius"
						placeholder="Radius in km"
            tabindex="2"
            step="5" />
					<form:errors path="radius" cssClass="validationErrorText" />

          <div class="row checkboxes">
            <div class="tile tile-third">
              <form:checkbox name="room" id="room" path="roomHelper" />
              <label for="room">Room</label>
            </div>
            <div class="tile tile-third">
              <form:checkbox name="studio" id="studio" path="studioHelper" />
              <label for="studio">Studio</label>
            </div>
            <%-- @Jerome
            TODO: change checkbox attrs to 'house'. --%>
            <div class="tile tile-third">
              <form:checkbox name="studio" id="studio" path="studioHelper" />
              <label for="studio">House</label>
            </div>
          </div>

          <form:checkbox style="display:none" name="neither" id="neither" path="noRoomNoStudio" />
					<form:checkbox style="display:none" name="both" id="both" path="bothRoomAndStudio" />
					<form:checkbox style="display:none" name="type" id="type" path="studio" />
					<form:checkbox style="display:none" name="filtered" id="filtered" path="filtered" />
					<form:errors path="noRoomNoStudio" cssClass="validationErrorText" />


					<form:input
						id="prizeInput"
						type="number"
						path="prize"
						placeholder="Maximum Price in CHF"
						step="50"
						tabindex="3" />
					<form:errors path="prize" cssClass="validationErrorText" />

          <div class="row">
            <div class="tile tile-half">
              <button type="submit" tabindex="4">Find</button>
            </div>
            <div class="tile tile-half">
    					<button type="reset" tabindex="5">Clear</button>
            </div>
          </div>

				</form:form>

			</div>
    </div>
  </div>


  <c:choose>
    <c:when test="${loggedIn}">

    </c:when>
    <c:otherwise>
      <div class="row">
        <div class="tile tile-half action action-tile action-medium">
          <a href="/login">Sign in</a>
        </div>
        <div class="tile tile-half action action-tile">
          <a href="/signup">Sign up</a>
        </div>
      </div>
    </c:otherwise>
  </c:choose>

  <div class="row">
    <nav class="tile-full nav-primary">
      <ul class="menu-primary">
        <li>
          <a href="/~">Home</a>
        </li>
        <li>
          <a href="/about">About us</a>
        </li>
      </ul>
    </nav>
  </div>

  <c:choose>
    <c:when test="${loggedIn}">
      <div class="row">
        <div class="tile tile-full action action-tile">
          <a href="/logout">Sign out</a>
        </div>
      </div>    
    </c:when>
    <c:otherwise>

    </c:otherwise>
  </c:choose>

  <div class="row">
    <div class="tile-full">
      <%-- <a href="#">
        <small>Terms of Use</small>
      </a>
      <a href="#">
        <small>Privacy Policy</small>
      </a> --%>
    </div>
  </div>
</header>
