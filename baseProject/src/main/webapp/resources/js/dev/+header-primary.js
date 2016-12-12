/**
 * @name header
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.header
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.header = function (window, document, $, $view, option) {



  /**
   *
   */
  $view
    .on('header:toggle', toggleHeader)
    .on('search:toggle', toggleSearch);





  var
    /**
     * A bit of an extra delay in ms to prevent janky css transitions.
     *
     * @private
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 10,




    /**
     * The menu icon element is the primary access point to the header that holds
     * common menu elements and modules. It also contains an ad search form.
     *
     * The menu icon resides in the top left corner of the window on each page.
     * It is decorated with a three bar icon a.k.a. sandwich icon.
     *
     * @see headerToggle
     */
    $js_menu_icon =
      $('#js-menu-icon')
        .on('click touch', viewDoToggleHeader)
        .on('menuIcon:toggle', toggleMenuIcon),



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
        .on('searchIcon:toggle', toggleSearchIcon),



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
        .on('searchForm:toggle', toggleFirstSearchFormField),



    /**
     * The first input field of the search form within the header. May tell
     * the view to open the search form on click/touch.
     */
    $first_search_form_field =
      $header_primary_form_search
        .find('input').first()
        .on('click touch', viewDoOpenSearch)
        .on('formIn', focusFirstSearchFormField)
        .on('formOut', blurFirstSearchFormField);



  /**
   *
   * @private
   */
  function toggleHeader() {
    var
      $this = $(this),
      duration = $.flatfindr.BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.headerPrimaryClosed')) {
      $this.unearth('headerPrimary', duration);
    } else {
      $this.bury('headerPrimary', duration);
      if ($this.is('.headerPrimarySearchOpen'))
        setTimeout(function() {
          $this.trigger('search:toggle')
        }, duration);
    }

    if ($this.is('.sidebarOpen'))
      $this.trigger('sidebar:toggle');

    $js_menu_icon.trigger('menuIcon:toggle');
  }



  /**
   *
   * @private
   */
  function toggleSearch() {
    var
      $this = $(this),
      duration = $.flatfindr.BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.headerPrimaryClosed.headerPrimarySearchClosed')) {
      $this.trigger('header:toggle')
      setTimeout(function() {
        $this.unearth('headerPrimarySearch', duration);
      }, duration);
    } else {
      if ($this.is('.headerPrimarySearchOpen'))
        $this.bury('headerPrimarySearch', duration);
      else
        $this.unearth('headerPrimarySearch', duration);
    }

    $header_primary_form_search.trigger('searchForm:toggle');
    $js_menu_form_search_icon.trigger('searchIcon:toggle');
  }



  /**
   *
   * @private
   */
  function toggleFirstSearchFormField() {
    if ($view.is('.headerPrimaryIn, .headerPrimarySearchIn'))
      $first_search_form_field.trigger('formIn');
    else
      $first_search_form_field.trigger('formOut');
  }



  /**
   * Tell $view to fire header:toggle
   *
   * @private
   * @param {TouchEvent|MouseEvent} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoToggleHeader(e) {
    e.preventDefault();
    $view.trigger('header:toggle');
  }



  /**
   * Tell $view to fire search:toggle
   *
   * @private
   * @param {TouchEvent|MouseEvent} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoToggleSearch(e) {
    e.preventDefault();
    $view.trigger('search:toggle');
  }



  /**
   * Check if search form is open. If true, tell $view to fire search:toggle
   *
   * @private
   * @param {TouchEvent|MouseEvent} e   the click/touch event
   * @see toggleHeader
   */
  function viewDoOpenSearch(e) {
    e.preventDefault();
    if ($view.is('.headerPrimarySearchClosed'))
      $view.trigger('search:toggle');
  }



  /**
   * Alter between three bars icon and an X (fa-times) depending on state
   * of header element.
   * Shows fa-bars when header open, fa-times when header closed.
   *
   * @private
   */
  function toggleMenuIcon() {
    $(this).toggleClass('fa-bars fa-times');
  }



  /**
   * Alter between search icon and search icon with a minus depending on state
   * of search form element.
   * Shows fa-search when serach form open, else fa-search minus.
   *
   * @private
   */
  function toggleSearchIcon() {
    $(this).toggleClass('fa-search fa-search-minus');
  }



  /**
   * Focus first form input and suggest user to input locality properties.
   *
   * @private
   */
  function focusFirstSearchFormField() {
    $(this).focus().attr('placeholder', 'City / ZIP');
  }



  /**
   * Unfocus first form input and replace placeholder with a more general hint.
   *
   * @private
   */
  function blurFirstSearchFormField() {
    $(this).off('focus').attr('placeholder', 'Find...');
  }

};
