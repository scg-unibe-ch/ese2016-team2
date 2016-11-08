<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
<script>
  +function (window, document, $) {

		flatfindr.filter(window, document, $, {
			zipcodes: <c:import url="getzipcodes.jsp" />
		});

  }(window, document, jQuery);
</script>
</body>
</html>
