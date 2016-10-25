<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />

<pre>
	<a href="/">Home</a>   &gt;   Place advertisement</pre>

<h1>Place an advertisement</h1>
<hr />
<h2>Choose of you want to add an ad or an auction.</h2>

<table>
	<tr>
		<td><a href="/profile/placeAd"><button type="button">Place Ad</button></a></td>
		<td><a href="/profile/placeAuction"><button type="button">Place Auction</button></a>
	</tr>
</table>

<c:import url="template/footer.jsp" />