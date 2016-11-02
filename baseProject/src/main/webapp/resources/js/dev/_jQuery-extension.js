+function(window, document, $) {

  /**
   * Handles classname replacement for css transitioning when a element should
   * be hidden. It adds/removes useful classes based on the state of the object
   * over time.
   *
   * @param  {string} prefix  the classname prefix to be combined with the state
   * @param  {integer} duration   the delay duration in ms
   * @return {object}          the jquery object with the delayed function
   */
  $.fn.unearth = function(prefix, duration) {
    var $this = $(this);
    $this.toggleClass(prefix +'Closed '+ prefix +'In');
    setTimeout(function () {
      return $this.toggleClass(prefix +'In '+ prefix +'Open');
    }, duration);
  };



  /**
   * Handles classname replacement for css transitioning when a element should
   * be shown. It adds/removes useful classes based on the state of the object
   * over time.
   *
   * @param  {string} prefix  the classname prefix to be combined with the state
   * @param  {integer} duration   the delay duration in ms
   * @return {object}          the jquery object with the delayed function
   */
  $.fn.bury = function(prefix, duration) {
    var $this = $(this);
    $this.toggleClass(prefix +'Open '+ prefix +'Out');
    setTimeout(function () {
      return $this.toggleClass(prefix +'Out '+ prefix +'Closed');
    }, duration);
  };

}(window, document, jQuery);
