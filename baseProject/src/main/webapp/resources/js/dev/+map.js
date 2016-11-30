/**
 *
 * @name map
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.map
 */



jQuery.flatfindr.register({

  name: 'map',


  /**
   * @memberof jQuery.flatfindr.map
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


    // @Jerome: just testing
    $('#js-map').click(function() {
      // open map;
      $map = $('#map');

      $map.toggleClass('js-show');

      if ($map.is('.js-show')) window.initMap();
    });

    var locs = [];
    var locIdx = 0;

    $('.resultAd, .resultPremiumAd').each(function(i, x) {
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
    });


    var map;

    // @Jerome
    window.initMap = function () {
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
    }

    function animateScrollTop(scrollTop) {
      $('#list')
        .delay(20)
        .animate({scrollTop: scrollTop}, $.flatfindr.BASE_DURATION);
    }
  }

});
