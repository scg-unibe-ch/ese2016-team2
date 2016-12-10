<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="About us..." />
	</c:import>

	<div class="container container-pad">
		<div class="row">
			<div class="span-half">
				<img id="about" src="/img/about.jpg">
			</div>
			<div class="span-half">
				<p>
					<span class="base-color-opposite">flat</span><span class="base-color">findr </span> was written and is maintained by
					random guys waiting in line for the restroom.
				</p>
			</div>
		</div>
	</div> <%-- .containerEND --%>
</main>


<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp"/>
