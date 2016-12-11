/**
 * @name visitors
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.visitors
 *
 * @param  {Object} window   the window as you know it
 * @param  {Object} document the document element
 * @param  {jQuery} $
 * @param  {Object} $view    the view, defaults to the body element
 * @param  {Object} option
 */
jQuery.flatfindr.visitors = function (window, document, $, $view, option) {


  $('.rating').each(function () {
    ratingFor($(this).attr('id'));
  });


  /**
   * Sets up the stars for rating the visitor (user).
   *
   * @private
   * @param  {Number} id     the id of the visitor (user)
   * @param  {Number} rating the rating level
   */
  function stars (id, rating) {
  	document.getElementById(id).innerHTML =
  	"<span onClick=\"$.flatfindr.visitors.rate(" + id + ", 1)\">" + star(1, rating) + "</span>" +
  	"<span onClick=\"$.flatfindr.visitors.rate(" + id + ", 2)\">" + star(2, rating) + "</span>" +
  	"<span onClick=\"$.flatfindr.visitors.rate(" + id + ", 3)\">" + star(3, rating) + "</span>" +
  	"<span onClick=\"$.flatfindr.visitors.rate(" + id + ", 4)\">" + star(4, rating) + "</span>" +
  	"<span onClick=\"$.flatfindr.visitors.rate(" + id + ", 5)\">" + star(5, rating) + "</span>";
  }



  /**
   *
   * @protected
   * @param  {Number} id the id of the visitor (user)
   */
  function ratingFor (id) {
    $.get("/profile/ratingFor?user=" + id, function(data) {
      stars(id, data);
    });
  }


  /**
   * Add star entities to the rating according new rating.
   *
   * @private
   * @param  {Number} starnr the star number
   * @param  {Number} rating the rating level
   * @return {String} html  the star entity  outlined if starnr is smaller than
   *                        old rating - else filled
   */
  function star (starnr, rating) {
      if(starnr <= rating) return "&#9733";
      else return "&#9734";
  }



  /**
   *
   * @type {Object} pub   public methods
   */
  var pub = {

    /**
     * Called from within jsp file, which is really ugly but we left it as is.
     *
     * @memberof jQuery.flatfindr.visitors
     * @method rate
     *
     * @public
     * @param  {Number} id the id of the visitor (user)
     * @param  {Number} rating the rating level of the visitor (user)
     */
    rate: function (id, rating) {
    	$.get("/profile/rateUser?rate=" + id + "&stars=" + rating, function() {
    		ratingFor(id);
    	});
    }

  };

  return pub;
};
