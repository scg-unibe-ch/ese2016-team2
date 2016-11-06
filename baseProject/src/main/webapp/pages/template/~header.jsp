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

          <div class="container-scroll">

            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:radiobutton name="buyable" id="rent" path="buyable" value="0" />
                <label for="rent">Rent</label>
              </div>
              <div class="tile tile-half">
                <form:radiobutton name="buyable" id="buy" path="buyable" value="1" />
                <label for="buy">Buy</label>
              </div>
            </div>


  					<form:input
              id="radiusInput"
              type="number"
              path="radius"
  						placeholder="Radius in km (5, 10, 15,... , 500)"
              tabindex="2"
              step="5"
              min="5"
              max="500" />
  					<form:errors path="radius" cssClass="validationErrorText" />


            <div class="row checkboxes">
              <div class="tile tile-third">
                <form:checkbox name="room" id="room" path="room" />
                <label for="room">Room</label>
              </div>
              <div class="tile tile-third">
                <form:checkbox name="studio" id="studio" path="studio" />
                <label for="studio">Studio</label>
              </div>
              <div class="tile tile-third">
                <form:checkbox name="house" id="house" path="house" />
                <label for="house">House</label>
              </div>
            </div>


            <form:checkbox style="display:none" name="neither" id="neither" path="neither" />
            <form:errors path="neither" cssClass="validationErrorText" />


  					<form:input
  						id="prizeInput"
  						type="number"
  						path="prize"
  						placeholder="Maximum Price in CHF"
  						step="50"
  						tabindex="3"
              min="50" />
  					<form:errors path="prize" cssClass="validationErrorText" />


            <%-- <label for="earliestMoveInDate">Earliest move-in date</label>
            <label for="earliestMoveOutDate">Earliest move-out date</label>

            <form:input type="text" id="field-earliestMoveInDate" path="earliestMoveInDate" />
            <form:input type="text" id="field-earliestMoveOutDate" path="earliestMoveOutDate" />


            <label for="latestMoveInDate">Latest move-in date</label>
            <label for="latestMoveOutDate">Latest move-out date</label>


            <form:input type="text" id="field-latestMoveInDate" path="latestMoveInDate" />
            <form:input type="text" id="field-latestMoveOutDate" path="latestMoveOutDate" />
             --%>



            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:checkbox id="field-smoker" path="smokers" value="1" />
                <label for="field-smoker">Smoking allowed</label>
              </div>
              <div class="tile tile-half">
                <form:checkbox id="field-animals" path="animals" value="1" />
                <label for="field-animals">Animals allowed</label>
              </div>
            </div>

            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:checkbox id="field-balcony" path="balcony" value="1" />
                <label for="field-balcony">Balcony or Patio</label>
              </div>
              <div class="tile tile-half">
                <form:checkbox id="field-garden" path="garden" value="1" />
                <label for="field-garden">Garden (co-use)</label>
              </div>
            </div>

            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:checkbox id="field-cellar" path="cellar" value="1" />
                <label for="field-cellar">Cellar or Attic</label>
              </div>
              <div class="tile tile-half">
                <form:checkbox id="field-furnished" path="furnished" value="1" />
                <label for="field-furnished">Furnished</label>
              </div>
            </div>

            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:checkbox id="field-cable" path="cable" value="1" />
                <label for="field-cable">Cable TV</label>
              </div>
              <div class="tile tile-half">
                <form:checkbox id="field-garage" path="garage" value="1" />
                <label for="field-garage">Garage</label>
              </div>
            </div>

            <div class="row checkboxes">
              <div class="tile tile-half">
                <form:checkbox id="field-internet" path="internet" value="1" />
                <label for="field-internet">WiFi</label>
              </div>
            </div>

          </div>

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
