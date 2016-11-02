+function (window, document, $) {

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
  $('.blender').flexslider({
    namespace: 'blender-',

    animation: 'fade',
    slideshowSpeed: 6400,
    animationSpeed: 1600,

    controlNav: false,
    directionNav: false
  });

  $('.slider').flexslider({
    namespace: 'slider-',

    animation: 'slide',
    slideshow: false,
    animationSpeed: 800,

    controlNav: false,
    directionNav: false
  });

}(window, document, jQuery);
