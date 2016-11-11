/**
 *
 * @name sliderBlenderCaption
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.sliderBlenderCaption
 */



jQuery.flatfindr.register({

  name: 'sliderBlenderCaption',


  /**
   * @memberof jQuery.flatfindr.sliderBlenderCaption
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

    var $slider_blender_full = $('.slider-blender-full');


    $('.slide-caption').hover(
      function() { $slider_blender_full.addClass('js-show'); },
      function() { $slider_blender_full.removeClass('js-show'); }
    );

  }

});
