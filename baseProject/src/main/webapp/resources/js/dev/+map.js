/**
 * @name map
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.map
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.map = function (window, document, $, $view, option) {


  var
    /**
     * The container for the google map that can be toggled via menu bar
     *
     * @private
     * @type {jQuery}
     */
    $map = $('#map'),

    /**
     * An array that gets populated with location items to be redered into the
     * the google map.
     *
     * @private
     * @type {Array}
     */
    locs = [],

    /**
     * A helper index for the markers, resp. the location items
     * @type {Number}
     */
    locIdx = 0,


    /**
     * The var that the actual google map is assigned to.
     */
    map;




  $('#js-map').click(toggleMap);



  $('.resultAd, .resultPremiumAd').each(createLocItem);



  /**
   * Toggle map container, call initMap to rerender if opening.
   * @private
   */
  function toggleMap() {
    $map.toggleClass('js-show');
    if ($map.is('.js-show')) window.initMap();
  }



  /**
   * Create a location item to be rendered into map with info content.
   * @private
   */
  function createLocItem() {
    var
      $this = $(this),
      lat = $this.attr('data-lat'),
      lon = $this.attr('data-lon'),
      title = $this.attr('data-title'),
      address = $this.attr('data-address'),
      price = $this.attr('data-price'),
      content = '<h3>'+ title +'</h3><p>'+ address +'</p><p>'+ price +'</p>';

    if (lat && lon)
      locs[locIdx] = [$this, content, parseFloat(lat), parseFloat(lon), locIdx++];
  }



  /**
   * Scroll corresponding ad into view when clicked on marker.
   *
   * @private
   * @param  {Number} scrollTop   scrollTop of the ad
   */
  function animateScrollTop(scrollTop) {
    $('#list')
      .delay(20)
      .animate({scrollTop: scrollTop}, $.flatfindr.BASE_DURATION);
  }



  /**
   * The map callback that does the setup for the google map
   * @private
   */
  window.initMap = function () {
    if (!locs.length) return;

    map = new google.maps.Map(document.getElementById('map'), {
      zoom: 9,
      center: new google.maps.LatLng(locs[0][2],locs[0][3]),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var
      infowindow = new google.maps.InfoWindow({}),
      marker, i;


    for (i = 0; i < locs.length; i++) {
  		marker = new google.maps.Marker({
  			position: new google.maps.LatLng(locs[i][2], locs[i][3]),
  			map: map
  		});

  		google.maps.event.addListener(marker, 'click', (function (marker, i) {
  			return function () {
          var $adinlist = locs[i][0];
  				infowindow.setContent(locs[i][1]);
  				infowindow.open(map, marker);

          $('.resultAd, .resultPremiumAd').attr('style', '');
          $adinlist
            .css('background-color', '#f2f2f2')

          animateScrollTop(
            $('#list').scrollTop() + $adinlist.position().top
          );
  			}
  		})(marker, i));
  	}
  };

};
