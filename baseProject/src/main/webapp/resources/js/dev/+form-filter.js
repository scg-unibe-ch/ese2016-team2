/**
 * @name filter
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.filter
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.filter = function (window, document, $, $view, option) {


  var
    /**
     * An opinionated bit of an extra delay. (usability specific)
     *
     * @private
     * @type {Number}
     * @constant
     */
    DURATION_BUFFER = 50,



    /**
     *
     * @private
     * @type {jQuery}
     */
    $form_filter = $('.form-filter form');



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
        unset: false
      });
    });



  $('[type=submit]').click(function () {
    validateType_FilterForm($form_filter[0]);
  });


  $('#modus').on('change', sort_div_attribute);


  $('.js-has-label').hide(0);



  /**
   * Validate form inputs.
   *
   * @private
   * @param  {Object} form the form to be validated
   */
  function validateType_FilterForm(form) {
  	var room = document.getElementById('room');
  	var studio = document.getElementById('studio');
  	var house = document.getElementById('house');
  	var neither = document.getElementById('neither');

  	neither.checked = false;
  	if(!room.checked && !studio.checked && !house.checked) {
  		neither.checked = true;
  	}
  }



  /**
   * This script takes all the resultAd divs and sorts them by a parameter
   * specified by the user. No arguments need to be passed, since the function
   * simply looks up the dropdown selection.
   *
   * @private
   */
  function sort_div_attribute() {
    //determine sort modus (by which attribute, asc/desc)
    var sortmode = $('#modus').find(":selected").val();

    //only start the process if a modus has been selected
    if(sortmode.length > 0) {
    	var attname;

    	//determine which variable we pass to the sort function
  		if(sortmode == "price_asc" || sortmode == "price_desc")
  			attname = 'data-price';
	    else if(sortmode == "moveIn_asc" || sortmode == "moveIn_desc")
			  attname = 'data-moveIn';
	    else
		    attname = 'data-age';

  		//copying divs into an array which we're going to sort
	    var divsbucket = new Array();
	    var divslist = $('li.resultAd');
	    var divlength = divslist.length;
	    for (a = 0; a < divlength; a++) {
  			divsbucket[a] = new Array();
  			divsbucket[a][0] =
          attname.match('price') ?
          parseInt(divslist[a].getAttribute(attname), 10) :
          divslist[a].getAttribute(attname);
  			divsbucket[a][1] = divslist[a];
  			divslist[a].remove();
	    }

  	    //sort the array
  		divsbucket.sort(function(a, b) {
  	    if (a[0] == b[0])
  			return 0;
  	    else if (a[0] > b[0])
  			return 1;
          else
  			return -1;
  		});

  	    //invert sorted array for certain sort options
  		if(sortmode == "price_desc" || sortmode == "moveIn_asc" || sortmode == "dateAge_asc")
  			divsbucket.reverse();

  	    //insert sorted divs into document again
  		for(a = 0; a < divlength; a++)
        $("#resultsDiv").append($(divsbucket[a][1]));
    }
  }



  /**
   * Little hack
   */
  setTimeout(function() {
    $form_filter[0].reset();
    $('.js-has-label').fadeIn($.flatfindr.BASE_DURATION);
  }, $.flatfindr.BASE_DURATION);

};
