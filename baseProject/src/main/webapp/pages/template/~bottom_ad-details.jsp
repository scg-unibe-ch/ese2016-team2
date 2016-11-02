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

		function attachBookmarkClickHandler(){
			$("#bookmarkButton").click(function() {

				$.post("/bookmark", {id: shownAdvertisementID, screening: false, bookmarked: false}, function(data) {
					$('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
					switch(data) {
					case 0:
						alert("You must be logged in to bookmark ads.");
						break;
					case 1:
						// Something went wrong with the principal object
						alert("Return value 1. Please contact the WebAdmin.");
						break;
					case 3:
						$('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
						break;
					default:
						alert("Default error. Please contact the WebAdmin.");
					}

					attachBookmarkedClickHandler();
				});
			});
		}

		function attachBookmarkedClickHandler(){
			$("#bookmarkedButton").click(function() {
				$.post("/bookmark", {id: shownAdvertisementID, screening: false, bookmarked: true}, function(data) {
					$('#bookmarkedButton').replaceWith($('<a class="right" id="bookmarkButton">' + "Bookmark Ad" + '</a>'));
					switch(data) {
					case 0:
						alert("You must be logged in to bookmark ads.");
						break;
					case 1:
						// Something went wrong with the principal object
						alert("Return value 1. Please contact the WebAdmin.");
						break;
					case 2:
						$('#bookmarkedButton').replaceWith($('<a class="right" id="bookmarkButton">' + "Bookmark Ad" + '</a>'));
						break;
					default:
						alert("Default error. Please contact the WebAdmin.");

					}
					attachBookmarkClickHandler();
				});
			});
		}

		$(document).ready(function() {
			attachBookmarkClickHandler();
			attachBookmarkedClickHandler();

			$.post("/bookmark", {id: shownAdvertisementID, screening: true, bookmarked: true}, function(data) {
				if(data == 3) {
					$('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
					attachBookmarkedClickHandler();
				}
				if(data == 4) {
					$('#shownAdTitle').replaceWith($('<h1>' + "${shownAd.title}" + '</h1>'));
				}
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
		});
  }(window, document, jQuery);
</script>

</body>
</html>
