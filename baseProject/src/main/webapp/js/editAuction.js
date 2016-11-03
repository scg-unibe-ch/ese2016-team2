$(document).ready(function(){
	
	$(".pictureThumbnail button").click(function (){
		var auctionId = $(this).attr("data-auction-id");
		var pictureId = $(this).attr("data-picture-id");
		
		$.post("/profile/editAuction/deletePictureFromAuction", {auctionId:auctionId, pictureId:pictureId}, function(){
			var button = $(".pictureThumbnail button[data-auction-id='" + auctionId + "'][data-picture-id='" + pictureId + "']");			
			var div = $(button).parent();
			$(div).children().animate({opacity: 0}, 300, function(){
				$(div).remove();
			});
		});
	});
	
});