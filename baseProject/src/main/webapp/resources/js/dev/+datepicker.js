/**
 *
 * @name datepicker
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.datepicker
 */



jQuery.flatfindr.register({

  name: 'datepicker',


  /**
   * @memberof jQuery.flatfindr.datepicker
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
      datepickers = option,
      $datepicker;

    for (var item in datepickers) {
      $datepicker =
        $(item).datepicker({
          altField: item.altfield,
          dateFormat: item.format
        });

      if (item.unset)
        $datepicker.datepicker('setDate', null);
    }
  }

});
