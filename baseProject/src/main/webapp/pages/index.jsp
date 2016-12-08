<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">

	<div class="container container-pad">
		<div class="row">
			<div class="tile-full brand brand-primary">
				<h1><a href="/searchAd" title="Start searching."><span class="base-color-opposite">flat</span><span class="base-color">findr</span></a></h1>
			</div>
		</div>
	</div>

	<div class="container container-pad">
		<div class="row">
			<div>




			</div>
		</div>
	</div>

	<%--
		@Jerome
		For the outer div slider or blender may be chosen as class. It then does
		what it says.
	 --%>
	<div class="blender slider-blender-full">
		<ul class="slides">

			<c:choose>

				<c:when test="${empty newestAds}">

					<%--
						@Jerome
						TODO: Add fallback images if there are no ads placed yet

				    <li>
				      <img src="" />
				    </li>
				    <li>
				      <img src="" />
				    </li>
				    <li>
				      <img src="" />
				    </li>
				    <li>
				      <img src="" />
				    </li>

					--%>

				</c:when>

				<c:otherwise>

					<c:forEach var="advertisement" items="${newestAds}">

						<c:choose>

							<c:when test="${advertisement.auction}">


								<li class="slide" style="background-image: url(${advertisement.pictures[0].filePath})">
									<a title="View this auction." class="slide-caption" href="<c:url value='/auction?id=${advertisement.id}' />">
										<span class="fa fa-gavel" aria-hidden="true"></span>
										<span>${advertisement.city}</span>
										<span>${advertisement.title}</span>
									</a>
								</li>

							</c:when>

							<c:otherwise>

								<li class="slide" style="background-image: url(${advertisement.pictures[0].filePath})">
									<a title="View this ad." class="slide-caption" href="<c:url value='/ad?id=${advertisement.id}' />">
										<span>${advertisement.city}</span>
										<span>${advertisement.title}</span>
									</a>
								</li>

							</c:otherwise>

						</c:choose>

					</c:forEach>

				</c:otherwise>

			</c:choose>

		</ul> <%-- .slides END --%>
	</div> <%-- .[slider|blender] END --%>

</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="index" />
</c:import>
