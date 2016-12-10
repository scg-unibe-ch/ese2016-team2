/**
 * @name place
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.place
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {jQuery} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.place = function (window, document, $, $view, option) {

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
        $('.gllpSearchButton').trigger('click');
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
      dateFormat : 'dd-mm-yy',
      minDate: new Date(),
      onSelect: function() {
        var
          date = $(this).datepicker('getDate'),
          date_out = date.setDate(date.getDate() + 1);
        $('#moveOutDate')
          .datepicker('option', 'minDate', new Date(date_out));
      }
    }).datepicker('setDate', null)

    $('#moveOutDate').datepicker({
      altField: '#field-moveOutDate',
      dateFormat : 'dd-mm-yy',
      minDate: new Date()
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
    var
      date = $("#field-visitDay").val(),
      $viewing_preview = $('#viewing-preview'),
      $container_scroll = $viewing_preview.parents('.container-scroll'),
      startTime, endTime,
      newVisit, newVisitLabel,
      index,
      label, input;

    if (date == "") return;

    startTime = $('#show-startTime').text();
    endTime = $('#show-endTime').text();
    newVisit = date +';'+ startTime +';'+ endTime;
    newVisitLabel = date +' '+ startTime +' to '+ endTime;
    index = $("#addedVisits input").length;
    label = createViewingPreviewElement(newVisitLabel, index);
    input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";

    $("#addedVisits").append(input);
    $viewing_preview.append(label);
    $container_scroll
      .animate({scrollTop: $('.scroll-wrapper').outerHeight() + 128 - $(window).height()});
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

};
