<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
<script>

	(function ($, pagename) {

		var js = {
			index: function () {
				return $.flatfindr
					.with({ ZIP_CODES: <c:import url="getzipcodes.jsp" /> })
					.add(['header', 'search']);
			},

			adDescreption: function () {
				return;
			}
		}

		pagename in js && js[pagename]();

	})(jQuery, '${param.js}');

</script>
</body>
</html>
