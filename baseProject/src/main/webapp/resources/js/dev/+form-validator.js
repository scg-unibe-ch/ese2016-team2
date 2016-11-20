/**
 *
 * @name validator
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.validator
 */



jQuery.flatfindr.register({

  name: 'validator',


  /**
   * @memberof jQuery.flatfindr.validator
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

    // Validate the email field
  	$(document).ready(function() {
  		$("#field-email").focusout(function() {
  			var text = $(this).val();
  			$.post("/signup/doesEmailExist", {email: text}, function(data){
  				if(data){
  					alert("This username is taken. Please choose another one!");
  					$("#field-email").val("");
  				}
  			});
  		});
  	});
    
  }

});
