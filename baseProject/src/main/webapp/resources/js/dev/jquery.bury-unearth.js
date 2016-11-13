/**
 * See <a href="http://jquery.com">http://jquery.com</a>.
 * @name jQuery
 * @class
 * See the jQuery Library  (<a href="http://jquery.com">http://jquery.com</a>) for full details.  This just
 * documents the function and classes that are added to jQuery by this plug-in.
 */

/**
 * @name fn
 * @namespace
 * @memberof jQuery
 */



/**
 * Handles classname replacement for css transitioning when a element should
 * be hidden. It adds/removes useful classes based on the state of the object
 * over time.
 *
 * @param  {string} prefix  the classname prefix to be combined with the state
 * @param  {integer} duration   the delay duration in ms
 * @return {object}          the jquery object with the delayed function
 * @memberof jQuery.fn
 */
jQuery.fn.unearth = function(prefix, duration) {
  var $this = jQuery(this);
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
 * @memberof jQuery.fn
 */
jQuery.fn.bury = function(prefix, duration) {
  var $this = jQuery(this);
  $this.toggleClass(prefix +'Open '+ prefix +'Out');
  setTimeout(function () {
    return $this.toggleClass(prefix +'Out '+ prefix +'Closed');
  }, duration);
};
