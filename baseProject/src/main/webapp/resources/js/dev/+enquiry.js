// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @param  {object} jsp      global object properties passed by jsp
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.enquiry = function (window, document, $) {

  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @constant
     */
    BASE_DURATION = 350,



    /**
     * Enquiry button
     */
    $enquiries =
      $('.enquiry').on('click touch', handleEnquiry);



  /**
   * 
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
      }, BASE_DURATION);
    });

    $cancel.on('click touch', function(e) {
      e.preventDefault();
      $confirm.off('click touch');
      $cancel.off('click touch');
      $this.toggleClass('js-confirm');
    });
  }

};


flatfindr.enquiry(window, document, jQuery);
