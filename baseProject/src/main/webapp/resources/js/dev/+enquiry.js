/**
 *
 * @name enquiry
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.enquiry
 */



jQuery.flatfindr.register({

  name: 'enquiry',


  /**
   * @memberof jQuery.flatfindr.enquiry
   * @method fn
   *
   * @protected
   * @param  {Object}   window   the window as you know it
   * @param  {Object}   document the document element
   * @param  {Object}   $        jQuery
   * @param  {jQuery}   $view    the default or custom view if set
   * @param  {Object}   option   what ever object param if passed
   * @return {Function}          method that sets up simple dom manipulations
   */
  fn: function (window, document, $, $view, option) {


    var
      /**
       * Enquiry button
       */
      $enquiries =
        $('.enquiry').on('click touch', handleEnquiry);



    /**
     *
     * @private
     * @param  {object} e  the click/touch event
     */
    function handleEnquiry(e) {
      e.preventDefault();

      var
        $this = $(this),
        $parent = $this.parent(),
        $confirm = $parent.find('.action-confirm'),
        $cancel = $parent.find('.action-cancel'),
        id = $this.attr('data-id');

      $this.toggleClass('js-confirm');

      $confirm.on('click touch', function(e) {
        e.preventDefault();
        $this.attr('disabled', 'disabled');
        $.get("/profile/enquiries/sendEnquiryForVisit?id=" + id);
        setTimeout(function() {
          $this.toggleClass('js-confirm');
        }, $.flatfindr.BASE_DURATION);
      });

      $cancel.on('click touch', function(e) {
        e.preventDefault();
        $confirm.off('click touch');
        $cancel.off('click touch');
        $this.toggleClass('js-confirm');
      });
    }
  }

});
