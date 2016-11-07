// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @param  {object} jsp      global object properties passed by jsp
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.enquiry = function (window, document, $) {


  $('.enquiry').on('click touch', handleEnquiry);


  function handleEnquiry(e) {
    e.preventDefault();

    var
      $this = $(this),
      $confirm = $this.find('.action-confirm'),
      $cancel = $this.find('.action-cancel'),
      id = $this.attr('data-id');

    if ($this.is('thinInactiveButton')) return;

    $this.toggleClass('js-confirm');

    $confirm.on('click touch', function(e) {
      e.preventDefault();
      $.get("/profile/enquiries/sendEnquiryForVisit?id=" + id);
    });

    $cancel.on('click touch', function(e) {
      e.prevenDefault();
      $confirm.off('click touch');
      $cancel.off('click touch');
      $this.toggleClass('js-confirm');
    });
  }

  //
  // var buttons = $("#visitList table tr button");
  //
	// //Makes the enquiry-button inactive after the user applied to a visit
	// $(buttons).click(function() {
	// 	var buttonText = $(this).attr("class");
  //
	// 	if (buttonText == 'thinInactiveButton') {
	// 		return;
	// 	}
  //
	// 	$("#content").children().animate({opacity: 0.4}, 300, function(){
	// 		$("#confirmationDialog").css("display", "block");
	// 		$("#confirmationDialog").css("opacity", "1");
	// 	});
  //
	// 	var id = $(this).attr("data-id");
  //
	// 	$("#confirmationDialogSend").attr("data-id", id);
	// });
  //
	// function reset(){
	// 	$("#confirmationDialogSend").removeAttr("data-id");
  //
	// 	$("#confirmationDialog").css("display", "none");
	// 	$("#confirmationDialog").css("opacity", "0");
	// 	$("#content").children().animate({opacity: 1}, 300);
	// }
  //
	// $("#confirmationDialogSend").click(function (){
	// 	var id = $(this).attr("data-id");
  //
	// 	$.get("/profile/enquiries/sendEnquiryForVisit?id=" + id);
  //
  //
	// 	var enquiryButton = $("#visitList table tr button[data-id='" + id + "']");
	// 	$(enquiryButton).addClass('thinInactiveButton').removeClass('thinButton');
	// 	$(enquiryButton).html('Enquiry sent');
  //
	// 	reset();
	// });
  //
  //
	// $("#confirmationDialogCancel").click(function (){
	// 	reset();
	// });

};


flatfindr.enquiry(window, document, jQuery);
