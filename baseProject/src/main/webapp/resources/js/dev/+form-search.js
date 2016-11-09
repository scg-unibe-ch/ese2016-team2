// Should already exist.
var flatfindr = flatfindr || {};



/**
 * pseudo namespace for docs
 * @param  {object} window
 * @param  {object} document the document element
 * @param  {object} $        jQuery
 * @param  {object} jsp      globals passed in jsp
 * @namespace
 * @memberOf flatfindr
 */
flatfindr.search = function (window, document, $, jsp) {

  var
    /**
     * The base duration in ms that corresponds to the one set in _vars.scss
     *
     * @type {Number}
     * @constant
     */
    BASE_DURATION = 350,



    /**
     * An opinionated bit of an extra delay. (usability specific)
     *
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 50,


    /**
     * The scrollable container.
     *
     * @type {object}
     */
    $container_scroll = $('.form-search .container-scroll');



  /**
   * All input fields within the search form scrollable container.
   */
  $('.form-search .container-scroll input')
    .focus(alignInputToTop);



  /**
   * All checkboxes within the search form scrollable container.
   */
  $('.form-search .container-scroll label')
    .on('click', alignInputToTop);



  /**
   * The clear button.
   */
  $('.form-search button[type=reset]')
    .on('click', alignTop);



  /**
   * Align the focused or cliced element to top of the visible part of the
   * scrollable $container_scroll.
   * @param  {object} e the event object click or touch.
   */
  function alignInputToTop() {
    var
      $this = $(this),
      offset_mod = 0;

    if ($this.is('.js-has-label')) {
      offset_mod =
        $this
          .parents('.row')
          .first()
          .find('label')
          .outerHeight();
    }

    animateScrollTop(
      $container_scroll.scrollTop() + $(this).position().top - offset_mod
    );
  }



  /**
   * Align $container_scroll to top, so scrollTop position is 0.
   * @param  {object} e the event object click or touch.
   */
  function alignTop() {
    animateScrollTop(0);
  }



  /**
   * Animate the change of the scrollTop position of the scrollable
   * element $container_scroll.
   * @param  {number} scrollTop the new scroll position to be animated to.
   */
  function animateScrollTop(scrollTop) {
    $container_scroll
      .delay(DURATION_BUFFER)
      .animate({scrollTop: scrollTop}, BASE_DURATION);
  }



  // @Jerome
  // TODO: Clean the closet !!
  // ==========================================================================
  $("#city").autocomplete({
    minLength : 2,
    enabled : true,
    autoFocus : true,
    source : jsp.zipcodes
  });

  // @Jerome
  // TODO: Do this in a beforeSubmit handler.
  // var price = document.getElementById('prizeInput');
  // var radius = document.getElementById('radiusInput');
  // if(price.value == null || price.value == "" || price.value == "0")
  //   price.value = "500";
  // if(radius.value == null || radius.value == "" || radius.value == "0")
  //   radius.value = "5";

  $("#earliestMoveInDate").datepicker({
    altField: '#field-earliestMoveInDate',
    dateFormat : 'dd-mm-yy'
  }).datepicker('setDate', null);
  $("#latestMoveInDate").datepicker({
    altField: '#field-latestMoveInDate',
    dateFormat : 'dd-mm-yy'
  }).datepicker('setDate', null);
  $("#earliestMoveOutDate").datepicker({
    altField: '#field-earliestMoveOutDate',
    dateFormat : 'dd-mm-yy'
  }).datepicker('setDate', null);
  $("#latestMoveOutDate").datepicker({
    altField: '#field-latestMoveOutDate',
    dateFormat : 'dd-mm-yy'
  }).datepicker('setDate', null);

  function validateType(form) {
  	var room = document.getElementById('room');
  	var studio = document.getElementById('studio');
  	var house = document.getElementById('house');
  	var neither = document.getElementById('neither');

  	neither.checked = false;

  	if(!room.checked && !studio.checked && !house.checked) {
  		neither.checked = true;
  	}
  }

  $('[type=submit]').click(function () {
    validateType($(this)[0]);
  });

  // @jerome
  // TODO: yes, this is insane. do this with a proper redirect, not js.
  //(function clearEverything() {
    // $('.form-search input:not([type=radio], [name=_room], #room, [name=_studio], #studio, [name=_house], #house)').val(null);
    // $('#room').attr('checked', 'checked');

    //$('#city').focus().attr('placeholder', 'City / ZIP');
  //})();

  // ==========================================================================

};
