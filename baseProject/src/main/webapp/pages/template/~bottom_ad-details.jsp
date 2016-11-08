<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
<script>
  +function (window, document, $) {

		flatfindr.search(window, document, $, {
			zipcodes: <c:import url="getzipcodes.jsp" />
		});

		flatfindr.bookmark(window, document, $, {
			shownAdvertisementID: "${shownAd.id}",
			shownAdvertisement: "${shownAd}"
		});

		flatfindr.message(window, document, $, {
			shownAdUsername: "${shownAd.user.username}"
		});

  }(window, document, jQuery);
</script>

</body>
</html>
