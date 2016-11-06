// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @param  {object} jsp  jsp passed by jsp
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.bookmark = function (window, document, $, jsp) {



  /**
   *
   *
   */
  function attachBookmarkClickHandler() {

    $("#bookmarkButton").click(function() {

      $.post("/bookmark", {
          id : jsp.shownAdvertisementID,
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
   *
   */
  function attachBookmarkedClickHandler() {

    $("#bookmarkedButton").click(
      function() {
        $.post("/bookmark", {
            id : jsp.shownAdvertisementID,
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
   *
   */
  (function () {
    attachBookmarkClickHandler();
    attachBookmarkedClickHandler();

    $.post("/bookmark", {
        id : jsp.shownAdvertisementID,
        screening : true,
        bookmarked : true
      },

      function(data) {
        if (data == 3) {
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
