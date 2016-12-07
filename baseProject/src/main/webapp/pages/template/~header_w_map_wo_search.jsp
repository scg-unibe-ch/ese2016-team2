<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<%-- check if user is logged in --%>
<security:authorize var="loggedIn" url="/profile" />


<header class="container header-primary wo-search">
  <div class="row">
    <div class="tile tile-full">
      <div class="action action-icon action-menu-primary">
        <div class="icon-pusher"></div>
        <div id="icons-bar" class="icon-wrapper">
          <span id="js-menu-icon" class="fa fa-bars fa-2x"></span>
  				<a href="/searchAd" title="Search ads"><span class="fa fa-search fa-2x"></span></a>
          <span id="js-map" class="fa fa-map-marker fa-2x"></span>
        </div>
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


  <div class="container-scroll menu-max-height">

    <div class="row">
      <nav class="tile-full nav-primary">
        <ul class="menu-primary">
          <li>
            <a href="/">Home</a>
          </li>

          <c:choose>
            <c:when test="${loggedIn}">
              <%@include file='/pages/getUserPicture.jsp' %>
              <li><a href="/profile/placeAd">Place an ad</a></li>
              <li><a href="/profile/placeAuction">Place an auction</a></li>
              <li><a href="/profile/myRooms">My rooms</a></li>
              <li><a id="messageLink" href="/profile/messages">Messages</a></li>
              <li><a href="/profile/enquiries">Enquiries</a></li>
              <li><a href="/profile/schedule">Schedule</a></li>
              <li><a href="/profile/alerts">Alerts</a></li>
              <li>
              <% out.print("<a href=\"/user?id=" + realUser.getId() + "\">Public Profile</a>"); %>
              </li>
              <li><a href="/register">Premium Account</a></li>

            </c:when>
            <c:otherwise>

            </c:otherwise>
          </c:choose>


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

  </div>

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
