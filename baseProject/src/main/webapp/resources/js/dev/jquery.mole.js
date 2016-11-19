/**
 * @name fn
 * @namespace
 * @memberof jQuery
 */



/**
 *
 * @param  {Function} func  a function that form contents well
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
      $that = $this.clone(),
      $throw_off;

    if ('value' in $this[0])
      $that.val($this.val());

    $throw_off = func ? func($that) : $that.contents();
    $that.remove();

    $moleholes.each(function(_, x) {
      jQuery(x).html($throw_off);
    });
  });

  return $this;
};
