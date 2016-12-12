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


  [ 'earliestMoveInDate',
    'earliestMoveOutDate',
    'latestMoveInDate',
    'latestMoveOutDate' ]
    .forEach(function (name) {
      $.flatfindr.bits.addDatepicker({
        selector: '#'+ name,
        altfield: '#field-'+ name,
        format: 'dd-mm-yy',
        unset: true
      });
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
