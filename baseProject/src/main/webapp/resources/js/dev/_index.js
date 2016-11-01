+function(window, document, $) {


  /**
   *
   *
   *
   *
   *
   */
   var $slider_blender_full = $('.slider-blender-full');

   $('.slide-caption').hover(
     function() { $slider_blender_full.addClass('js-show'); },
     function() { $slider_blender_full.removeClass('js-show'); }
   );

   



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

    animation: "fade",
    slideshowSpeed: 6400,
    animationSpeed: 1600,

    controlNav: false,
    directionNav: false
  });

  $('.slider').flexslider({
    namespace: 'slider-',

    animation: "slide",
    slideshow: false,
    animationSpeed: 800
  });

}(window, document, jQuery);
