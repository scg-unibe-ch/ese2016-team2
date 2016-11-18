/**
 *
 * @name place
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.place
 */



jQuery.flatfindr.register({

  name: 'place',


  /**
   * @memberof jQuery.flatfindr.place
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

    // Go to controller take what you need from user
    // save it to a hidden field
    // iterate through it
    // if there is id == x then make "Bookmark Me" to "bookmarked"

    $("#field-city").autocomplete({
      minLength : 2
    });
    $("#field-city").autocomplete({
      source : $.flatfindr.ZIP_CODES
    });
    $("#field-city").autocomplete("option", {
      enabled : true,
      autoFocus : true
    });



    $('#buy').on('click', function() {
      $('.fields-optional-sell').removeClass('js-show');
    });

    $('#rent').on('click', function() {
      $('.fields-optional-sell').addClass('js-show');
    });

    /**
     * Initiate datepicker with no date set.
     */
    $("#visitDay").datepicker({
     altField: '#field-visitDay',
     dateFormat : 'dd-mm-yy'
    }).datepicker('setDate', null);

    $("#moveInDate").datepicker({
      altField: '#field-moveInDate',
      dateFormat : 'dd-mm-yy'
    }).datepicker('setDate', null);

    $("#moveOutDate").datepicker({
      altField: '#field-moveOutDate',
      dateFormat : 'dd-mm-yy'
    }).datepicker('setDate', null);



    $("#addbutton").click(function() {
      // Validates the input for Email Syntax
      function validateForm(text) {
          var positionAt = text.indexOf("@");
          var positionDot = text.lastIndexOf(".");
          if (positionAt< 1 || positionDot<positionAt+2 || positionDot+2>=text.length) {
              return false;
          } else {
            return true;
          }
      }
    });



    $('#addedVisits').mole(function($this) {
      var $throw_off = $this.find('p');
      $this.remove();
      return $throw_off;
    });

    $('#roomDescription')
      .mole(function($this) {
        var $throw_off = $this.val();
        $this.remove();
        return $throw_off;
      })
      .on('focusout', function() {
        $(this).trigger('mole');
      });


    $("#addVisitButton").click(function() {
      var date = $("#field-visitDay").val();
      if(date == ""){
        return;
      }

      var startHour = $("#startHour").val();
      var startMinutes = $("#startMinutes").val();
      var endHour = $("#endHour").val();
      var endMinutes = $("#endMinutes").val();

      if (startHour > endHour) {
        alert("Invalid times. The visit can't end before being started.");
        return;
      } else if (startHour == endHour && startMinutes >= endMinutes) {
        alert("Invalid times. The visit can't end before being started.");
        return;
      }

      var newVisit = date + ";" + startHour + ":" + startMinutes +
        ";" + endHour + ":" + endMinutes;
      var newVisitLabel = date + " " + startHour + ":" + startMinutes +
      " to " + endHour + ":" + endMinutes;

      var index = $("#addedVisits input").length;

      var label = "<p>" + newVisitLabel + "</p>";
      var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";

      $("#addedVisits")
        .append(label + input)
        .trigger('mole');
    });


    // @Jerome: Ugly W/A
    $('input[type=number]').each(function(_, x) {
      if ($(x).val() === '0')
        $(x).val('');
    });
    $('form').on('submit', function(e) {
      e.preventDefault();
      $('input[type=number]').each(function(_, x) {
        var hustensaft = $(x);
        if (hustensaft.val() === '')
          hustensaft.val(0);
      });
      this.submit();
      return false;
    });


    (function () {
      var error_count = $('form').find('.validationErrorText').length;
      var error_text = 'There are '+ error_count +' fields that need to be corrected.';
      if (error_count <= 1)
        error_text = 'There is one field that needs to be corrected.';

      if (error_count > 0)
        $('button[type=submit]').append('<span class="base-color-opposite"> '+ error_text +'</span>');
    }());
  }

});
