/**
 * @name alerts
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.alerts
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {jQuery} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.alerts = function (window, document, $, $view, option) {


  $.flatfindr.bits.addAutoloc('#city');


  $('form').on('submit', function(e) {
    e.preventDefault();
    validateType();
    this.submit();
  });


  $('.deleteButton').on('click', deleteAlert);


  /**
   * Delete previously set alerts.
   * @private
   */
  function deleteAlert() {
    var id = $(this).attr("data-id");
    $.get("/profile/alerts/deleteAlert?id=" + id, function(){
      $("#alertsDiv").load(document.URL + " #alertsDiv");
    });
  }



  /**
   * @private
   */
  function validateType() {

		var room = document.getElementById('room');
		var studio = document.getElementById('studio');
		var house = document.getElementById('house');
		var neither = document.getElementById('neither');
		var r = document.getElementById('r');
		var s = document.getElementById('s');
		var h = document.getElementById('h');
		var ras = document.getElementById('ras');
		var rah = document.getElementById('rah');
		var sah = document.getElementById('sah');
		var all = document.getElementById('all');

    neither.checked = false;
		r.checked = false;
		h.checked = false;
		s.checked = false;
		ras.checked = false;
		rah.checked = false;
		sah.checked = false;
		all.checked = false;

		if(!room.checked && !studio.checked && !house.checked) {
			neither.checked = true;
		}
		else if(room.checked && !studio.checked && !house.checked) {
			r.checked = true;
		}
		else if(!room.checked && studio.checked && !house.checked) {
			s.checked = true;
		}
		else if(!room.checked && !studio.checked && house.checked) {
			h.checked = true;
		}
		else if(room.checked && studio.checked && !house.checked) {
			ras.checked = true;
		}
		else if(room.checked && !studio.checked && house.checked) {
			rah.checked = true;
		}
		else if(!room.checked && studio.checked && house.checked) {
			sah.checked = true;
		}
		else {
			all.checked = true;
		}
	}


};
