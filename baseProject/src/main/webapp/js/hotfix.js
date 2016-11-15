+(function(window, document, $) {

	// Add stuff between lines..
	// -----------------------------------------------------------------------

	// Example on sort function.. just override existing func.
	// Not ultimately sure if it's working.

	$('#modus').off('change').on(
			'change',
			function() {
				// determine sort modus (by which attribute, asc/desc)
				var sortmode = $('#modus').find(":selected").val();

				// only start the process if a modus has been selected
				if (sortmode.length > 0) {
					var attname;

					// determine which variable we pass to the sort function
					if (sortmode == "price_asc" || sortmode == "price_desc")
						attname = 'data-price';
					else if (sortmode == "moveIn_asc"
							|| sortmode == "moveIn_desc")
						attname = 'data-moveIn';
					else
						attname = 'data-age';

					// copying divs into an array which we're going to sort
					var divsbucket = new Array();
					var divsbucketPremium = new Array();
					var divslist = $('li.resultAd');
					var divslistPremium = $('li.resultPremiumAd');
					var divlength = divslist.length;
					var divlengthPremium = divslistPremium.length;
					for (a = 0; a < divlength; a++) {
						divsbucket[a] = new Array();
						divsbucket[a][0] =
							attname.match('price') ?
							parseInt(divslist[a].getAttribute(attname), 10) :
							divslist[a].getAttribute(attname);
						divsbucket[a][1] = divslist[a];
						divslist[a].remove();
					}
					for (b = 0; b < divlengthPremium; b++) {
						divsbucketPremium[b] = new Array();
						divsbucketPremium[b][0] =
							attname.match('price') ?
							parseInt(divslistPremium[b].getAttribute(attname), 10) :
							divslistPremium[b].getAttribute(attname);
						divsbucketPremium[b][1] = divslistPremium[b];
						divslistPremium[b].remove();
					}

					// sort the array
					divsbucket.sort(function(a, b) {
						if (a[0] == b[0])
							return 0;
						else if (a[0] > b[0])
							return 1;
						else
							return -1;
					});

					// sort the array with premium advertisements
					divsbucketPremium.sort(function(a, b) {
						if (a[0] == b[0])
							return 0;
						else if (a[0] > b[0])
							return 1;
						else
							return -1;
					});

					// invert sorted array for certain sort options
					if (sortmode == "price_desc" || sortmode == "moveIn_asc"
							|| sortmode == "dateAge_asc") {
						divsbucket.reverse();
						divsbucketPremium.reverse();
					}

					// insert sorted divs into document again
					for (b = 0; b < divlengthPremium; b++)
						$("#resultsDiv").append($(divsbucketPremium[b][1]));
					for (a = 0; a < divlength; a++)
						$("#resultsDiv").append($(divsbucket[a][1]));
				}

			});

	// -----------------------------------------------------------------------
	// Reference to this file in whatever jsp you'd like to... at the bottom
	// just before the closing body tag. like so:
	// <script src="js/hotfix.js"></script>
	// </body>

})(window, document, jQuery);
