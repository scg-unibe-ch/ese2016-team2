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
flatfindr.sliderBlender = function (window, document, $) {

  /**
   * Configurations for the flexslider plugin
   * @type {Object}
   */
  var config = {
    /**
     * Blender configurations
     * @type {Object}
     */
    blender: {
      namespace: 'blender-',

      animation: 'fade',
      slideshowSpeed: 6400,
      animationSpeed: 1600,

      controlNav: false,
      directionNav: false
    },
    /**
     * Slider Configurations
     * @type {Object}
     */
    slider: {
      namespace: 'slider-',

      animation: 'slide',
      slideshow: false,
      animationSpeed: 800,

      controlNav: false,
      directionNav: true,

      prevText: "Prev (provi)",
      nextText: "Next (provi)"
    }
  };



  /**
   * .blender / .slider
   *
   * Adjust .blender or .slider behavior (and html output [navigation]). The
   * slideshow property is true by default, meaning it blends or slides the
   * images automatically.
   *
   * Check out all properties here:
   * https://github.com/woocommerce/FlexSlider/wiki/FlexSlider-Properties
   */
  $('.blender').flexslider(config.blender);
  $('.slider').flexslider(config.slider);

};

flatfindr.sliderBlender(window, document, jQuery);
