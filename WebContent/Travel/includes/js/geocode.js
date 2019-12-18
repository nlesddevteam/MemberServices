//NLESD Travel Claim Distance Calculator - shortest driving distance
	var location1;
	var location2;	
	var address1;
	var address2;	
	var latlng;
	var geocoder;	
	var map;	
	var distance;
	
	
	function showit(target){
		document.getElementById(target).style.display = 'block';
		
		}
		function hideit(target){
		document.getElementById(target).style.display = 'none';
		
		}
	
	
	// finds the coordinates for the two locations and calls the showMap() function
	function initialize()
	{
		
				
		geocoder = new google.maps.Geocoder(); // creating a new geocode object
		
		// getting the two address values
		
		if(document.getElementById('useForm1').checked) {
		
		
		if (document.getElementById("streetnum1").value == "") {
			document.getElementById("errDisplay").innerHTML = "Please enter a starting street NUMBER.";
			document.getElementById("streetnum1").focus();				
			$('#myModal').modal('show');	
			
			return;
		}
		
		if (document.getElementById("addressa").value == "") {
			document.getElementById("errDisplay").innerHTML = "Please enter a starting street NAME.";
			document.getElementById("addressa").focus();
			$('#myModal').modal('show');	
			return;
		}
		
		if (document.getElementById("town1").value == "") {
			document.getElementById("town1").focus();			
			document.getElementById("errDisplay").innerHTML = "Please enter a starting TOWN/CITY.";
			$('#myModal').modal('show');	
			return;
		}
		
		
		if (document.getElementById("streetnum2").value == "") {
			document.getElementById("streetnum2").focus();
			document.getElementById("errDisplay").innerHTML = "Please enter a destination street NUMBER.";			
			$('#myModal').modal('show');	
			return;
		}
		
		
		
		if (document.getElementById("addressb").value == "") {
			document.getElementById("addressb").focus();
			document.getElementById("errDisplay").innerHTML = "Please enter a destination street NAME.";			
			$('#myModal').modal('show');	
			return;
		}
		
		
		
		
		if (document.getElementById("town2").value == "") {
			document.getElementById("town2").focus();
			document.getElementById("errDisplay").innerHTML = "Please enter a destination TOWN/CITY.";			
			$('#myModal').modal('show');	
			return;
		}
		
		
		
		address1 = document.getElementById("streetnum1").value + " " + document.getElementById("addressa").value + ", " + document.getElementById("town1").value + ", " +document.getElementById("prov1").value;
		address2 = document.getElementById("streetnum2").value + " " + document.getElementById("addressb").value + ", " + document.getElementById("town2").value + ", " +document.getElementById("prov2").value;
		
		showit("loadMes");
		
		}
	
		if(document.getElementById('useForm2').checked) {
		
			if (document.getElementById("addressc").value == "") {
				document.getElementById("addressc").focus();
				document.getElementById("errDisplay").innerHTML = "Please enter a starting location name (school, street, and/or town/city)";				
				$('#myModal').modal('show');	
				return;
			}
			
			if (document.getElementById("addressd").value == "") {
				document.getElementById("addressd").focus();
				document.getElementById("errDisplay").innerHTML = "Please enter a destination location name (school, street, and/or town/city)";				
				$('#myModal').modal('show');	
				return;
			}	
			
			
		address1 = document.getElementById("addressc").value + ", " +document.getElementById("prov3").value;
		address2 = document.getElementById("addressd").value + ", " +document.getElementById("prov4").value;
		showit("loadMes");		
		}
		
		
	
		// finding out the coordinates
		if (geocoder) 
		{
			geocoder.geocode( { 'address': address1}, function(results, status) 
			{
				if (status == google.maps.GeocoderStatus.OK) 
				{
					//location of first address (latitude + longitude)
					location1 = results[0].geometry.location;
				} else 
				{
					hide('map_canvas');
					hide('directionsPanel');
					hide('map_directions');
					alert("Geocode search was not successful for the following reason: " + status);
					
				}
			});
			geocoder.geocode( { 'address': address2}, function(results, status) 
			{
				if (status == google.maps.GeocoderStatus.OK) 
				{
					//location of second address (latitude + longitude)
					location2 = results[0].geometry.location;
					// calling the showMap() function to create and show the map 
					showMap();
				} else 
				{
					hide('map_canvas');
					hide('directionsPanel');
					hide('map_directions');
					alert("Geocode search was not successful for the following reason: " + status);
					
				}
			});
		}
	}
		
	// creates and shows the map
	function showMap()
	{
		// center of the map (compute the mean value between the two locations)
		latlng = new google.maps.LatLng((location1.lat()+location2.lat())/2,(location1.lng()+location2.lng())/2);
		show('map_canvas');
		show('directionsPanel');
		show('map_directions');
		// set map options
			// set zoom level
			// set center
			// map type
		var mapOptions = 
		{
			zoom: 1,
			center: latlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		
		// create a new map object
			// set the div id where it will be shown
			// set the map options
		map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
		
		// show route between the points
		directionsService = new google.maps.DirectionsService();
		directionsDisplay = new google.maps.DirectionsRenderer(
		{
			suppressMarkers: true,
			suppressInfoWindows: true,
			provideRouteAlternatives: true
			
		});
		directionsDisplay.setMap(map);
		var request = {
			origin:location1, 
			destination:location2,			
			travelMode: google.maps.DirectionsTravelMode.DRIVING,		
			//avoidHighways: true,
			//avoidTolls: true,			
			provideRouteAlternatives: true,
			unitSystem: google.maps.UnitSystem.METRIC
		};
		directionsService.route(request, function(response, status) 
		{
			document.getElementById("directionsPanel").innerHTML = "";
			if (status == google.maps.DirectionsStatus.OK) 
			{
				
				
				var routenum=1;
				//Just in case no extra route values besides n0, and we take the lowest of the three.
				var n0=10000;
				var n1=10000;
				var n2=10000;
				
				distance="<div class='alert alert-success'>The driving distance(s) (one way) between <b>"+address1+"</b> and <b>"+address2+"</b> are:<br/><br/>";
				var directionsDisplay = new google.maps.DirectionsRenderer;
				directionsDisplay.setPanel(document.getElementById('directionsPanel'));
				for (var i = 0; i < response.routes.length; i++){
					
					  
					    if(i <= 0) {
					    	//distance += "<span style='color:Red;'><b>Route "+ routenum +":</b>  "+parseFloat(response.routes[i].legs[0].distance.value).toFixed(1)+"kms ("+ response.routes[i].legs[0].duration.text+")</span> &nbsp;&nbsp;";
					    	distance += "<span style='color:Red;'><b>Route "+ routenum +":</b>  "+((response.routes[i].legs[0].distance.value)/1000).toFixed(2) +" kms ("+ response.routes[i].legs[0].duration.text+")</span> &nbsp;&nbsp;";
							
					    	//n0=parseFloat(response.routes[i].legs[0].distance.text).toFixed(0);
					    	n0=(response.routes[i].legs[0].distance.value)/1000;
					    	
					    	
							 directionsDisplay.setRouteIndex(i);
							 directionsDisplay.setMap(map);
							 directionsDisplay.setDirections(response);
							directionsDisplay.setOptions({
					          polylineOptions: {
					                          strokeColor: 'red',
					                          strokeweight: 3
					                           }
					       });
						  
						  
					    }
					     else if (i>0 && i==1) {

							  routenum++;
					    	 //distance += "<span style='color:Blue;'><b>Route "+ routenum +":</b>  "+parseFloat(response.routes[i].legs[0].distance.text).toFixed(1)+"kms ("+ response.routes[i].legs[0].duration.text+")</span> &nbsp;&nbsp;";
					    	 distance += "<span style='color:Blue;'><b>Route "+ routenum +":</b>  "+((response.routes[i].legs[0].distance.value)/1000).toFixed(2)+" kms ("+ response.routes[i].legs[0].duration.text+")</span> &nbsp;&nbsp;";
					    	 
					    	 n1=(response.routes[i].legs[0].distance.value)/1000;
					    	 directionsDisplay.setRouteIndex(i);
					    	 directionsDisplay.setMap(map);
					    	 directionsDisplay.setDirections(response);
					    	 directionsDisplay.setOptions({
					          polylineOptions: {
					                          strokeColor: 'blue',
					                          strokeOpacity: 0.6,
					                          strokeweight: 1
					                           }
					       });
					        }
					     else {
					    	 routenum++;
					    	 distance += "<span style='color:Green;'><b>Route "+ routenum +":</b>  "+((response.routes[i].legs[0].distance.value)/1000).toFixed(2)+" kms ("+ response.routes[i].legs[0].duration.text+")</span> &nbsp;&nbsp;";
					    	 n2=(response.routes[i].legs[0].distance.value)/1000;
					    	 directionsDisplay.setRouteIndex(i); 
					    	 directionsDisplay.setMap(map);
					    	 directionsDisplay.setDirections(response);
					    	 directionsDisplay.setOptions({
						          polylineOptions: {
						                          strokeColor: 'green',
						                          strokeOpacity: 0.6,
						                          strokeweight: 2
						                           }
						       });
						       }
					    
					    
					     
					  }
				
				var minDistance = Math.min(n0,n1,n2);
				var totalDistance = Math.round(minDistance*2);
				
				if (totalDistance < 0.5) { 
					distance+="<br/><br/><span style='color:red;font-size:12px;'><b>NOTICE:</b> Your total distance is less than 0.5 kms, therefore a value of 0 km must be used. If you have traveled this distance more than once for a particular claim, please combine them into one item so your total is >= 1km.</span>";
					
				}
			     
				minDistance = Math.round(minDistance);
				
				
				
				
				distance+="<br/><br/><span style='color:black;font-size:12px;'>Number of routes:"+ response.routes.length +". You should use <b>"+ minDistance +" kms</b> to calculate your travel claim ("+totalDistance+" kms return). However you can use any route to travel to and from your destination.</span>";
				distance+="</div>";
			     hideit("loadMes");
				
				
				
				
				//directionsDisplay.setDirections(response);
				//distance += "<div class='alert alert-success'>The shortest driving distance (one way) between <b>"+address1+"</b> and <b>"+address2+"</b> is: "+response.routes[0].legs[0].distance.text+"</div>";
				//distance += "<div class='alert alert-info'>The aproximative driving time between these two locations is: "+response.routes[0].legs[0].duration.text+"</div>";
				document.getElementById("distance_road").innerHTML = distance;
			}
		});
		
		// show a line between the two points (No need for this now outside test)
		//var line = new google.maps.Polyline({
		//	map: map, 
		//	path: [location1, location2],
		//	strokeWeight: 0,
		//	strokeOpacity: 0.8,
		//	strokeColor: "#FFAA00"
		//});
		
		// create the markers for the two locations	(No need for this now outside test)	
		//var image = 'http://maps.google.com/mapfiles/kml/paddle/grn-blank.png';
		//var marker1 = new google.maps.Marker({
		//map: map, 
	//		position: location1,
	//	title: address1,
	//	icon: image
	//	});
	//	var image = 'http://maps.google.com/mapfiles/kml/shapes/ranger_station.png';
	//	var marker2 = new google.maps.Marker({
	//		map: map, 
	//		icon: image,
	//		position: location2,
	//		title: address2
	//	});
		
		// create the text to be shown in the infowindows
		var text1 = '<div id="content">'+
				'<h1 id="firstHeading">First location</h1>'+
				'<div id="bodyContent">'+
				'<p>Coordinates: '+location1+'</p>'+
				'<p>Address: '+address1+'</p>'+
				'</div>'+
				'</div>';
				
		var text2 = '<div id="content">'+
			'<h1 id="firstHeading">Second location</h1>'+
			'<div id="bodyContent">'+
			'<p>Coordinates: '+location2+'</p>'+
			'<p>Address: '+address2+'</p>'+
			'</div>'+
			'</div>';
		
		// create info boxes for the two markers
		var infowindow1 = new google.maps.InfoWindow({
			content: text1
		});
		var infowindow2 = new google.maps.InfoWindow({
			content: text2
		});

		// add action events so the info windows will be shown when the marker is clicked
		google.maps.event.addListener(marker1, 'click', function() {
			infowindow1.open(map,marker1);
		});
		google.maps.event.addListener(marker2, 'click', function() {
			infowindow2.open(map,marker1);
		});
		
		// compute distance between the two points
		var R = 6371; 
		var dLat = toRad(location2.lat()-location1.lat());
		var dLon = toRad(location2.lng()-location1.lng()); 
		
		var dLat1 = toRad(location1.lat());
		var dLat2 = toRad(location2.lat());
		
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(dLat1) * Math.cos(dLat1) * 
				Math.sin(dLon/2) * Math.sin(dLon/2); 
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		var d = R * c;
		
		//document.getElementById("distance_direct").innerHTML = "<br/>The distance between <b>"+address1+"</b> and <b>"+address2+"</b> (in a straight line) is: "+d.toFixed(2)+" kms";
	
	hideit("loadMes");
	}
	
	function toRad(deg) 
	{
		return deg * Math.PI/180;
	}

	function show(target){
		document.getElementById(target).style.display = 'block';
		}
	function hide(target){
		document.getElementById(target).style.display = 'none';
		}
	
	function showMe (box) {

	    var chboxs = document.getElementsByName("calculate-type");
	    var vis = "none";
	    for(var i=0;i<chboxs.length;i++) { 
	        if(chboxs[i].checked){
	         vis = "block";
	            break;
	        }
	    }
	    document.getElementById(box).style.display = vis;


	}