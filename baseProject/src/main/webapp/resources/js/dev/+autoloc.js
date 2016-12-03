/**
 *
 * @name autoloc
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.autoloc
 */



jQuery.flatfindr.register({

  name: 'autoloc',


  /**
   * @memberof jQuery.flatfindr.autoloc
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

    $("#city").autocomplete({
      minLength : 2,
      enabled : true,
      autoFocus : true,
      source : $.flatfindr.ZIP_CODES
    });
    
  }

});
