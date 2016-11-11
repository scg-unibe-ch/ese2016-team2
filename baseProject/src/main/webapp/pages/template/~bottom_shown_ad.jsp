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

		adDescription: function () {
			return $.flatfindr
				.with({
					shownAdvertisementID: '${shownAd.id}',
					shownAdvertisement: '${shownAd}',
					shownAdUsername: '${shownAd.user.username}'
				})
				.add([
					'search',
					'sidebar',
					'sliderBlender',
					'bookmark',
					'message',
					'enquiry'
				]);
		}
	};

	js.common();
	pagename in js && js[pagename]();

})(jQuery, '${param.js}');
</script>

</body>
</html>
