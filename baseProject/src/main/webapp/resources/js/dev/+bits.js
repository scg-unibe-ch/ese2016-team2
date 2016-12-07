/**
 * @name bits
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.bits
 *
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @return {Object}          public methods
 */
jQuery.flatfindr.bits = function (window, document, $) {


  /**
   *
   * @type {Object} pub   public methods
   */
  var pub = {


    /**
     * @memberof jQuery.flatfindr.bits
     * @method addDatepicker
     *
     * @public
     * @param {Object} datepicker  element and options
     */
    addDatepicker: function (item) {

      $datepicker =
        $(item.selector).datepicker({
          altField: item.altfield,
          dateFormat: item.format
        });

      if (item.unset)
        $datepicker.datepicker('setDate', null);
    },


    /**
     *
     * @memberof jQuery.flatfindr.bits
     * @method addAutoloc
     *
     * @public
     * @param {String} selector  element selector
     */
    addAutoloc: function (selector) {

      $(selector).autocomplete({
        minLength : 2,
        enabled : true,
        autoFocus : true,
        source : $.flatfindr.ZIP_CODES
      });
    },



    /**
     * We pass a place: If it is "messages", the function returns the string
     * for the inbox; if it is anything else, it returns the string for the header.
     *
     * @memberof jQuery.flatfindr.message
     * @method unreadMessages
     *
     * @public
     */
    unreadMessages: function (place) {
    	$.get("/profile/unread", function(data){
    		var message;
    		if(place == "messages")
    			message = "Inbox";
    		else
    			message = "Messages";
    		if(data > 0)
    			message += " (" + data + ")";
    		if(place == "messages")
    			$("#inbox").html(message);
    		else {
    			$("#messageLink").html(message);
          $('<a href="/profile/messages" title="New mail ('+ data +')">'+
            '<span class="fa fa-envelope fa-2x" aria-hidden="true"></span>'+
            '<span id="icon-new-mail">'+ data +'</span>'+
          '</a>').appendTo('#icons-bar');
        }
    	});
    }

  };


  return pub;

}(window, document, jQuery);
