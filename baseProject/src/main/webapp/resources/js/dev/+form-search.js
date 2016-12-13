/**
 * @name search
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.search
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.search = function (window, document, $, $view, option) {


  $.flatfindr.bits.addAutoloc('#city');


  $.flatfindr.bits.addDatepicker({
    selector: '#earliestMoveInDate',
    altfield: '#field-earliestMoveInDate',
    select: function() {
      var
        date = $(this).datepicker('getDate'),
        date_out = date.setDate(date.getDate() + 1),
        date_lout = date.setDate(date.getDate() + 1);

      $('#earliestMoveOutDate, #latestMoveInDate')
        .datepicker('option', 'minDate', new Date(date_out));
      $('#latestMoveOutDate')
        .datepicker('option', 'minDate', new Date(date_lout));
    },
    unset: true
  });


  $.flatfindr.bits.addDatepicker({
    selector: '#latestMoveInDate',
    altfield: '#field-latestMoveInDate',
    unset: true
  });


  $.flatfindr.bits.addDatepicker({
    selector: '#earliestMoveOutDate',
    altfield: '#field-earliestMoveOutDate',
    unset: true
  });


  $.flatfindr.bits.addDatepicker({
    selector: '#latestMoveOutDate',
    altfield: '#field-latestMoveOutDate',
    unset: true
  });



  $('form').on('submit', function(e) {
    e.preventDefault();
    validateType();
    this.submit();
  });



  /**
   * Validate form inputs.
   *
   * @private
   */
  function validateType() {
    var
      room = document.getElementById('room'),
      studio = document.getElementById('studio'),
      house = document.getElementById('house'),
      neither = document.getElementById('neither');

    neither.checked = false;

    if(!room.checked && !studio.checked && !house.checked) {
      neither.checked = true;
    }
  }

};
