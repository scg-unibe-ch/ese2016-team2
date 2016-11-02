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




    $view = $('[view]'),
    $js_menu_icon = $('#js-menu-icon'),
    $js_menu_form_search_icon = $('#js-menu-form-search-icon'),


    $header_primary = $('.header-primary'),
    $header_primary_form_search = $header_primary.find('.form-search'),
    $first_form_input = $header_primary_form_search.find('input').first();




  /**
   * Adds/emoves useful classnames based on the interaction of the user. These
   * classnames trigger css transition effects on header elements. The $view
   * decides on the presence of its own classnames (states) and on the user input
   * what classnames to add or remove, which in turn results in various changes
   * in the ui.
   */
  $view
    .on('header.toggle', function() {
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

      $js_menu_icon.trigger('headerToggle');
    })
    .on('search.toggle', function() {
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

      $header_primary_form_search.trigger('toggle');
      $js_menu_form_search_icon.trigger('searchToggle');
    });



  /**
   * Dictates change of the first input field based on the $view state.
   *
   * @see $first_form_input
   */
  $header_primary_form_search.on('toggle', function() {
    if ($view.is('.headerPrimaryIn, .headerPrimarySearchIn'))
      $first_form_input.trigger('formIn');
    else
      $first_form_input.trigger('formOut');
  });



  /**
   * Triggers the header
   */
  $js_menu_icon
    .on('click touch', function (e) {
      e.preventDefault();
      $view.trigger('header.toggle');
    })
    .on('headerToggle', function() {
      $(this).toggleClass('fa-bars fa-times');
    });



  /**
   *
   *
   */
  $js_menu_form_search_icon
    .on('click touch', function (e) {
      e.preventDefault();
      $view.trigger('search.toggle');
    })
    .on('searchToggle', function() {
      $(this).toggleClass('fa-search fa-search-minus');
    });



  /**
   *
   *
   */
  $first_form_input
    .on('click touch', function (e) {
      e.preventDefault();
      if ($view.is('.headerPrimarySearchClosed'))
        $view.trigger('search.toggle');
    })
    .on('formIn', function() {
      $(this)
        .focus()
        .attr('placeholder', 'City / ZIP');
    })
    .on('formOut', function() {
      $(this)
        .off('focus')
        .attr('placeholder', 'Find...');
    });

}(window, document, jQuery);
