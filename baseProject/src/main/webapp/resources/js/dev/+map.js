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
     * Plus a helper boolean that tells if the map has already been initilized.
     */
    map,
    map_initd = false,

    // ... Engelberg.
    ch_center = {LAT: 46.8210087, LON: 8.3943264};




  $('#js-map').click(toggleMap);



  $('.resultAd, .resultPremiumAd').each(createLocItem);



  /**
   * Toggle map container, render map only once.
   * @private
   */
  function toggleMap() {
    $map.toggleClass('js-show');
    $('#js-map')
      .toggleClass('fa-map')
      .toggleClass('fa-map-o');

    if ($map.is('.js-show') && !map_initd) {
      setTimeout(function () {
        window.initMap();
        map_initd = true;
      }, $.flatfindr.BASE_DURATION * 2);
    }

    if ($view.is('.headerPrimaryOpen')) {
      $view.trigger('header:toggle');
    }
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
      content = '<h3>'+ title +'</h3><p>'+ address +'</p><p>CHF '+ price +'</p>'+
                '<button type="button" id="zoom'+ locIdx +'" class="action">Close view</button>';

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
   * @private
   * @param  {Object} pos   the coordinate of the target
   */
  function zoomIn(pos) {
    map.setCenter(pos);
    map.setZoom(18);
    map.setMapTypeId('hybrid');
    $(this).text('Distant view');
  }



  /**
   * @private
   * @param  {Object} bounds  the bounds of the map
   */
  function zoomOut(bounds) {
    map.fitBounds(bounds);
    map.setMapTypeId('roadmap');
    $(this).text('Close view');
  }



  /**
   * Toggle map zoom level according to the current map view state, checked by
   * the presence of css class 'zoomIn' (tells: going to zoom in).
   *
   * @private
   * @param  {Object} pos    the coordinate of the target
   * @param  {Object} bounds the bounds of the map
   * @return {Function}      zoomIn if the map is in distant view state
   *                                - else zoomOut
   */
  function toggleZoom(pos, bounds) {
    if ($(this).is('.zoomIn'))
      return zoomIn.call(this, pos);
    return zoomOut.call(this, bounds);
  }



  /**
   * The map function that does the setup for the google map, markers, info
   * window.
   *
   * @private
   */
  window.initMap = function () {
    if (!locs.length) return;

    map = new google.maps.Map(document.getElementById('map'), {
      zoom: 7,
      center: new google.maps.LatLng(ch_center.LAT, ch_center.LON),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var
      infowindow = new google.maps.InfoWindow({}),
      bounds = new google.maps.LatLngBounds(),
      marker, i, pos;


    for (i = 0; i < locs.length; i++) {
      pos = new google.maps.LatLng(locs[i][2], locs[i][3]);
      bounds.extend(pos);

  		marker = new google.maps.Marker({
  			position: pos,
  			map: map
  		});

  		google.maps.event.addListener(marker, 'click', (function (marker, i, pos) {
  			return function () {
          var $adinlist = locs[i][0];

  				infowindow.setContent(locs[i][1]);
  				infowindow.open(map, marker);

          $('.resultAd, .resultPremiumAd').attr('style', '');
          $adinlist.css('background-color', '#f2f2f2');

          if (map.getZoom() > 11)
            $('#zoom'+i)
              .text('Distant view')
              .addClass('zoomIn');

          $('#zoom'+i).on('click', function() {
            $(this).toggleClass('zoomIn');
            toggleZoom.call(this, pos, bounds);
          });

          animateScrollTop(
            $('#list').scrollTop() + $adinlist.position().top
          );
  			}
  		})(marker, i, pos));
  	}

    map.fitBounds(bounds);
  };

};
