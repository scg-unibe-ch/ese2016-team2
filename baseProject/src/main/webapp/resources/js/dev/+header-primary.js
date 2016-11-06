/**
 * See <a href="http://jquery.com">http://jquery.com</a>.
 * @name $
 * @class
 * See the jQuery Library  (<a href="http://jquery.com">http://jquery.com</a>) for full details.  This just
 * documents the function and classes that are added to jQuery by this plug-in.
 */


/**
 * @name flatfindr
 * @namespace
 */
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.header = function (window, document, $) {

  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @constant
     */
    BASE_DURATION = 350,



    /**
     * A bit of an extra delay in ms to prevent janky css transitions.
     *
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 10,



    /**
     * The body element.
     * The view attribute is only owned by the body element of each page. It is
     * therefore the only element that ownes the classes that trigger css
     * property alterations. In other words, the body and its classes control
     * changes in the interface.
     *
     * @listens event:header.toggle
     * @listens event:search.toggle
     * @listens event:sidebar.toggle
     * @fires header.toggle
     * @fires search.toggle
     * @fires sidebar.toggle
     * @see {@link headerToggle}
     * @see searchToggle
     * @see {@link _sidebar.js}
     */
    $view =
      $('[view]')
        .on('header.toggle', toggleHeader)
        .on('search.toggle', toggleSearch),



    /**
     * The menu icon element is the primary access point to the header that holds
     * common menu elements and modules. It also contains an ad search form.
     *
     * The menu icon resides in the top left corner of the window on each page.
     * It is decoratied with a three bar icon a.k.a. sandwich icon.
     *
     * @listens event:click
     * @listens event:touch
     * @listens event:menuIcon.toggle
     * @see headerToggle
     */
    $js_menu_icon =
      $('#js-menu-icon')
        .on('click touch', viewDoToggleHeader)
        .on('menuIcon.toggle', toggleMenuIcon),



    /**
     * The search icon is primarily access point to the search form within the
     * header. It also acts as the secondary access point to the header when the
     * latter is in a closed state.
     *
     * @see toggleSearch
     */
    $js_menu_form_search_icon =
      $('#js-menu-form-search-icon')
        .on('click touch', viewDoToggleSearch)
        .on('searchIcon.toggle', toggleSearchIcon),



    /**
     * The primary header element, which is contained by each page and always
     * accessible by the menu icon element and the search icon element.
     */
    $header_primary = $('.header-primary'),



    /**
     * The search form within the header element.
     */
    $header_primary_form_search =
      $header_primary
        .find('.form-search')
        .on('searchForm.toggle', toggleFirstFormInput),



    /**
     * The first input field of the search form within the header.
     *
     */
    $first_form_input =
      $header_primary_form_search
        .find('input').first()
        .on('click touch', viewDoOpenSearch)
        .on('formIn', focusFirstFormInput)
        .on('formOut', unFocusFirstFormInput);



  /**
   * Does something
   */
  function toggleHeader() {
    var
      $this = $(this),
      duration = BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.headerPrimaryClosed')) {
      $this.unearth('headerPrimary', duration);
    } else {
      $this.bury('headerPrimary', duration);
      if ($this.is('.headerPrimarySearchOpen'))
        setTimeout(function() {
          $this.trigger('search.toggle')
        }, duration);
    }

    if ($this.is('.sidebarOpen'))
      $this.trigger('sidebar.toggle');

    $js_menu_icon.trigger('menuIcon.toggle');
  }



  /**
   * does something else
   */
  function toggleSearch() {
    var
      $this = $(this),
      duration = BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.headerPrimaryClosed.headerPrimarySearchClosed')) {
      $this.trigger('header.toggle')
      setTimeout(function() {
        $this.unearth('headerPrimarySearch', duration);
      }, duration);
    } else {
      if ($this.is('.headerPrimarySearchOpen'))
        $this.bury('headerPrimarySearch', duration);
      else
        $this.unearth('headerPrimarySearch', duration);
    }

    $header_primary_form_search.trigger('searchForm.toggle');
    $js_menu_form_search_icon.trigger('searchIcon.toggle');
  }



  /**
   * do something different
   */
  function toggleFirstFormInput() {
    if ($view.is('.headerPrimaryIn, .headerPrimarySearchIn'))
      $first_form_input.trigger('formIn');
    else
      $first_form_input.trigger('formOut');
  }



  /**
   * Tell $view to fire header.toggle
   *
   * @param {object} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoToggleHeader(e) {
    e.preventDefault();
    $view.trigger('header.toggle');
  }



  /**
   * Tell $view to fire search.toggle
   *
   * @param {object} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoToggleSearch(e) {
    e.preventDefault();
    $view.trigger('search.toggle');
  }



  /**
   * Check if search form is open. If true, tell $view to fire search.toggle
   *
   * @param {object} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoOpenSearch(e) {
    e.preventDefault();
    if ($view.is('.headerPrimarySearchClosed'))
      $view.trigger('search.toggle');
  }



  /**
   * Alter between three bars icon and an X (fa-times) depending on state
   * of header element.
   * Shows fa-bars when header open, fa-times when header closed.
   */
  function toggleMenuIcon() {
    $(this).toggleClass('fa-bars fa-times');
  }



  /**
   * Alter between search icon and search icon with a minus depending on state
   * of search form element.
   * Shows fa-search when serach form open, else fa-search minus.
   */
  function toggleSearchIcon() {
    $(this).toggleClass('fa-search fa-search-minus');
  }



  /**
   * Focus first form input and suggest user to input locality properties.
   */
  function focusFirstFormInput() {
    $(this).focus().attr('placeholder', 'City / ZIP');
  }



  /**
   * Unfocus first form input and replace placeholder with a more general hint.
   */
  function unFocusFirstFormInput() {
    $(this).off('focus').attr('placeholder', 'Find...');
  }

};

flatfindr.header(window, document, jQuery);


console.log(window);
