
$(document).ready(function() {

	$(function() {
	    var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
	    $('html').css({'background': 'url(includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
	    	'-webkit-background-size':'cover',
	    	'-moz-background-size':'cover',
	    	'-o-background-size':'cover',
	    	'background-size':'cover'});
	   });


}); 
 