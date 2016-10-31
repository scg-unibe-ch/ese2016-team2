+function(window, document, $) {

  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @see _vars.scss $base-duration
     */
    BASE_DURATION = 350,



    /**
     * A bit of an extra delay in ms to prevent janky css transitions.
     *
     * @type {Number}
     */
    DURATION_BUFFER = 10,



    /**
     * A opinionated minium in ms which is used when setTimeout functions should
     * be triggered as soon as possible. It s*cks, might remove it and do it
     * differently.
     *
     * @type {Number}
     * @see #toggleSearch
     */
    DURATION_MIN = 10,



    /**
     * Used to store states of css transitioning elements.
     * searchIn - when search form within primary header is opening/closing
     * menuIn - when primary header is opening/closing
     * menuOpen - when primary header has just opened/closed
     * 0/1 ints are used as booleans
     *
     * @type {Number}
     * @see #toggleMenu
     * @see #toggleSearch
     * @see #ev0_toggleMenu
     * @see #ev0_toggleSearch
     * @see #ev1_toggleSearch
     */
    searchIn = 0,
    menuIn = 0,
    menuOpen = 0,



    $js_menu_icon = $('#js-menu-icon'),
    $js_menu_form_search_icon = $('#js-menu-form-search-icon'),



    $header_primary = $('.header-primary'),
    $header_primary_form_search = $header_primary.find('.form-search'),
    $first_form_input = $header_primary_form_search.find('input').first();



  /**
   * Open or closes the primary header sidebar according to its state.
   * Also changes the icon element (ev) bars <-> cross (times X)
   * State changes are stored in menuIn and menuOpen which are used
   * throughout this script. menuIn when a header primary sidebare state
   * change is triggered, spoken: before it is closing/opening.
   * menuOpen changes spoken: when it has just closed/openend.
   *
   * @see #menuIn
   * @see #menuOpen
   * @see _header.scss .header-primary
   */
  var toggleMenu = function() {
    menuIn = 1 - menuIn;
    $header_primary.toggleClass('js-show');
    $js_menu_icon.toggleClass('fa-bars fa-times');

    setTimeout(function t_menuOpen() {
      menuOpen = 1 - menuOpen;
    }, BASE_DURATION + DURATION_BUFFER);
  };



  /**
   * Add or remove class js-show of the form search within the primary header.
   * js-show holds animated css properties.
   *
   * @see _from.scss .header-primary .form-search
   */
  var toggleSearchForm = function() {
    $header_primary_form_search.toggleClass('js-show');
  };



  /**
   * Alter header primary search form icon (ev).
   * fa-search-minus when it's open, fa-search when it's closed.
   */
  var toggleSearchFormIcon = function() {
    $js_menu_form_search_icon.toggleClass('fa-search fa-search-minus');
  };



  /**
   * [toggleSearch description]
   * @return {[type]} [description]
   */
  var toggleSearch = function() {
    var timeout_duration = (menuIn && menuOpen) ?
      DURATION_MIN : BASE_DURATION + DURATION_BUFFER;

    searchIn = 1 - searchIn;

    if (searchIn)
      $first_form_input
        .focus()
        .attr('placeholder', 'City / ZIP');
    else
      $first_form_input
        .off('focus')
        .attr('placeholder', 'Find...');

    toggleSearchFormIcon();
    setTimeout(function t_toggleSearchForm() {
      toggleSearchForm();
    }, timeout_duration);
  };



  /**
   * Eventhandler that triggers the logic of the primary header visibility.
   * If the search form within the primary header is open and the primary
   * header gets closed, it will also trigger #toggleSearch to close the
   * search form.
   *
   * @see #toggleMenu
   * @see #toggleSearch
   */
  $js_menu_icon.on(
    'click touch',
    function ev0_toggleMenu(e) {
      e.preventDefault();
      toggleMenu();
      if (searchIn) toggleSearch();
  });



  /**
   * Eventhandler that triggers the logic of primary header search form
   * visibility. If the primary header is not open/visible, it will also open
   * the primary header.
   *
   * @see #toggleMenu
   * @see #toggleSearch
   */
  $js_menu_form_search_icon.on(
    'click touch',
    function ev0_toggleSearch(e) {
      e.preventDefault();
      if (!menuIn) toggleMenu();
      toggleSearch();
  });



  /**
   * Eventhandler that triggers the logic of the primary header search form
   * visibility. If the search is closed/not fully visible (the first input is
   * always visible, which is in fact this element (ev)), clicking/touching this
   * element will fully open the search form.
   *
   * @see #toggleSearch
   */
  $first_form_input.on(
    'click touch',
    function ev1_toggleSearch(e) {
      e.preventDefault();
      if (!searchIn) toggleSearch();
  });

}(window, document, jQuery);
