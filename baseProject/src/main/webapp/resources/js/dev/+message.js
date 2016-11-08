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
flatfindr.message = function (window, document, $, jsp) {


  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @constant
     */
    BASE_DURATION = 350,



    /**
     * A bit of an extra delay in ms to prevent janky css transitions.
     *
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 10;




  $("#messageSend").click(function() {
    var
      $this = $(this),
      $msgSubject = $('#msgSubject'),
      $msgTextarea = $('#msgTextarea');

    if ($this.attr('disable') ||
        $msgSubject.val() == "" ||
        $msgTextarea.val() == "") return;

    var
      subject = $msgSubject.val(),
      text = $msgTextarea.val(),
      recipientEmail = jsp.shownAdUsername;

    $.post("profile/messages/sendMessage", {
      subject : subject,
      text: text,
      recipientEmail : recipientEmail
    }, function() {
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
        }, (BASE_DURATION * 3));
      }, (BASE_DURATION * 3) + DURATION_BUFFER);
    });
  });

};
