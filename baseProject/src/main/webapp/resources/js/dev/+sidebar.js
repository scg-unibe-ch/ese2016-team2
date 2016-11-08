// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.sidebar = function (window, document, $) {

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
     * @listens event:message.toggle
     * @fires header.toggle
     * @fires sidebar.toggle
     * @see toggleSidebar
     * @see toggleMessage
     * @see {@link +header-primary.js}
     */
    $view =
      $('[view]')
        .on('sidebar.toggle', toggleSidebar)
        .on('message.toggle', toggleMessage),



    /**
     * Sidebar icon
     */
    $js_sidebar_icon =
      $('#js-sidebar-icon')
      .on('click touch', viewDoToggleSidebar)
      .on('sidebarToggle', toggleSidebarIcon),




    /**
     * Message button
     */
    $js_message_button =
      $('#newMsg')
        .on('click touch', viewDoToggleMessage)
        .on('messageToggle', function() {
          // silence for now.
        });





  /**
   * Toggle sidebar.
   */
  function toggleSidebar() {
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
  }



  /**
   * Toggle message.
   */
  function toggleMessage() {
    var
      $this = $(this),
      duration = BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.sidebarMessageClosed'))
      $this.unearth('sidebarMessage', duration);
    else
      $this.bury('sidebarMessage', duration);

    $js_message_button.trigger('messageToggle');
  }



  /**
   * Tell $view to fire sidebar.toggle.
   *
   * @param  {object} e the click or touch event
   */
  function viewDoToggleSidebar(e) {
    e && e.preventDefault();
    $view.trigger('sidebar.toggle');
  }



  /**
   * Toggle the sidebar icon
   */
  function toggleSidebarIcon() {
    $(this).toggleClass('fa-info fa-times');
  }



  /**
   * Tell view to fire message.toggle.
   *
   * @param  {object} e the click or touch event
   */
  function viewDoToggleMessage(e) {
    e.preventDefault();
    $view.trigger('message.toggle');
  }



  /**
   * Open sidebar on page load with a slight delay, so user might better
   * understand that the sidebar can be toggled.
   */
  (function () {
    setTimeout(function () {
      $view.trigger('sidebar.toggle');
    }, BASE_DURATION);
  })();

};

flatfindr.sidebar(window, document, $);
