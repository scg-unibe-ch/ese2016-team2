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
    $js_sidebar_icon = $('#js-sidebar-icon'),
    $js_message_button = $('#newMsg');




  /**
   * Adds/removes useful classnames based on the interaction of the user. These
   * classnames trigger css transition effects on the sidebar element. The $view
   * decides on the presence of its own classnames (states) and on the user input
   * what classnames to add or remove, which in turn results in ui changes.
   */
  $view
    .on('sidebar.toggle', function() {
      var
        $this = $(this),
        duration = BASE_DURATION + DURATION_BUFFER;

      if ($this.is('.sidebarClosed'))
        $this.unearth('sidebar', duration);
      else
        $this.bury('sidebar', duration);

      if ($this.is('.headerPrimaryOpen'))
        $this.trigger('header.toggle');

      $js_sidebar_icon.trigger('sidebarToggle');
    })
    .on('message.toggle', function() {
      var
        $this = $(this),
        duration = BASE_DURATION + DURATION_BUFFER;

      if ($this.is('.sidebarMessageClosed'))
        $this.unearth('sidebarMessage', duration);
      else
        $this.bury('sidebarMessage', duration);

      $js_message_button.trigger('messageToggle');
    });





  /**
   * Triggers the sidebar
   */
  $js_sidebar_icon
    .on('click touch', function (e) {
      e.preventDefault();
      $view.trigger('sidebar.toggle');
    })
    .on('sidebarToggle', function() {
      $(this).toggleClass('fa-info fa-times');
    });



  $js_message_button
    .on('click touch', function (e) {
      e.preventDefault();
      $view.trigger('message.toggle');
    })
    .on('messageToggle', function() {
      // shall we do something here?
    });


}(window, document, jQuery);
