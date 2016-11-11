/**
 *
 * @name bookmark
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.bookmark
 */



jQuery.flatfindr.register({

  name: 'bookmark',


  /**
   * @memberof jQuery.flatfindr.bookmark
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



    /**
     *
     *
     */
    function attachBookmarkClickHandler() {

      $("#bookmarkButton").click(function() {

        $.post("/bookmark", {
            id : $.flatfindr.shownAdvertisementID,
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
              id : $.flatfindr.shownAdvertisementID,
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
          id : $.flatfindr.shownAdvertisementID,
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

  }

});
