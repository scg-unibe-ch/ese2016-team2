/**
 * @name validator
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.validator
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {jQuery} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.validator = function (window, document, $, $view, option) {


  var SAFE_INT = Math.pow(2,31) - 1;


  /**
   * @private
   * @type {Object}   error messages object
   */
  var errors = {
    'field-password': {
      isError: function ($this) {
        return $this.val().length < 6;
      },
      is_other_than_server: false,
      text: 'The password must be at least 6 characters long.'
    },

    'field-email': {
      isError: function ($this, callback) {
        $.post("/signup/doesEmailExist", {email: $this.val()}, callback);
      },
      is_other_than_server: true,
      text: 'This username is taken. Please choose another one.'
    },

    'msgSubject': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: true,
      text: 'Please add a subject to your message.'
    },

    'msgTextarea': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: true,
      text: 'Well, it says "Leave a message", so, please drop some lines.'
    },

    'field-street': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: false,
      text: 'Please add an address.'
    },

    'field-city': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: false,
      text: 'Please add a locality from the list.'
    },

    'field-Prize': {
      isError: function ($this) {
        var int = $this.val(); // do not parseInt as string returns 1
        return (int < 1) || (int > SAFE_INT);
      },
      is_other_than_server: false,
      text: 'Price should be at least one lousy buck and not exceed '+ SAFE_INT
    },

    'field-SquareFootage': {
      isError: function ($this) {
        var int = $this.val();
        return (int < 1) || (int > SAFE_INT);
      },
      is_other_than_server: false,
      text: 'Square footage should be at least 1 and not exceed '+ SAFE_INT
    },

    'field-title': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: false,
      text: 'Please add a title.'
    },

    'roomDescription': {
      isError: function ($this) {
        return $this.val() === '';
      },
      is_other_than_server: false,
      text: 'Please add a description.'
    }
  };


	$("#field-password").focusout(function() {
    var $this = $(this);
    if (isViolated($this)) showError($this, true);
		else handleValidity($this);
	});


  $("#field-email").focusout(function() {
    var $this = $(this);
		isViolated($this, function(violated) {
      if (violated) showError($this);
			else handleValidity($this);
    });
	});


  $("#msgSubject, #msgTextarea").focusout(function() {
    var $this = $(this);
    if (isViolated($this)) showError($this);
		else handleValidity($this);
	});


  $("#field-street, #field-city,"+
    "#field-Prize, #field-SquareFootage,"+
    "#field-title, #roomDescription")
    .focusout(function() {
      var $this = $(this);
      if (isViolated($this)) showError($this);
  		else handleValidity($this);
  	});




  /**
   *
   * @private
   * @param  {jQuery}   $this    the form field under test
   * @param  {Function} callback a callback functoin if we have to wait for a
   *                             server response
   * @return {Boolean}           [description]
   */
  function isViolated($this, callback) {
    var error = errors[$this.attr('id')];
    if (callback) return error.isError($this, callback);
    else return error.isError($this);
  }



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

    if (isSensibleError($this))
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
      .attr('placeholder', (value !== '' ? value : placeholder))
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
   * @param  {String} $this  the form field
   * @return {Boolean}    true if there is no server error visible or there is
   *                           a visible server error but error on client side
   *                           is different - else false
   */
  function isSensibleError($this) {
    var
      id = $this.attr('id'),
      $error_field = $('.error-'+id),
      has_server_error = hasServerError($error_field);
    return (!has_server_error ||
            (has_server_error && errors[id].is_other_than_server));
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
