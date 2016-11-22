/**
 *
 * @name message
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.message
 */



jQuery.flatfindr.register({

  name: 'message',


  /**
   * @memberof jQuery.flatfindr.message
   * @method fn
   *
   * @protected
   * @param  {Object}   window   the window as you know it
   * @param  {Object}   document the document element
   * @param  {Object}   $        jQuery
   * @param  {jQuery}   $view    the default or custom view if set
   * @param  {Object}   option   what ever object param if passed
   * @return {Function}          method that sets up simple dom manipulations
   */
  fn: function (window, document, $, $view, option) {


    var
      /**
       * A bit of an extra delay in ms to prevent janky css transitions.
       *
       * @private
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
        recipientEmail = $.flatfindr.username,
        duration = $.flatfindr.BASE_DURATION;

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
          }, (duration * 3));
        }, (duration * 3) + DURATION_BUFFER);
      });
    });
  }

});
