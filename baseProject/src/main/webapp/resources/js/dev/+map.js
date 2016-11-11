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
    console.log(window.geoc);

    // @Jerome: just testing
    $('#js-map').click(function() {
      // open map;
      $('.map').css('height', '500px');

      // NB: I ll do that differently later. Just for testing: I call the map
      // again after parent element has some height, so the map gets rendered
      // properly.
      window.initMap();
    });

    // @Jerome
    // NB: Just testing and familiarizing.
    // Geojson, http://geojson.org/ or rather
    // http://geojson.org/geojson-spec.html
    // .... coool.... works.
    // NB: first link shows an even simpler example.
    var geo = {
    	"type": "FeatureCollection",
    	"features": [{
    		"type": "Feature",
    		"geometry": {
    			"type": "Point",
    			"coordinates": [102.0, 0.5]
    		},
    		"properties": {
    			"prop0": "value0"
    		}
    	},

      //@Jerome: added this one...
      {
    		"type": "Feature",
    		"geometry": {
    			"type": "Point",
    			"coordinates": [202.0, 10.5]
    		},
    		"properties": {
    			"prop0": "value0"
    		}
    	},

      //... and this one (beautiful place to settle.. )
      {
    		"type": "Feature",
    		"geometry": {
    			"type": "Point",
    			"coordinates": [52.0, 60.5]
    		},
    		"properties": {
    			"prop0": "value0"
    		}
    	},

      //... and one more
      {
    		"type": "Feature",
    		"geometry": {
    			"type": "LineString",
    			"coordinates": [
    				[102.0, 0.0],
    				[103.0, 1.0],
    				[104.0, 0.0],
    				[105.0, 1.0]
    			]
    		},
    		"properties": {
    			"prop0": "value0",
    			"prop1": 0.0
    		}
    	}, {
    		"type": "Feature",
    		"geometry": {
    			"type": "Polygon",
    			"coordinates": [
    				[
    					[100.0, 0.0],
    					[101.0, 0.0],
    					[101.0, 1.0],
    					[100.0, 1.0],
    					[100.0, 0.0]
    				]
    			]
    		},
    		"properties": {
    			"prop0": "value0",
    			"prop1": {
    				"this": "that"
    			}
    		}
    	}]
    };

    var geocoder;
    var map;
    var georesults = [];

    function code(addresses) {
      geocoder = new google.maps.Geocoder();

      addresses.forEach(function(address) {
        geocoder.geocode( address, function(results, status) {
          if (status == 'OK') {
            console.log(results);
          } else {
            console.log('Geocode was not successful for the following reason: ' + status);
          }
        });
      });
    }


    // @Jerome
    // Has to be global, i guess. That's why window.blah..
    // Well, maybe only because the callback param in the deferred script of
    // bottom.jsp is set globaly.
    // TODO: Try to call the callback in another namespace by altering the
    // callback param... tomorrow.
    window.initMap = function() {
      map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: new google.maps.LatLng(2.8,-187.3),
        mapTypeId: 'terrain'
      });

      // Create a <script> tag and set the USGS URL as the source.
      var script = document.createElement('script');
      // This example uses a local copy of the GeoJSON stored at
      // http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.geojsonp

      // @Jerome
      // Calling 'callback' function right here because we just declared a
      // json object above.
      script.src = eqfeed_callback(geo);
      document.getElementsByTagName('head')[0].appendChild(script);

      
      code(window.geoc);
    }

    // Loop through the results array and place a marker for each
    // set of coordinates.
    window.eqfeed_callback = function(results) {
      for (var i = 0; i < results.features.length; i++) {
        var coords = results.features[i].geometry.coordinates;
        var latLng = new google.maps.LatLng(coords[1],coords[0]);
        var marker = new google.maps.Marker({
          position: latLng,
          map: map
        });
      }
    }
  }

});
