/**
 *
 * @name imageUpload
 * @memberof jQuery.flatfindr
 * @namespace jQuery.flatfindr.imageUpload
 */



jQuery.flatfindr.register({

  name: 'imageUpload',


  /**
   * @memberof jQuery.flatfindr.imageUpload
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

    var
      /**
       * Width of the image preview element set.
       * @private
       * @type {Number}
       */
      PREVIEW_WIDTH = 320,


      /**
       * Height of the image preview element set.
       * @private
       * @type {Number}
       */
      PREVIEW_HEIGHT = 128,


      /**
       *
       * @type {String}
       */
      PAGE_NAME = $.flatfindr.PAGE_NAME,


      /**
       * The container of the image preview element sets. In other words, the
       * container the previews get appended to.
       * @private
       * @type {jQuery}
       */
      $image_preview = $('#image-preview');



    /**
     *
     * @private
     */
    function retrieveImages() {
      $.post('/profile/'+ PAGE_NAME +'/getUploadedPictures', function(data) {
        $.each(data, function(_, image) {
          $image_preview.prepend(createPreviewElement(image));
        });

        $image_preview
          .find('.action-delete')
          .on('dblclick', deleteImage);
      });
    }



    /**
     *
     * @private
     * @param  {MouseEvent|TouchEvent} e the mouse or touch event
     */
    function deleteImage(e) {
      e.preventDefault();

      var
        $that = $(this),
        $parent = $that.parent(),
        deleteUrl, adId, pictureId;


      if (PAGE_NAME === 'editAd') {
        adId = $parent.attr('data-ad-id');
        pictureId = $parent.attr('data-picture-id');
        $.post(
          "/profile/editAd/deletePictureFromAd",
          {adId:adId, pictureId:pictureId}, function() {
    			  $parent.remove();
    		});
      } else {
        deleteUrl = $parent.attr('data-url');
        $.post(
          '/profile/'+ PAGE_NAME +'/deletePicture',
          {url : deleteUrl}, function() {
            $parent.remove();
        });
      }
    }





    /**
     *
     * @private
     * @param  {Object} image the image file object or a canvas element
     *                        (canvas preview is not used at the moment)
     * @return {jQuery}       the preview element set appended to the preview
     *                        container
     */
    function createPreviewElement(image) {
      if (image.url && image.name)
        _image = '<img src="'+ image.url +'" alt="'+ image.name +'" />';
      else if (image.preview)
        _image = image.preview;
      else return;

      return $('<div data-url="'+ image.url +'"' +
               'style="height:'+ PREVIEW_HEIGHT +'px" ' +
               'class="image-preview-wrap">' +
               '<span title="Remove image by double clicking it." class="fa fa-times fa-2x action-delete"></span></div>')
               .append(_image);
    }




    /**
     *
     * IIFE
     * @private
     */
    $(function() {
    	$('#field-pictures').fileupload({

    		url: '/profile/'+ PAGE_NAME +'/uploadPictures',
    		dataType: 'json',
    		acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        disableImageResize: true,
        // Canvas not used, therefore not needed
        // previewMaxWidth: PREVIEW_WIDTH,
        // previewMaxHeight: PREVIEW_HEIGHT,
        previewCrop: false

    	}).bind('fileuploadprocessalways', function(e, data) {
          // var file = data.files[data.index];

          // if (file.preview)
            // optionally do something

          // if (file.error)
            // do something explenatory in $image-preview

      }).bind('fileuploaddone', function(e, data) {

        var
          files = $.parseJSON(data.result).files,
          file = files[files.length-1],
          path;


        if (PAGE_NAME === 'editAd' || PAGE_NAME === 'placeAd') {
          $image_preview
            .prepend(createPreviewElement(file))
            .find('.action-delete')
            .on('dblclick', deleteImage);
        } else {
          path = file.url;

          $('#picture').val(path);
          $('#profile-picture').attr('src', path);
        }

      });

      retrieveImages();
    });
  }

});
