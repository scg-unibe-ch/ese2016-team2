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



		var shownAdvertisementID = "${shownAd.id}";
		var shownAdvertisement = "${shownAd}";


		flatfindr.bookmark(
			window, document, jQuery, {
				shownAdvertisementID: "${shownAd.id}",
				shownAdvertisement: "${shownAd}"
			});


		// $("#newMsg").click(function(){
		// 	$("#content").children().animate({opacity: 0.4}, 300, function(){
		// 		$("#msgDiv").css("display", "block");
		// 		$("#msgDiv").css("opacity", "1");
		// 	});
		// });
		//
		// $("#messageCancel").click(function(){
		// 	$("#msgDiv").css("display", "none");
		// 	$("#msgDiv").css("opacity", "0");
		// 	$("#content").children().animate({opacity: 1}, 300);
		// });

		$("#messageSend").click(function (){
			if($("#msgSubject").val() != "" && $("#msgTextarea").val() != ""){
				var subject = $("#msgSubject").val();
				var text = $("#msgTextarea").val();
				var recipientEmail = "${shownAd.user.username}";
				$.post("profile/messages/sendMessage", {subject : subject, text: text, recipientEmail : recipientEmail}, function(){
					// $("#msgDiv").css("display", "none");
					// $("#msgDiv").css("opacity", "0");
					$("#msgSubject").val("");
					$("#msgTextarea").val("");
					// $("#content").children().animate({opacity: 1}, 300);
				})
			}
		});
  }(window, document, jQuery);
</script>

</body>
</html>
