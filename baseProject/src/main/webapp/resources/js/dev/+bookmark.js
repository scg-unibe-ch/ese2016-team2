/**
 * @name bookmark
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.bookmark
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {jQuery} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.bookmark = function (window, document, $, $view, option) {

  var url =
    $.flatfindr.PAGE_NAME === 'adDescription' ?
    '/bookmark' : '/bookmarkAuction';

  /**
   * @private
   */
  function attachBookmarkClickHandler() {

    $("#bookmarkButton").click(function() {
      console.log('bookmarking');
      $.post(url, {
          id : $.flatfindr.advertisementID,
          screening : false,
          bookmarked : false
        },

        function(data) {

          $('#bookmarkButton').replaceWith(
            $('<a class="right" id="bookmarkedButton" title="Remove bookmark">'
                + '<span class="fa fa-bookmark fa-2x base-color-opposite"></span>'
                + '</a>')
            );

          switch (data) {
            case 0:
              alert("You must be logged in to bookmark ads.");
              break;
            case 1:
              // Something went wrong with the principal object
              alert("Return value 1. Please contact the WebAdmin.");
              break;
            case 3:
              $('#bookmarkButton')
                  .replaceWith(
                      $('<a class="right" id="bookmarkedButton" title="Remove bookmark">'
                          + '<span class="fa fa-bookmark fa-2x base-color-opposite"></span>'
                          + '</a>'));
              break;
            default:
              alert("Default error. Please contact the WebAdmin.");
          }

          attachBookmarkedClickHandler();
        });
    });
  }



  /**
   * @private
   */
  function attachBookmarkedClickHandler() {

    $("#bookmarkedButton").click(
      function() {
        $.post(url, {
            id : $.flatfindr.advertisementID,
            screening : false,
            bookmarked : true
          },

          function(data) {
            $('#bookmarkedButton').replaceWith(
              $('<a class="right" id="bookmarkButton" title="Bookmark property">'
                  + '<span class="fa fa-bookmark fa-2x action-inactive-color"></span>'
                  + '</a>')
            );

            switch (data) {
            case 0:
              alert("You must be logged in to bookmark ads.");
              break;
            case 1:
              // Something went wrong with the principal object
              alert("Return value 1. Please contact the WebAdmin.");
              break;
            case 2:
              $('#bookmarkedButton').replaceWith(
                $('<a class="right" id="bookmarkButton" title="Bookmark property">'
                    + '<span class="fa fa-bookmark fa-2x base-color-opposite"></span>'
                    + '</a>')
              );
              break;
            default:
              alert("Default error. Please contact the WebAdmin.");
            }

            attachBookmarkClickHandler();
          });
      });
  }



  /**
   * IIFE
   * @private
   * @type {Function}
   */
  (function () {
    attachBookmarkClickHandler();
    attachBookmarkedClickHandler();

    $.post(url, {
        id : $.flatfindr.advertisementID,
        screening : true,
        bookmarked : true
      },

      function(data) {

        if (data === 3) {
          $('#bookmarkButton').replaceWith(
            $('<a class="right" id="bookmarkedButton" title="Remove bookmark">'
              + '<span class="fa fa-bookmark fa-2x base-color-opposite"></span>'
              + '</a>')
          );

          attachBookmarkedClickHandler();
        }
      });
  })();

};
