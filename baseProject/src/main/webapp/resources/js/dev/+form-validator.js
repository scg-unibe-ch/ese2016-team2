/**
 * @name validator
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.validator
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.validator = function (window, document, $, $view, option) {



  /**
   *
   * @type {Object}   error messages object
   */
  var errors = {
    'field-password': {
      text: 'The password must be at least 6 characters long.',
      clientOnly: false
    },
    'field-email': {
      text: 'This username is taken. Please choose another one.',
      clientOnly: true
    }
  };


	$("#field-password").focusout(function() {
    var
      $this = $(this),
      tooshort = $this.val().length < 6;

    if (tooshort) showError($this, true);
		else handleValidity($this);
	});


  $("#field-email").focusout(function() {
    var $this = $(this);
		$.post("/signup/doesEmailExist", {email: $this.val()}, function(exists){
			if (exists) showError($this);
			else handleValidity($this);
		});
	});



  /**
   *
   * @private
   * @param  {jQuery} $this       the form field
   * @param  {Boolean} hiddenValue if the value should be output as bullets
   *                               (hidden) as for passwords
   */
  function showError($this, hiddenValue) {
    var
      id = $this.attr('id'),
      value = hiddenValue ?
        getValueAsBullets($this.val().length) :
        $this.val(),
      placeholder = $this.attr('placeholder'),
      $error_field = $('.error-'+id);

    if (isSensibleError(id))
      $error_field
        .addClass('js-show')
        .find('.validationErrorText')
        .text(errors[id].text);
    else
      $error_field
        .next('span.validationErrorText')
        .animate({marginTop: '-12px', marginBottom: '12px'}, function() {
          $(this).animate({marginTop: '0', marginBottom: '0'});
        });

    $this
      .attr('placeholder', value)
      .val('')
      .on('click', function () {
        $this
          .off('click')
          .attr('placeholder', placeholder);
      });
  }



  /**
   * @private
   * @param  {jQuery} $this the form field
   */
  function handleValidity($this) {
    $('.error-'+$this.attr('id')).removeClass('js-show');
  }



  /**
   *
   * @private
   * @param  {Number} length the length of the form field value
   * @return {String}        a string of bullets with the same length as the
   *                           value
   */
  function getValueAsBullets (length) {
    var bullets = '';
    while (length--) bullets += '•';
    return bullets;
  }



  /**
   *
   * @private
   * @param  {String} id  the id of the form field
   * @return {Boolean}    [description]
   */
  function isSensibleError(id) {
    var
      $error_field = $('.error-'+id),
      has_server_error = hasServerError($error_field);
    return (!has_server_error ||
            (has_server_error && errors[id].clientOnly));
  }



  /**
   *
   * @private
   * @param  {jQuery}  $error_field the 'client side' error field
   * @return {Boolean}             true if there already is a server error in the
   *                                    dom for the form field - else false
   */
  function hasServerError($error_field) {
    return $error_field.next('span.validationErrorText').length;
  }
}
