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

  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @constant
     */
    BASE_DURATION = 350,



    /**
     * An opinionated bit of an extra delay. (usability specific)
     *
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 50,


    /**
     * The scrollable container.
     *
     * @type {object}
     */
    $container_scroll = $('.container-scroll');



  /**
   * All input fields within the search form scrollable container.
   */
  $('.form-search .container-scroll input')
    .focus(alignInputToTop);



  /**
   * All checkboxes within the search form scrollable container.
   */
  $('.form-search .container-scroll label')
    .on('click', alignInputToTop);



  /**
   * The clear button.
   */
  $('button[type=reset]')
    .on('click', alignTop);



  /**
   * Align the focused or cliced element to top of the visible part of the
   * scrollable $container_scroll.
   * @param  {object} e the event object click or touch.
   */
  function alignInputToTop() {
    var
      $this = $(this),
      offset_mod = 0;

    if ($this.is('.js-has-label')) {
      offset_mod =
        $this
          .parents('.row')
          .first()
          .find('label')
          .outerHeight();
    }

    animateScrollTop(
      $container_scroll.scrollTop() + $(this).position().top - offset_mod
    );
  }



  /**
   * Align $container_scroll to top, so scrollTop position is 0.
   * @param  {object} e the event object click or touch.
   */
  function alignTop() {
    animateScrollTop(0);
  }



  /**
   * Animate the change of the scrollTop position of the scrollable
   * element $container_scroll.
   * @param  {number} scrollTop the new scroll position to be animated to.
   */
  function animateScrollTop(scrollTop) {
    $container_scroll
      .delay(DURATION_BUFFER)
      .animate({scrollTop: scrollTop}, BASE_DURATION);
  }

};

flatfindr.search(window, document, jQuery);
