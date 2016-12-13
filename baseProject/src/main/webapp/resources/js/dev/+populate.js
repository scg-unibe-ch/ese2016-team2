/**
 * @name populate
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.populate
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {jQuery} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.populate = function (window, document, $, $view, option) {

  var popul = $.flatfindr.popul;

  for (var o in popul) {
    var
      item = popul[o],
      $item = $(o);

    if ('val' in item) $item.val(item.val);
    else $item.text(item);
  }

};
