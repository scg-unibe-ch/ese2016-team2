/**
 *
 * @name populate
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.populate
 */



jQuery.flatfindr.register({

  name: 'populate',


  /**
   * @memberof jQuery.flatfindr.populate
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

    var popul = $.flatfindr.popul;

    for (var o in popul) {
      var
        item = popul[o],
        $item = $(o);

      if ('val' in item) $item.val(item.val);
      else $item.text(item);
    }
  }

});
