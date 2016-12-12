/**
 * @name sidebar
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.sidebar
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.sidebar = function (window, document, $, $view, option) {


  /**
   *
   */
  $view
    .on('sidebar:toggle', toggleSidebar)
    .on('message:toggle', toggleMessage);



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
   *
   * @private
   */
  function toggleSidebar() {
    var
      $this = $(this),
      duration = $.flatfindr.BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.sidebarClosed'))
      $this.unearth('sidebar', duration);
    else
      $this.bury('sidebar', duration);

    if ($this.is('.headerPrimaryOpen'))
      $this.trigger('header:toggle');

    $js_sidebar_icon.trigger('sidebarToggle');
  }



  /**
   * Toggle message.
   *
   * @private
   */
  function toggleMessage() {
    var
      $this = $(this),
      duration = $.flatfindr.BASE_DURATION + DURATION_BUFFER;

    if ($this.is('.sidebarMessageClosed'))
      $this.unearth('sidebarMessage', duration);
    else
      $this.bury('sidebarMessage', duration);

    $js_message_button.trigger('messageToggle');
  }



  /**
   * Tell $view to fire sidebar:toggle.
   *
   * @private
   * @param  {object} e the click or touch event
   */
  function viewDoToggleSidebar(e) {
    e && e.preventDefault();
    $view.trigger('sidebar:toggle');
  }



  /**
   * Toggle the sidebar icon
   *
   * @private
   */
  function toggleSidebarIcon() {
    $(this).toggleClass('fa-info fa-times');
  }



  /**
   * Tell view to fire message:toggle.
   *
   * @private
   * @param  {object} e the click or touch event
   */
  function viewDoToggleMessage(e) {
    e.preventDefault();
    $view.trigger('message:toggle');
  }



  /**
   * Open sidebar on page load with a slight delay, so user might better
   * understand that the sidebar can be toggled.
   *
   * IIFE
   * @private
   */
  (function ($view) {
    setTimeout(function () {
      $view.trigger('sidebar:toggle');
    }, $.flatfindr.BASE_DURATION);
  })($view);

};
