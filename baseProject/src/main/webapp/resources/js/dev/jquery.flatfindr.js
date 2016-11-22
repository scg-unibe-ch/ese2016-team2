/**
 * See <a href="http://jquery.com">http://jquery.com</a>.
 * @name jQuery
 * @class
 * See the jQuery Library  (<a href="http://jquery.com">http://jquery.com</a>) for full details.  This just
 * documents the function and classes that are added to jQuery by this plug-in.
 */
jQuery.extend({

  /**
   * Flatfindr provides some convenient methods to setup the current page.
   * Due to the 'need' of globals (zip codes writtin into js on page request) and
   * having jQuery as global anyway, jQuery is extended with the flatfindr object.
   * @memberof jQuery
   * @namespace jQuery.flatfindr
   * @property {Array} ZIP_CODES  - the zipcods for search forms
   * @property {Number} BASE_DURATION  - the $base_duration corresponding the one
   *                                   used for css transitions
   * @property {String} VIEW  - the element selector string
   *
   * @public
   * @type {Object}
   */
  flatfindr: {

    /**
     * Will be populated when the document needs zipcodes (e.g. search form)
     * @memberof jQuery.flatfindr
     *
     * @public
     * @type {Array}
     */
    ZIP_CODES: [],


    /**
     * A default duration value which corresponds to the $base_duration used
     * for css trasitions. In js files, this value is used for timeouts to play
     * well with css transitions.
     * @memberof jQuery.flatfindr
     *
     * @public
     * @type {Number}
     */
    BASE_DURATION: 350,


    /**
     * The default view. A view has classes toggled by js to trigger related
     * css transitions. A view can be set when registering a module. It can
     * therefore be different for each module. Where omitted, the body element
     * is the view by default.
     * @memberof jQuery.flatfindr
     *
     * @public
     * @type {String}
     */
    VIEW: 'body[view]',




    /**
     * Register a module. For instance, this could be the header or the sidebar
     * module. Once a module is registered within flatfindr, it can be called
     * from anywhere.
     * NB: Modules are registered and called per document. This means that if
     * a document does not register a module it is not available even if a
     * document that was loaded before registered that module.
     * @memberof jQuery.flatfindr
     * @method register
     *
     * @public
     * @param  {Object} module the module to be registered
     * @return {Object} jQuery.flatfindr  flatfindr for chaining
     */
    register: function(module) {
      jQuery.flatfindr[module.name] = module;

      return jQuery.flatfindr;
    },



    /**
     * Call the module, resp. its main method fn.
     * If the module does not exist e.g. it was not registered - return
     * else - proceed
     * @memberof jQuery.flatfindr
     * @method add
     *
     * @public
     * @param  {Array} modules   the modules to be called in the document
     * @return {Object} jQuery.flatfindr  flatfindr for chaining
     */
    add: function(modules) {

      modules.forEach(function(module) {
        if (!(module in jQuery.flatfindr)) return;

        module = jQuery.flatfindr[module];

        module.fn.call(
          module,
          window,
          document,
          jQuery,
          module.$view || $(jQuery.flatfindr.VIEW),
          module.option || {});
      });

      return jQuery.flatfindr;
    },



    /**
     * Add reusable value properties to flatfindr.
     *
     * This shti actually led me to introduce flatfindr under jQuery. v1.0 used
     * lots of unnecessary globals which i wanted to wrap up.
     * There might be better ways to handle stuff that 'has to be' written into
     * js scripts server-side, but let's go with this.
     * @memberof jQuery.flatfindr
     * @method with
     *
     * @public
     * @param  {Object} options   an object param containing the shti thats
     *                            needed by the document, e.g zipcodes
     * @return {Object} jQuery.flatfindr  flatfindr for chaining
     */
    with: function(options) {
      for (var option in options)
        jQuery.flatfindr[option] = options[option];

      return jQuery.flatfindr;
    }
  }
});
