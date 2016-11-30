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


    // @Jerome quick and dirty.
    $('#field-city').on('input blur', function() {
      var streetVal = $('#field-street').val();
      $('.gllpSearchField').val(streetVal +', '+ this.value);

      $('.ui-menu')
      .off('mouseup')
      .on('mouseup', function () {
        setTimeout(function() {
          var streetVal = $('#field-street').val();
          var cityVal = $('#field-city').val();
          $('.gllpSearchField').val(streetVal +', '+ cityVal);
        }, 100);
      });
    });

    $('#field-street').on('input blur', function() {
      var cityVal = $('#field-city').val();
      $('.gllpSearchField').val(this.value +', '+ cityVal);
    });



    $("#field-city").autocomplete({
      minLength : 2,
      source : $.flatfindr.ZIP_CODES,
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

    if ($.flatfindr.PAGE_NAME === 'editAd') {
      $("#moveInDate").datepicker({
        altField: '#field-moveInDate',
        dateFormat : 'dd-mm-yy'
      });

      $('#moveOutDate').datepicker({
        altField: '#field-moveOutDate',
        dateFormat : 'dd-mm-yy'
      });

      $('#dp-endDate').datepicker({
        altField: '#field-endDate',
        dateFormat : 'dd-mm-yy'
      });
    } else {
      $("#moveInDate").datepicker({
        altField: '#field-moveInDate',
        dateFormat : 'dd-mm-yy'
      }).datepicker('setDate', null);

      $('#moveOutDate').datepicker({
        altField: '#field-moveOutDate',
        dateFormat : 'dd-mm-yy'
      }).datepicker('setDate', null);

      $('#dp-endDate').datepicker({
        altField: '#field-endDate',
        dateFormat : 'dd-mm-yy'
      }).datepicker('setDate', null);
    }


    $('.time-range').on('input', function () {
      var
        id = $(this).attr('id'),
        val = this.value,
        hour = Math.floor(val / 4),
        minute = (val % 4) * 15,
        minute = minute < 10 ? '0'+ minute : minute,
        time = hour +':'+ minute;

      $('#show-'+ id).text(time);

      if (id === 'startTime' ) {
        var $endTime = $('#endTime');
        if (parseInt(val, 10) >= parseInt($endTime.val(), 10)) {
          $endTime.val(this.value);
          $('#show-endTime').text(time);
        }
      } else if (id === 'endTime') {
        var $startTime = $('#startTime');
        if (parseInt(val, 10) <= parseInt($startTime.val(), 10)) {
          $startTime.val(val)
          $('#show-startTime').text(time);
        }
      }
    });



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


    $("#addVisitButton").click(function() {
      var date = $("#field-visitDay").val();
      if (date == "") return;


      var startTime = $('#show-startTime').text();
      var endTime = $('#show-endTime').text();

      //
      // var startHour = $("#startHour").val();
      // var startMinutes = $("#startMinutes").val();
      // var endHour = $("#endHour").val();
      // var endMinutes = $("#endMinutes").val();
      //
      // if (startHour > endHour) {
      //   alert("Invalid times. The visit can't end before being started.");
      //   return;
      // } else if (startHour == endHour && startMinutes >= endMinutes) {
      //   alert("Invalid times. The visit can't end before being started.");
      //   return;
      // }
      //
      var newVisit = date +';'+ startTime +';'+ endTime;
      var newVisitLabel = date +' '+ startTime +' to '+ endTime;
      //
      var index = $("#addedVisits input").length;
      //
      var label = createViewingPreviewElement(newVisitLabel, index);
      var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";

      $("#addedVisits").append(input);
      $('#viewing-preview').append(label);
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




    function createViewingPreviewElement(viewingTime, index) {
      var
        $viewing_time =
          $('<div class="row">' +
            '<div class="tile tile-three-quarter">' +
            '<span>'+ viewingTime +'</span>' +
            '</div>' +
            '<div class="tile tile-quarter">' +
            '<span title="Remove viewing time by double clicking me." data-index="visits['+ index +']"' +
                  'class="fa fa-times fa-2x">' +
            '</span>' +
            '</div>' +
            '</div>');


        $viewing_time
          .find('span[data-index]')
          .on('dblclick', function() {
            var
              $that = $(this),
              viewing_index = $that.attr('data-index');

              $('input[name="'+ viewing_index +'"]').remove();
              $viewing_time.remove();
          })
          .hover(function() {
            $viewing_time.find('span').first().css('color', 'tomato');
          }, function() {
            $viewing_time.find('span').first().removeAttr('style');
          });

      return $viewing_time;
    }


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
