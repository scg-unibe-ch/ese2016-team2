// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.search = function (window, document, $) {

  var $container_scroll = $('.container-scroll');

  $('.form-search .container-scroll input').focus(alignTop);
  $('.form-search .container-scroll label').click(alignTop);

  /**
   * [alignTop description]
   * @return {[type]} [description]
   */
  function alignTop() {
    var
      $this = $(this),
      $parent = $container_scroll,
      scrollTop = $parent.scrollTop() + $this.position().top;

      $parent
        .delay(50)
        .animate({scrollTop: scrollTop}, 300);
  };
  
};

flatfindr.search(window, document, jQuery);
