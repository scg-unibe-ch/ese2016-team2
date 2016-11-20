<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
<script>

	(function ($, pagename) {

		var js = {
			common: function () {
				return $.flatfindr
					.with({ ZIP_CODES: <c:import url="getzipcodes.jsp" /> })
					.add(['header']);
			},

			login: function () {
				return;
			},

			signup: function () {
				return $.flatfindr.add([
					'signup'
				]);
			},

			index: function () {
				return $.flatfindr.add([
					'search',
					'sliderBlender',
					'sliderBlenderCaption'
				]);
			},

			searchAd: function () {
				return $.flatfindr.add([
					'search'
				]);
			},

			results: function () {
				return $.flatfindr.add([
					'filter',
					'map'
				]);
			},

			placeAd: function () {
				return $.flatfindr
					.with({ PAGE_NAME: pagename })
					.add([
						'place',
						'imageUpload'
					]);
			},

			placeAuction: function () {
				return js.placeAd();
			}
		};

		js.common();
		pagename in js && js[pagename]();

	})(jQuery, '${param.js}');
</script>

<c:choose>
    <c:when test="${param.map=='1'}">
			<script
				async
				defer
				src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAh9mJhrCy-xTWy5b3Niop8QilZAdMh1To&callback=initMap">
			</script>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

<script src="js/hotfix.js"></script>

</body>
</html>
