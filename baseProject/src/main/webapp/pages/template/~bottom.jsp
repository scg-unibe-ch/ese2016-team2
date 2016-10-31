<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
  @Jerome
  NB: Ideally, everything js should be put here into one file (per page if
      reasonable).
      This index.js file should log "yay." for testing purposes.

      Heads up!
      As i am using codekit (https://incident57.com/codekit/) for combining
      js files, you either have to use it too or setup your own task flow.
      Codekit is paid software, so, if you do not want to pay, you could use
      grunt (http://gruntjs.com/) or gulp (http://gulpjs.com/). There is even
      a java tool that handles that kind of stuff (i guess), called
      dandelion (http://dandelion.github.io/). Please talk to me, if the latter
      seems to be the best option for you.
--%>
<script src="/resources/js/prod/index.js"></script>
<script>
	// @Jerome
	// TODO: Clean the closet, dude.
  +function (window, document, $) {
    $("#city").autocomplete({
      minLength : 2,
			enabled : true,
      autoFocus : true,
			source : <c:import url="getzipcodes.jsp" />
    });

    var price = document.getElementById('prizeInput');
    var radius = document.getElementById('radiusInput');

		// @Jerome
		// TODO: Do this in a beforeSubmit handler.
    // if(price.value == null || price.value == "" || price.value == "0")
    //   price.value = "500";
    // if(radius.value == null || radius.value == "" || radius.value == "0")
    //   radius.value = "5";
  }(window, document, jQuery);
</script>
</body>
</html>
