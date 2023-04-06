<%@ page language="java" contentType="text/html" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html> 
<head>
<title>Weather and Road Conditions</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style>
td {vertical-align:middle;}
input { border:1px solid silver;}

.card {width:24%;margin-top:3px;float:left;margin-right:3px;height:260px;min-width:300px;}
.cardW {height:600px;}
.card-title {text-transform:uppercase;font-size:14px;font-weight:bold;}
.card-image {width:100%;max-width:200px;height:160px;transition: transform .2s;z-index:99;}

.card-image:hover { transform: scale(3.0);position:relative;z-index:999;}

.cameraAvalon {background-color:rgba(191, 0, 0, 0.1);}
.cameraEastern {background-color:rgba(128, 0, 128, 0.1);}
.cameraCentral {background-color:rgba(143, 188, 143, 0.1);}
.cameraWestern {background-color:rgba(255, 132, 0, 0.1);}
.cameraLabrador {background-color:rgba(127, 130, 255, 0.1);}
</style>

</head>

<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
		
  	<div class="siteHeaderGreen">Provincial Weather Conditions</div>					
  		Below are the current weather forcasts for office locations throughout the province as well as all provincial road cameras. 
  		
  		<br/><br/>
  		
  		<div align="center"><a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to StaffRoom</a></div>
  		<br/><br/>
  		
  	 <div style="clear:both;"></div>	
  	<div class="card cardW cameraLabrador">
    <div class="card-body text-center">
    <h4 class="card-title">DISTRICT OFFICE<br/>(Labrador)</h4>
    <img class="card-img-top" src="/includes/img/office-labrador.png" style="border:1px solid white;margin-bottom:5px;">
    						16 Strathcona Street<br/>
						 	Happy Valley-Goose Bay, NL<br/>
						 	Canada &middot; A0P 1E0<br/>
						 	<b>Tel:</b>(709)896-2431<br/>	<b>Fax:</b>(709)896-9638
						 	<hr>
						 	
						 	<a class="weatherwidget-io" href="https://forecast7.com/en/53d30n60d33/happy-valley-goose-bay/" data-label_1="HAPPY VALLEY-GOOSE BAY" data-label_2="WEATHER" data-days="3" data-theme="pure" data-basecolor="rgba(255, 255, 255, 0)" >HAPPY VALLEY-GOOSE BAY WEATHER</a>
<script>
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src='https://weatherwidget.io/js/widget.min.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','weatherwidget-io-js');
</script>
						 	
    </div>
    </div>
  	<div class="card cardW cameraWestern">
    <div class="card-body text-center">
    <h4 class="card-title">DISTRICT OFFICE<br/>(Western)</h4>
     <img class="card-img-top" src="/includes/img/office-western.png" style="border:1px solid white;margin-bottom:5px;"> 
    10 Wellington Street<br/>
						 	Corner Brook, NL<br/>
						 	Canada &middot; A2H 6G9<br/>			
							<b>Tel:</b>(709)637-4000<br/><b>Fax:</b>(709)634-1828	
							<hr>
							<a class="weatherwidget-io" href="https://forecast7.com/en/48d95n57d95/corner-brook/" data-label_1="CORNER BROOK" data-label_2="WEATHER" data-days="3" data-theme="pure" data-basecolor="rgba(255, 255, 255, 0)" >CORNER BROOK WEATHER</a>
<script>
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src='https://weatherwidget.io/js/widget.min.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','weatherwidget-io-js');
</script>
							
    </div>
    </div>
  	<div class="card cardW cameraCentral">
    <div class="card-body text-center">
    <h4 class="card-title">DISTRICT OFFICE<br/>(Central)</h4>
    <img class="card-img-top" src="/includes/img/office-central.png" style="border:1px solid white;margin-bottom:5px;"> 
    203 Elizabeth Drive<br/>
				 			Gander, NL<br/>
				 			Canada &middot; A1V 1H6<br/>
							<b>Tel:</b>(709)256-2547<br/><b>Fax:</b>(709)651-3044
							<hr>
							<a class="weatherwidget-io" href="https://forecast7.com/en/48d95n54d61/gander/" data-label_1="GANDER" data-label_2="WEATHER" data-days="3" data-theme="pure" data-basecolor="rgba(255, 255, 255, 0)" >GANDER WEATHER</a>
<script>
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src='https://weatherwidget.io/js/widget.min.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','weatherwidget-io-js');
</script>
    </div>
    </div>
  	<div class="card cardW cameraAvalon">
    <div class="card-body text-center">
    <h4 class="card-title">DISTRICT OFFICE<br/>(Avalon)</h4>
    <img class="card-img-top" src="/includes/img/office-avalon.png" style="border:1px solid white;margin-bottom:5px;">   
    95 Elizabeth Avenue<br/>	
							St. John's, NL<br/>
							Canada &middot; A1B 1R6<br/>			
							<b>Tel:</b>(709)758-2372<br/><b>Fax:</b>(709)758-2706
							<hr>
							<a class="weatherwidget-io" href="https://forecast7.com/en/47d56n52d71/st-johns/" data-label_1="ST. JOHN'S" data-label_2="WEATHER" data-days="3" data-theme="pure" data-basecolor="rgba(255, 255, 255, 0)" >ST. JOHN'S WEATHER</a>
<script>
!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src='https://weatherwidget.io/js/widget.min.js';fjs.parentNode.insertBefore(js,fjs);}}(document,'script','weatherwidget-io-js');
</script>
    </div>
    </div>            
 
  	 <div style="clear:both;"></div>	
  		

  						<hr>				
  	<div class="siteHeaderGreen">Provincial Cameras / Road Conditions</div>					
  		Below are the current provincial camera road conditions and weather. Images are updated from these cameras every 15-20 minutes depending. To see the current images, please load/refresh this page again.				
<!-- **********AVALON CAMERAS ******************** -->  

  <div class="siteSubHeaderGreen">Avalon Cameras</div>		
 <div style="clear:both;"></div>
 	<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">BAY ROBERTS</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/bayroberts_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located at Bay Roberts Tourist Chalet, Route # 75, Looking North
      </p>
    </div>
  	</div>
   	<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Black Duck Siding</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/blackducksiding_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 460, 13.5 km from TCH Facing North - East
      </p>
    </div>
  	</div>
  	<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Dunville</h4>
     <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/Dunville_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
     <br/>Route 100, at Intersection to Long Hill Road, Facing North East
      </p>
    </div>
  </div>
<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Foxtrap</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/foxtrap/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking East
      </p>
    </div>
  </div>
  
<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Heart's Content</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/heartscontent_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 74, 8.9 km from the intersection of Routes 70 &amp; 74 facing South-East
      </p>
    </div>
</div>

<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Holyrood</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/holyrood_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Looking West, Located At Holyrood Exit Route 62
      </p>
    </div>
</div>
<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Paddy's Pond</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/paddyspond/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking East
      </p>
    </div>
  </div>
  <div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Salmonier Line</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/salmonier/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 90, facing North East
      </p>
    </div>
  </div>
 
<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Trepassey</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/trepassey/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route #10, Looking West
      </p>
    </div>
  </div>


<div class="card cameraAvalon">
    <div class="card-body text-center">
     <h4 class="card-title">Whitbourne</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/whitbourne_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Looking East, Located at Intersection of TCH and Route 100
(Visitor Information Center)
      </p>
    </div>
</div>  
  
<div style="clear:both;"></div>
<hr>
<!-- ********** EASTERN CAMERAS ******************** -->    
  <div class="siteSubHeaderGreen">Eastern Cameras</div>
  <div style="clear:both;"></div>
  <div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Clarenville</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/clarenville/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route #1, Looking East
      </p>
    </div>
  </div>
  <div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Dunn's River</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/DunnsRiver_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 210, Looking South
      </p>
    </div>
  </div>
  <div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Goobies</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/goobies/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking West
      </p>
    </div>
  </div> 
 
<div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Grand Bank</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/grandbank/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route #210, Looking North East
      </p>
    </div>
  </div> 
  
  
  <div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Port Rexton</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/portrexton_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route # 230 Looking West
      </p>
    </div>
  </div>
  <div class="card cameraEastern">
    <div class="card-body text-center">
     <h4 class="card-title">Salt Pond</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/saltpond/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route # 220, Looking South East
      </p>
    </div>
  </div>
  

<div style="clear:both;"></div>
<hr>
<!-- ********** CENTRAL CAMERAS ******************** -->   
  <div class="siteSubHeaderGreen">Central Cameras</div>
  <div style="clear:both;"></div>
  <div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">BADGER</h4>
      <p class="card-text"><img src="https://www.roads.gov.nl.ca/cameras/sites/badger_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located at Trans Canada Highway, Route #1, Facing East Bound.
This camera is located approximately 600m East of Badger River Bridge.
      </p>
    </div>
  </div>
  <div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Birchy Narrows</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/birchynarrows_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located on Route #1 TCH, Facing East  
      </p>
    </div>
  </div>
  <div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Gander</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/gander/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking West
      </p>
    </div>
  </div>  
<div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Grand Falls-Windsor</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/grandfallswindsor/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking East
      </p>
    </div>
</div>
   
<div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Harbour Breton</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/harbourbreton_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Intersection Route # 360 and 362, Looking South
      </p>
    </div>
</div>

<div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Lewisporte</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/Lewisporte_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located at Trans Canada Highway, on Route # 340, Facing South
      </p>
    </div>
</div>

<div class="card cameraCentral">
    <div class="card-body text-center">
     <h4 class="card-title">Lumsden</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/lumsden_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located at Route 330, Looking South
      </p>
    </div>
</div>
  
<div style="clear:both;"></div>
<hr>
<!-- ********** WESTERN CAMERAS ******************** -->   
  <div class="siteSubHeaderGreen">Western Cameras</div>
  <div style="clear:both;"></div>
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Burgeo</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/burgeo_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 480, facing South
      </p>
    </div>
  </div>
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Corner Brook</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/cornerbrook/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route #1, Looking North East
      </p>
    </div>
  </div>
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Daniel's Harbour</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/danielsharbour_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route # 430, Looking South
      </p>
    </div>
  </div>
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Doyles</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/doyles/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route #1, Looking North East
      </p>
    </div>
  </div>
  
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Flower's Cove</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/flowerscove/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      Northern Peninsula, Route # 430, Looking North
      </p>
    </div>
  </div>
  <div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Port au Port Peninsula</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/portauport_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      Located on Route #463, Facing West
      </p>
    </div>
</div>

<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Port aux Basques</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/portauxbasques/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking West
      </p>
    </div>
</div>
  

<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Port Saunders</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/portsaunders_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      Located at intersection of Route #430 & #430-28, Facing South
      </p>
    </div>
  </div>


<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Pynn's Brook</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/pynnsbrook/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking West
      </p>
    </div>
</div>

<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Rocky Harbour</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/RockyHarbour_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Located on Route 430 at intersection of West Link Drive, Facing in a North West direction
      </p>
    </div>
</div>
<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">Springdale</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/springdale/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Canada Highway, Route # 1, Looking West
      </p>
    </div>
</div>

<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">St. George's</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/StGeorges_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>TCH, 1.9 KM West of Exit to Route 461, Looking West
      </p>
    </div>
</div>



<div class="card cameraWestern">
    <div class="card-body text-center">
     <h4 class="card-title">St. Anthony</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/stanthony_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route 430, facing West
      </p>
    </div>
  </div>
  
 
<div style="clear:both;"></div>
<hr>
<!-- ********** LABRADOR CAMERAS ******************** -->   
  <div class="siteSubHeaderGreen">Labrador Cameras</div>
  <div style="clear:both;"></div>
  <div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Cartwright Junction</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/cartwrightjunction/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      Trans Labrador Highway, Route # 510, Looking Northbound      
      </p>
    </div>
</div>

<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Chateau Pond</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/chateaupond/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/> 
      Trans Labrador Highway, Route # 510, Looking Westbound
      </p>
    </div>
  </div>
  
<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Churchill Falls</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/churchillfalls/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
    <br/>
    Churchill Falls Airport, on Route 500 TLH, pointing
	East towards Churchill Falls.</p>
    </div>
  </div>


<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Crooks Lake</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/crookslake/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      Trans Labrador Highway, Route # 510, Looking West
      </p>
    </div>
</div>

<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Happy Valley-Goose Bay</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/goosebay/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>
      View from Elizabeth Goudie Building
      </p>
    </div>
</div>

<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">L'Anse Au Loup</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/lanseauloup/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Route # 510, Looking West
      </p>
    </div>
  </div>
  
<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Trans Labrador Highway</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/goosebay_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Labrador Highway, Route #500, Looking East towards Happy Valley - Goose Bay
      </p>
    </div>
  </div>

<div class="card cameraLabrador">
    <div class="card-body text-center">
     <h4 class="card-title">Wabush</h4>
      <p class="card-text"><img src="http://www.roads.gov.nl.ca/cameras/sites/wabush_amec/current.jpg" onerror="this.onerror=null; this.src='img/no-camphoto.png'" class="card-image"/>
      <br/>Trans Labrador Highway, Route # 500, Looking West
      </p>
    </div>
</div>
  
  
 <div style="clear:both;"></div>
<br/><br/>
 <div align="center"><a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to StaffRoom</a></div>

</div> 
</div>
</div>

</body>
</html>