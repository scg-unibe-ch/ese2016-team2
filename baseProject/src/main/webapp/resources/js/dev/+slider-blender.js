/**
 * @name sliderBlender
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.sliderBlender
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.sliderBlender = function (window, document, $, $view, option) {


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
        directionNav: false
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
    $slider = $('.slider').flexslider(config.slider);



    /**
     * Close any sidebars if they are in open state.
     * @private
     */
    function closeSidebars() {
      if ($view.is('.sidebarOpen'))
        $view.trigger('sidebar:toggle');
      if ($view.is('.headerPrimaryOpen'))
        $view.trigger('header:toggle');
    }



    /**
     * Transport slide according direction.
     * @protected
     */
    function transport() {
      var dir = $(this).is('.transport-next') ? 'next' : 'prev';
      // Follow user intention and have the slider fully visible.
      // Do it with delay, otherwise user might think sidebar is fixed
      // to first slide.
      setTimeout(closeSidebars, $.flatfindr.BASE_DURATION);
      $slider.flexslider(dir);
    }


    /**
     *
     * @type {Object} pub   public methods
     */
    var pub = {

      /**
       *
       * @memberof jQuery.flatfindr.sliderBlender
       * @method addSliderBlenderCaption
       *
       * @public
       */
      addSliderBlenderCaption: function() {
        var $slider_blender_full = $('.slider-blender-full');
        $('.slide-caption').hover(
          function() { $slider_blender_full.addClass('js-show'); },
          function() { $slider_blender_full.removeClass('js-show'); }
        );
      },


      /**
       *
       * @memberof jQuery.flatfindr.sliderBlender
       * @method addOnClickNavigation
       *
       * @public
       */
      addOnClickNavigation: function() {
        $('.transport-prev, .transport-next').on('click', transport);
      }
    };

    return pub;
};
