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
				<span id="js-menu-icon" class="fa fa-bars fa-2x"></span>
				<%-- <span id="js-menu-form-search-icon" class="fa fa-search fa-2x"></span> --%>
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
          <a href="/">Home</a>
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
