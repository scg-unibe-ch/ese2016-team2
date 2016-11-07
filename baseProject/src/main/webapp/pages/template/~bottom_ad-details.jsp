<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
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



		$("#earliestMoveInDate").datepicker({
			altField: '#field-earliestMoveInDate',
			dateFormat : 'dd-mm-yy'
		});
		$("#latestMoveInDate").datepicker({
			altField: '#field-latestMoveInDate',
			dateFormat : 'dd-mm-yy'
		});
		$("#earliestMoveOutDate").datepicker({
			altField: '#field-earliestMoveOutDate',
			dateFormat : 'dd-mm-yy'
		});
		$("#latestMoveOutDate").datepicker({
			altField: '#field-latestMoveOutDate',
			dateFormat : 'dd-mm-yy'
		});



		flatfindr.bookmark(
			window, document, $, {
				shownAdvertisementID: "${shownAd.id}",
				shownAdvertisement: "${shownAd}"
			});

		flatfindr.message(
			window, document, $, {
				shownAdUsername: "${shownAd.user.username}"
			});

  }(window, document, jQuery);
</script>

</body>
</html>
