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


  // $('[type=submit]').on('click', function () {
  //   validateType($('.form-search form')[0]);
  // });



  /**
   * Validate form inputs.
   *
   * @private
   * @param  {Object} form the form to be validated
   */
  function validateType(form) {
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
