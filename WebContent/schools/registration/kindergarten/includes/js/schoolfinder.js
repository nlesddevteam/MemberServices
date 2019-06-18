//Required dojo modules
dojo.require("esri.tasks.locator");
dojo.require("esri.tasks.gp");

//Call this function to get School ID
function getSchoolID() {
	$('#tblPhysicalAddress caption').append("<img src='/MemberServices/schools/registration/kindergarten/includes/images/ajax-loader-1.gif' align='right' border='0' />");
	
    //Get Address and City values
    var sAddress = dojo.byId("txt_PhysicalStreetAddress1").value;
    var sCity = dojo.byId("txt_PhysicalCityTown").value;

    //Set the spatial reference used by the geocode service
    var sr = new esri.SpatialReference({ wkid: 102100 });

    if (sAddress == '' || sCity == '') {

        alert("Please enter address and city");

    } else {

        //Show wait cursor. It might take a few seconds for the geocode and geoprocessor services to return results.
        //document.body.style.cursor = "wait";

        //Geocode Service 
        var locator = new esri.tasks.Locator("http://tasks.arcgisonline.com/ArcGIS/rest/services/Locators/TA_Address_NA_10/GeocodeServer");

        //Set Parameters
        var address = { "Address": sAddress, "City": sCity, "State": "NL", "Country": "Canada" };
        var params = { address: address };

        //Set spatial reference
        locator.outSpatialReference = sr;

        //Call Geocode Service to get location for the address
        //params - Geocode parameters
        //foundAddress - Function that's called when geocode service has found the location for the address
        //addressError - Function that's called when geocode service has an error
        locator.addressToLocations(params, foundAddress, addressError);

    }

}

//Function that's called when geocode service has an error
function addressError(err) {

    //document.body.style.cursor = "default";
    //alert("Error Finding Location for Address");
	$('#tblPhysicalAddress caption img').remove();

}

//Function that's called when geocode service has found the location for the address
function foundAddress(addressCandidates) {

    if (addressCandidates.length == 0) {

        //document.body.style.cursor = "default";
        //alert("Error Finding Location for Address");
    	$('#tblPhysicalAddress caption img').remove();

    } else {
    
        var features = [];
        var featureSet, params;

        //Create new graphic for address location
        var newPoint = new esri.geometry.Point([parseFloat(addressCandidates[0].location.x), parseFloat(addressCandidates[0].location.y)], new esri.SpatialReference({ wkid: 102100 }));
        var newGraphic = new esri.Graphic(newPoint);
        
        //Geoprocessing Service
        gpLocateCatchment = new esri.tasks.Geoprocessor("http://ec2-67-202-1-67.compute-1.amazonaws.com/ArcGIS/rest/services/ESDNL/ESDNL/GPServer/Locate%20Catchment");
        
        //Add new graphic to features array       
        features.push(newGraphic);
        
        //Create a new featureset and add features array
        featureSet = new esri.tasks.FeatureSet();
        featureSet.features = features;

        //set parameters and spatial reference
        params = { "Input_Address_Position": featureSet, "School_Type": "" };
        gpLocateCatchment.setUpdateDelay("1000");
        gpLocateCatchment.outSpatialReference = new esri.SpatialReference({ wkid: 102100 });

        //Call geoprocessing service
        //params - Geoprocessor parameters
        //gpFindCatchments - Function that's called if the geoprocessing service has an error
        //gpStatusCallback - Function that's called to check geoprocessing job status
        //gpError -Function that's called when geoprocessing job is complete
        gpLocateCatchment.submitJob(params, gpFindCatchments, gpStatusCallback, gpError);
    }
}

//Function that's called if the geoprocessing service has an error
function gpError(err) {

    //document.body.style.cursor = "default";
    //alert("Unable to find school ID");
	$('#tblPhysicalAddress caption img').remove();
}

//Function that's called to check geoprocessing job status
function gpStatusCallback(jobInfo) {

    //If geoprocessing  job failed call gpError function
    if (jobInfo.jobStatus == "esriJobFailed") {
        gpError();
    }

}

//Function that's called when geoprocessing job is complete
function gpFindCatchments(results, messages) {

    //Get results from geoprocessing service
    //results.jobId - Geoprocessing job ID
    //"CatchmentResult" - get results for this parameter 
    //processGetGPData - Function that's called when getting results from geoprocessing job
    //gpError - Function that's called if the geoprocessing service has an error
    gpLocateCatchment.getResultData(results.jobId, "CatchmentResult", processGetGPData, gpError);

}

//Function that's called when getting results from geoprocessing job
function processGetGPData(result) {
	
    var schoolID = '';
    var j, schoolType;
    var rLength = result.value.features.length;

    //loop through results and get school ID
    for (j = 0, jl = rLength; j < jl; j++) {

        schoolType = result.value.features[j].attributes["SCHOOL_TYP"];

        //Get ID if school type is PRIMARY_ELEMENTARY or contains K
        if (schoolType.toUpperCase() === "PRIMARY_ELEMENTARY" || schoolType.toUpperCase().indexOf("K") !== -1) {
            schoolID = result.value.features[j].attributes["SCHOOL_NUM"];
        }

    }
    if(schoolID != ''){
    	$.post("/MemberServices/schools/registration/kindergarten/ajax/getSchoolByDeptId.html", 
			{	
				deptid : schoolID,
				ajax : true 
			}, 
			function(data){
				if($(data).find('SCHOOL').length > 0) {
					$('#ddl_School').val($(data).find('ID').first().text());
					$('#ddl_School').change();
				}
				
				$('#tblPhysicalAddress caption img').remove();
			}, 
			"xml");
    }
    //populate text box with school ID
    //dojo.byId("txtSchoolID").value = schoolID;

}