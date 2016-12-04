<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="/resources/js/prod/${param.js}.js"></script>
<script>

	function onSignIn(googleUser) {
		var profile = googleUser.getBasicProfile();

		var gUser = {
			id_token: googleUser.getAuthResponse().id_token,
			firstName: profile.getGivenName(), // before: firstname
			lastName: profile.getFamilyName(), // before: lastname
			email: profile.getEmail(),
			picture: profile.getImageUrl()
		};


		// Send stuff to controller just like the example below. So, specify
		// the url, e.g '/signup/doesEmailExist' and then specify an obj with
		// key:value pairs ({key1: value, key2: value, ...}) where the keys
		// correspond to the properties of the form that is handled in a
		// controller method. I.e. if the signupForm has a property firstname, the
		// key to be passed to the controller method has to be the same. Of course,
		// types of the values should match too, i guess.

		// EXAMPLE
		// check if email exists - and handle according response (bool)

		$.post('/signup/doesGoogleEmailExist', {
			email: gUser.email
		})
		.done(function (existingEmail) {
			if (existingEmail) {
				console.log('user exists');
			} else {
				$.post('/signup/google', {
					email: gUser.email,
					firstName: gUser.firstName,
					lastName: gUser.lastName
					// password: '654321',
					// Gender: 'MALE',
					// Account: 'normal'
				})
				.done(function (data) {
					console.log('Yepp: ', data);
				})
				.fail(function (data) {
					console.log('Nope: ', data);
				});
			}
		})
		.fail(function (data) {
			console.log(data.responseText);
		});

		// @Jerome
		// The object literal held firstname / lastname not firstName / lastName.
		// But yea, i changed the names to be the same as in signupForm ->
		// firstName / lastName. It would have worked with gUser.firstname/.lastname,
		// though.
	}




	function signOut() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut().then(function () {
			console.log('User signed out.');
		});
	}





	(function ($, pagename) {

		var js = {
			common: function () {
				return $.flatfindr
					.with({
						PAGE_NAME: pagename,
						ZIP_CODES: <c:import url="getzipcodes.jsp" />
					})
					.add(['header']);
			},

			login: function () {




				return $.flatfindr.add([
					'search'
				]);
			},

			signup: function () {
				return $.flatfindr.add([
					'search',
					'signup'
				]);
			},

			register: function () {
				/**
		     * Initiate autocompletion for localities
		     */
		    $("#city").autocomplete({
		      minLength : 2,
		      enabled : true,
		      autoFocus : true,
		      source : $.flatfindr.ZIP_CODES
		    });
			},

			alerts: function () {

				function deleteAlert(button) {
					var id = $(button).attr("data-id");
					$.get("/profile/alerts/deleteAlert?id=" + id, function(){
						$("#alertsDiv").load(document.URL + " #alertsDiv");
					});
				}

				// $(document).ready(function() {
				// 	$("#city").autocomplete({
				// 		minLength : 2
				// 	});
				// 	$("#city").autocomplete({
				// 		source : <c:import url="getzipcodes.jsp" />
				// 	});
				// 	$("#city").autocomplete("option", {
				// 		enabled : true,
				// 		autoFocus : true
				// 	});
				//
				// 	var price = document.getElementById('priceInput');
				// 	var radius = document.getElementById('radiusInput');
				//
				// 	if(price.value == null || price.value == "" || price.value == "0")
				// 		price.value = "500";
				// 	if(radius.value == null || radius.value == "" || radius.value == "0")
				// 		radius.value = "5";
				// });
				//

				return $.flatfindr.add


			},

			updatedProfile: function () {
				return;
			},

			myRooms: function () {
				$('.list-delete-link').on('click touch', function (e) {
					e.preventDefault();

					$(this)
						.parents('.row').first()
						.find('.deletion-confirm')
						.toggleClass('js-confirm');
				});

				$('.action-cancel').on('click touch', function(e) {
					e.preventDefault();

					$(this)
						.parents('.deletion-confirm')
						.removeClass('js-confirm');
				});

				return $.flatfindr.add([
					'search'
				]);
			},

			user: function () {
				return $.flatfindr
					.with({ username: '${user.username}' })
					.add([
						'search',
						'message'
					]);
			},

			editProfile: function () {
				return $.flatfindr
					.with({
						popul: { '#about-me': {
							val: "${currentUser.aboutMe}"
						}}
					})
					.add([
						'imageUpload',
						'populate'
					])
			},

			index: function () {
				return $.flatfindr.add([
					'search',
					'sliderBlender',
					'sliderBlenderCaption'
				]);
			},

			searchAd: function () {
				return $.flatfindr.add([
					'search'
				]);
			},

			results: function () {
				return $.flatfindr.add([
					'filter',
					'map'
				]);
			},

			placeAd: function () {
				return $.flatfindr.add([
					'place',
					'imageUpload'
				]);
			},

			editAd: function () {
				return js.placeAd();
			},

			placeAuction: function () {
				return js.placeAd();
			}

		};

		js.common();
		pagename in js && js[pagename]();

	})(jQuery, '${param.js}');
</script>

<c:choose>
    <c:when test="${param.map=='1'}">
			<script
				async
				defer
				src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAh9mJhrCy-xTWy5b3Niop8QilZAdMh1To&callback=initMap">
			</script>
    </c:when>
    <c:otherwise>
			<c:if test="${param.map=='2'}">
				<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAh9mJhrCy-xTWy5b3Niop8QilZAdMh1To"></script>
				<script>
					/**
					 *
					 * A JQUERY GOOGLE MAPS LATITUDE AND LONGITUDE LOCATION PICKER
					 * version 1.2
					 *
					 * Supports multiple maps. Works on touchscreen. Easy to customize markup and CSS.
					 *
					 * To see a live demo, go to:
					 * http://www.wimagguc.com/projects/jquery-latitude-longitude-picker-gmaps/
					 *
					 * by Richard Dancsi
					 * http://www.wimagguc.com/
					 *
					 */

					(function($) {

					// for ie9 doesn't support debug console >>>
					if (!window.console) window.console = {};
					if (!window.console.log) window.console.log = function () { };
					// ^^^

					$.fn.gMapsLatLonPicker = (function() {

						var _self = this;

						///////////////////////////////////////////////////////////////////////////////////////////////
						// PARAMETERS (MODIFY THIS PART) //////////////////////////////////////////////////////////////
						_self.params = {
							defLat : 0,
							defLng : 0,
							defZoom : 1,
							queryLocationNameWhenLatLngChanges: true,
							queryElevationWhenLatLngChanges: true,
							mapOptions : {
								mapTypeId: google.maps.MapTypeId.ROADMAP,
								mapTypeControl: false,
								disableDoubleClickZoom: true,
								zoomControlOptions: true,
								streetViewControl: false
							},
							strings : {
								markerText : "Drag this Marker",
								error_empty_field : "Couldn't find coordinates for this place",
								error_no_results : "Couldn't find coordinates for this place"
							}
						};


						///////////////////////////////////////////////////////////////////////////////////////////////
						// VARIABLES USED BY THE FUNCTION (DON'T MODIFY THIS PART) ////////////////////////////////////
						_self.vars = {
							ID : null,
							LATLNG : null,
							map : null,
							marker : null,
							geocoder : null
						};

						///////////////////////////////////////////////////////////////////////////////////////////////
						// PRIVATE FUNCTIONS FOR MANIPULATING DATA ////////////////////////////////////////////////////
						var setPosition = function(position) {
							_self.vars.marker.setPosition(position);
							_self.vars.map.panTo(position);

							$(_self.vars.cssID + ".gllpZoom").val( _self.vars.map.getZoom() );
							$(_self.vars.cssID + ".gllpLongitude").val( position.lng() );
							$(_self.vars.cssID + ".gllpLatitude").val( position.lat() );

							$(_self.vars.cssID).trigger("location_changed", $(_self.vars.cssID));

							if (_self.params.queryLocationNameWhenLatLngChanges) {
								getLocationName(position);
							}
							if (_self.params.queryElevationWhenLatLngChanges) {
								getElevation(position);
							}
						};

						// for reverse geocoding
						var getLocationName = function(position) {
							var latlng = new google.maps.LatLng(position.lat(), position.lng());
							_self.vars.geocoder.geocode({'latLng': latlng}, function(results, status) {
								if (status == google.maps.GeocoderStatus.OK && results[1]) {
									$(_self.vars.cssID + ".gllpLocationName").val(results[1].formatted_address);
								} else {
									$(_self.vars.cssID + ".gllpLocationName").val("");
								}
								$(_self.vars.cssID).trigger("location_name_changed", $(_self.vars.cssID));
							});
						};

						// for getting the elevation value for a position
						var getElevation = function(position) {
							var latlng = new google.maps.LatLng(position.lat(), position.lng());

							var locations = [latlng];

							var positionalRequest = { 'locations': locations };

							_self.vars.elevator.getElevationForLocations(positionalRequest, function(results, status) {
								if (status == google.maps.ElevationStatus.OK) {
									if (results[0]) {
										$(_self.vars.cssID + ".gllpElevation").val( results[0].elevation.toFixed(3));
									} else {
										$(_self.vars.cssID + ".gllpElevation").val("");
									}
								} else {
									$(_self.vars.cssID + ".gllpElevation").val("");
								}
								$(_self.vars.cssID).trigger("elevation_changed", $(_self.vars.cssID));
							});
						};

						// search function
						var performSearch = function(string, silent) {
							if (string == "") {
								if (!silent) {
									displayError( _self.params.strings.error_empty_field );
								}
								return;
							}
							_self.vars.geocoder.geocode(
								{"address": string},
								function(results, status) {
									if (status == google.maps.GeocoderStatus.OK) {
										$(_self.vars.cssID + ".gllpZoom").val(11);
										_self.vars.map.setZoom( parseInt($(_self.vars.cssID + ".gllpZoom").val()) );
										setPosition( results[0].geometry.location );
									} else {
										if (!silent) {
											displayError( _self.params.strings.error_no_results );
										}
									}
								}
							);
						};

						// error function
						var displayError = function(message) {
							alert(message);
						};

						///////////////////////////////////////////////////////////////////////////////////////////////
						// PUBLIC FUNCTIONS  //////////////////////////////////////////////////////////////////////////
						var publicfunc = {

							// INITIALIZE MAP ON DIV //////////////////////////////////////////////////////////////////
							init : function(object) {

								if ( !$(object).attr("id") ) {
									if ( $(object).attr("name") ) {
										$(object).attr("id", $(object).attr("name") );
									} else {
										$(object).attr("id", "_MAP_" + Math.ceil(Math.random() * 10000) );
									}
								}

								_self.vars.ID = $(object).attr("id");
								_self.vars.cssID = "#" + _self.vars.ID + " ";

								_self.params.defLat  = $(_self.vars.cssID + ".gllpLatitude").val()  ? $(_self.vars.cssID + ".gllpLatitude").val()		: _self.params.defLat;
								_self.params.defLng  = $(_self.vars.cssID + ".gllpLongitude").val() ? $(_self.vars.cssID + ".gllpLongitude").val()	    : _self.params.defLng;
								_self.params.defZoom = $(_self.vars.cssID + ".gllpZoom").val()      ? parseInt($(_self.vars.cssID + ".gllpZoom").val()) : _self.params.defZoom;

								_self.vars.LATLNG = new google.maps.LatLng(_self.params.defLat, _self.params.defLng);

								_self.vars.MAPOPTIONS		 = _self.params.mapOptions;
								_self.vars.MAPOPTIONS.zoom   = _self.params.defZoom;
								_self.vars.MAPOPTIONS.center = _self.vars.LATLNG;

								_self.vars.map = new google.maps.Map($(_self.vars.cssID + ".gllpMap").get(0), _self.vars.MAPOPTIONS);
								_self.vars.geocoder = new google.maps.Geocoder();
								_self.vars.elevator = new google.maps.ElevationService();

								_self.vars.marker = new google.maps.Marker({
									position: _self.vars.LATLNG,
									map: _self.vars.map,
									title: _self.params.strings.markerText,
									draggable: true
								});

								// Set position on doubleclick
								google.maps.event.addListener(_self.vars.map, 'dblclick', function(event) {
									setPosition(event.latLng);
								});

								// Set position on marker move
								google.maps.event.addListener(_self.vars.marker, 'dragend', function(event) {
									setPosition(_self.vars.marker.position);
								});

								// Set zoom feld's value when user changes zoom on the map
								google.maps.event.addListener(_self.vars.map, 'zoom_changed', function(event) {
									$(_self.vars.cssID + ".gllpZoom").val( _self.vars.map.getZoom() );
									$(_self.vars.cssID).trigger("location_changed", $(_self.vars.cssID));
								});

								// Update location and zoom values based on input field's value
								$(_self.vars.cssID + ".gllpUpdateButton").bind("click", function() {
									var lat = $(_self.vars.cssID + ".gllpLatitude").val();
									var lng = $(_self.vars.cssID + ".gllpLongitude").val();
									var latlng = new google.maps.LatLng(lat, lng);
									_self.vars.map.setZoom( parseInt( $(_self.vars.cssID + ".gllpZoom").val() ) );
									setPosition(latlng);
								});

								// Search function by search button
								$(_self.vars.cssID + ".gllpSearchButton").bind("click", function() {
									performSearch( $(_self.vars.cssID + ".gllpSearchField").val(), false );
								});

								// Search function by gllp_perform_search listener
								$(document).bind("gllp_perform_search", function(event, object) {
									performSearch( $(object).attr('string'), true );
								});

								// Zoom function triggered by gllp_perform_zoom listener
								$(document).bind("gllp_update_fields", function(event) {
									var lat = $(_self.vars.cssID + ".gllpLatitude").val();
									var lng = $(_self.vars.cssID + ".gllpLongitude").val();
									var latlng = new google.maps.LatLng(lat, lng);
									_self.vars.map.setZoom( parseInt( $(_self.vars.cssID + ".gllpZoom").val() ) );
									setPosition(latlng);
								});
							}

						}

						return publicfunc;
					});

					}(jQuery));

					$(document).ready( function() {
						$(".gllpLatlonPicker").each(function() {
							$(document).gMapsLatLonPicker().init( $(this) );
						});
					});

					$(document).bind("location_changed", function(event, object) {
						//console.log("changed: " + $(object).attr('id') );

						$('#ad-lat').val($('#map-lat').val());
						$('#ad-lon').val($('#map-lon').val());
					});

				</script>
			</c:if>
    </c:otherwise>
</c:choose>

<script src="js/hotfix.js"></script>

</body>
</html>
