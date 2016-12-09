/**
 * @name message
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.message
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.message = function (window, document, $, $view, option) {


  var
    /**
     * A bit of an extra delay in ms to prevent janky css transitions.
     *
     * @private
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 10;




  $("#messageSend").on('click', function() {
    sendMessage.call(this, $.flatfindr.username);
  });


  $("#new_messageSend").on('click', function() {
    animateSubmit($(this));
  });


  $("#inbox").click(function() {
		$.post("/profile/message/inbox", function(data) {
      $('.form-messages').removeClass('js-show');
			loadMessages(data);
			prepareRows();
		}, 'json');
	});


  $("#newMessage").click(function(){
		$('.form-messages').addClass('js-show');
	});


	$("#sent").click(function() {
		$.post("/profile/message/sent", function(data) {
			loadMessages(data);
			prepareRows();
		}, 'json');
	});


	$("#receiverEmail").focusout(function() {
		var text = $("#receiverEmail").val();

		$.post("/profile/messages/validateEmail", {email:text}, function(data) {
			if (data != text) {
				alert(data);
				$("#receiverEmail").val("");
			}
		});
	});


	$("#messageForm").submit(function (e){
		if($("#receiverEmail").val() == ""){
			e.preventDefault();
		}
	});



  /**
   * Send the message.
   * @private
   */
  function sendMessage(recipient) {
    var
      $this = $(this),
      $msgSubject = $('#msgSubject'),
      $msgTextarea = $('#msgTextarea');

    if ($this.attr('disable') ||
        $msgSubject.val() == "" ||
        $msgTextarea.val() == "") {
      if ($msgSubject.val() == ""){
        
      }

      return;
    }

    var
      subject = $msgSubject.val(),
      text = $msgTextarea.val(),
      recipientEmail = recipient;

    $.post("profile/messages/sendMessage", {
      subject : subject,
      text: text,
      recipientEmail : recipientEmail
    }, function () { animateSubmit($this); });
  }




  /**
   * Show some animations during submitting proc, so user is happy.
   * @private
   */
  function animateSubmit($this) {
    var
     duration = $.flatfindr.BASE_DURATION,
     $msgSubject = $('#msgSubject'),
     $msgTextarea = $('#msgTextarea');

    $this
      .attr('disable', 'disable')
      .toggleClass('submit-state-before submit-state-submitting');
    setTimeout(function () {
      $this.toggleClass('submit-state-submitting submit-state-after');
      $msgSubject.val("");
      $msgTextarea.val("");
      setTimeout(function () {
        $this
          .removeAttr('disable')
          .toggleClass('submit-state-after submit-state-before');
      }, (duration * 3));
    }, (duration * 3) + DURATION_BUFFER);
  }




  /**
   * @private
   * @param  {Object} data
   */
  function loadMessages(data) {
  	$("#messageList table tr:gt(0)").remove();
  	$.each(data, function(index, message) {
  		var result = '<tr data-id="' + message.id + '" class="' + message.state + '" >';
  		result += '<td><span class="fa fa-circle" aria-hidden="true"></span>' + message.subject + '</td>';
  		result += '<td>' + message.sender.email + '</td>';
  		result += '<td>' + message.recipient.email + '</td>';
  		result += '<td>' + message.dateSent + '</td></tr>';

  		$("#messageList table").append(result);
  	});
  }



  /**
   *
   * @protected
   */
  function prepareRows() {
  	var $rows = $("#messageList table tr:gt(0)");
  	$rows.click(function() {
      $('.form-messages').removeClass('js-show');
  		var id = $(this).attr("data-id");
  		$(this).removeClass("UNREAD");
  		$.get("/profile/readMessage?id=" + id, function (data) {
  			$.get("/profile/messages/getMessage?id=" + id, function(data) {
  				var result = '<h3>' + data.subject + '</h3><ul>';
  				result += '<li><b>To: </b>' + data.recipient.email + '</li>';
  				result += '<li><b>From: </b>' + data.sender.email + '</li>';
  				result += '<li><b>Date sent: </b>' + data.dateSent + '</li>';
  				result += '</ul><p>' + data.text + '</p>';
  				$("#messageDetail").html(result);
  			}, 'json');
  			$.flatfindr.bits.unreadMessages("header");
  			$.flatfindr.bits.unreadMessages("messages");
  		});
  	});
  }



  var pub = {

    /**
     *
     * @memberof jQuery.flatfindr.message
     * @method update
     *
     * @public
     */
    update: function () {
      prepareRows();
    }
  };

  return pub;

};
