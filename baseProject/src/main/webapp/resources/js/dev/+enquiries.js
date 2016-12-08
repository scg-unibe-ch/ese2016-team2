/**
 * @name enquiries
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.enquiries
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.enquiries = function (window, document, $, $view, option) {

  // ... nice
  //              ... indentation.
  $(document)
      .ready(
          function() {
            var rows = $("#enquiryList table tr:gt(0)");
            $(rows).hover(
                function() {
                  $(this).children().css("background-color",
                      "#ececec");
                },
                function() {
                  $(this).children().css("background-color",
                      "white");
                });

            function attachHandlers() {
              $(".acceptButton")
                  .click(
                      function() {
                        var cell = $(this).parent();
                        var id = $(this)
                            .attr("data-id");
                        $
                            .get("/profile/enquiries/acceptEnquiry?id="
                                + id);
                        $(cell)
                            .html(
                                "Accepted <button class='undoButton' data-id='"+ id+ "'>Undo</button>");
                        attachUndoHandler();
                      });
              $(".declineButton")
                  .click(
                      function() {
                        var cell = $(this).parent();
                        var id = $(this)
                            .attr("data-id");
                        $
                            .get("/profile/enquiries/declineEnquiry?id="
                                + id);
                        $(cell)
                            .html(
                                "Declined <button class='undoButton' data-id='"+ id+ "'>Undo</button>");
                        attachUndoHandler();
                      });
            }

            function attachUndoHandler() {
              $(".undoButton")
                  .click(
                      function() {
                        var cell = $(this).parent();
                        var id = $(this)
                            .attr("data-id");
                        $
                            .get("/profile/enquiries/reopenEnquiry?id="
                                + id);
                        $(cell)
                            .html(
                                "<button class='acceptButton' data-id='"+ id + "'>Accept</button><button class='declineButton' data-id='" + id + "'>Decline</button>");
                        attachHandlers();
                      });
            }

            attachHandlers();

          });


    /**
     *
     * @type {Object} pub   public methods
     */
    var pub = {

      /**
       *
       * @memberof jQuery.flatfindr.enquiries
       * @method
       *
       * @public
       */

    };

    return pub;
};
