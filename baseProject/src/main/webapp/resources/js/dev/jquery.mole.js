/**
 * See <a href="http://jquery.com">http://jquery.com</a>.
 * @name jQuery
 * @class
 * See the jQuery Library  (<a href="http://jquery.com">http://jquery.com</a>) for full details.  This just
 * documents the function and classes that are added to jQuery by this plug-in.
 */

/**
 * @name fn
 * @namespace
 * @memberof jQuery
 */



/**
 *
 * @param  {Function} func  a function form contents well
 * @return {jQuery}      this jQuery object
 * @memberof jQuery.fn
 */
jQuery.fn.mole = function(func) {
  var
    $this = jQuery(this),
    $moleholes;

  if (!$this.is('.fn-mole')) return;
  $moleholes = jQuery('.fn-molehole-'+ $this.attr('id'));

  $this.on('mole', function() {
    var
      $that = jQuery(this).clone(),
      $throw_off = func ? func($that) : $that.contents();

    //console.log($that.val(), $throw_off);
    //$that.remove();
    $moleholes.each(function(_, x) {
      jQuery(x).html($throw_off);
    });
  });

  return $this;
};
