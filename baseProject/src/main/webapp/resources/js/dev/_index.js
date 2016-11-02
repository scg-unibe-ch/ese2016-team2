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

}(window, document, jQuery);
