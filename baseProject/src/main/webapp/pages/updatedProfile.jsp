<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Well done..." />
	</c:import>
	<div class="container container-pad">
		<div class="row">
			<div class="span-half">
				<h3>${message}</h3>
				<a href="/user?id=${currentUser.id}">Check it out...</a>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .containerEND --%>
</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="updatedProfile" />
</c:import>
