 var tag = document.createElement('script');
  tag.id = 'iframe-demo';
  tag.src = 'https://www.youtube.com/iframe_api';
  var firstScriptTag = document.getElementsByTagName('script')[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

  var player;
  function onYouTubeIframeAPIReady() {
    player = new YT.Player('videoEmbed', {
        events: {
          'onReady': onPlayerReady,
          'onStateChange': onPlayerStateChange
        }
    });
  }
  function onPlayerReady(event) {
    document.getElementById('videoEmbed').style.borderColor = '#FF6D00';
  }

function loadCertificate() {
window.location.href = 'certifyethics.jsp';
}

  function changeBorderColor(playerStatus) {
    var color;
    if (playerStatus == -1) {
      color = "#37474F"; // unstarted = gray    
      
      $(".certifyLink").text("Please watch the training video above. Your Declaration Certificate will be ready once completed.");      
    } else if (playerStatus == 0) {
      color = "#FFFF00"; // ended = yellow      
      $(".certifyLink").text("Your Declaration Certificate is Ready! Click HERE.");      
      $(".certifyLink").attr("onclick","loadCertificate();");
	  $(".certifyLink").removeClass("disabled");
      $(".certifyLink").removeClass("btn-info").addClass("btn-success");
    } else if (playerStatus == 1) {
      color = "#33691E"; // playing = green
      
      $(".certifyLink").text("Training video PLAYING. Your Declaration Certificate will be ready once completed.");
    } else if (playerStatus == 2) {
      color = "#DD2C00"; // paused = red      ;
      $(".certifyLink").text("Training video PAUSED....");
    } else if (playerStatus == 3) {
      color = "#AA00FF"; // buffering = purple
      $(".certifyLink").text("Loading....");     
    } else if (playerStatus == 5) {
      color = "#FF6DOO"; // video cued = orange     
      $(".certifyLink").text("Video Ready!");
    }
    if (color) {
      document.getElementById('videoEmbed').style.borderColor = color;
    }
  }
  function onPlayerStateChange(event) {
    changeBorderColor(event.data);
  }