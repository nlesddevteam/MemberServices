function numberTest (sNr)
{
    var reFloatF1 = /^(\d+)$/; // format 1
    
    return (reFloatF1.test (sNr));
}

function validateEvent(form)
{
  var check = true;
  var seltxt;

  if(form.evttype.selectedIndex == 0)
  {
    alert( 'Please selected event type' );
    check = false;
  }
  else if(form.EventName.value=="")
  {
    alert( 'Title is Required' );
    check = false;
  }
  else if(form.EventName.value.length > 150)
  {
    alert( 'Title must not exceed 150 characters' );
    check = false;
  }
  else if(form.EventDesc.value=="")
  {
    alert( 'Description is Required' );
    check = false;
  }
  else if(form.EventDesc.value.length > 500)
  {
    alert( 'Description must not exceed 500 characters' );
    check = false;
  }
  else if(form.EventLocation.value=="")
  {
    alert( 'Location is Required' );
    check = false;
  }
  else if(form.EventLocation.value.length > 150)
  {
    alert( 'Location must not exceed 150 characters.' );
    check = false;
  }
  else if($('#lstSchoolZone').val() == "")
  {
    alert( 'Region must be selected.' );
    check = false;
  }
  else if(form.EventDate.value=="")
  {
    alert( 'Date is Required' );
    check = false;
  }
  else
  {
    seltxt = form.evttype.options[form.evttype.selectedIndex].text;
    
    if(seltxt == 'CLOSE-OUT DAY PD SESSION')
    {
      if(form.closeoutoption.selectedIndex == 0)
      {
        alert('Please select close-out session option.');
        check = false;
      }
    }

    if(seltxt == 'DISTRICT CALENDAR CLOSE-OUT ENTRY')
    {
      if(form.EventEndDate.value=="")
      {
        alert( 'End Date is Required' );
        check = false;
      }
    }
    
    if(check && ((seltxt == 'PD OPPORTUNITY') || (seltxt == 'CLOSE-OUT DAY PD SESSION')))
    {
      if(form.limited.checked)
      {
        if(form.max.value=="")
        {
          alert('Maximum Number of Participants required.');
          check = false;
        }
        else if(! numberTest(form.max.value))
        {
          alert('Invalid number format. Should be positive number only.');
          check = false;
        }
      }
    }

    if(check && form.evttime.checked)
    {
      if(form.shour.selectedIndex==-1)
      {
        alert( 'Start Time Hour is Required' );
        check = false;
      }
      else if(form.sminute.selectedIndex==-1)
      {
        alert( 'Start Time Minute is Required' );
        check = false;
      }
      else if(form.sAMPM.selectedIndex==-1)
      {
        alert( 'Start Time AM/PM is Required' );
        check = false;
      }
      else if(form.fhour.selectedIndex==-1)
      {
        alert( 'Finish Time Hour is Required' );
        check = false;
      }
      else if(form.fminute.selectedIndex==-1)
      {
        alert( 'Finish Time Minute is Required' );
        check = false;
      }
      else if(form.fAMPM.selectedIndex==-1)
      {
        alert( 'Finish Time AM/PM is Required' );
        check = false;
      }
      else if((eval(form.shour.value) > eval(form.fhour.value))&&(form.sAMPM.value==form.fAMPM.value))
      {
    	  if(eval(form.shour.value) < 12){
    		  alert( 'Start time later then Finish time.' );
    		  check = false;
    	  }
      }
      else if((eval(form.shour.value) == eval(form.fhour.value))&&(form.sAMPM.value==form.fAMPM.value)
        &&((eval(form.sminute.value) > eval(form.fminute.value))))
      {
        alert( 'Start time later then Finish time.' );
        check = false;
      }
    }
    
    if(check && (seltxt == 'SCHOOL PD REQUEST'))
    {
      if(form.agendafile.value == null)
      {
        alert( 'Please select adgenda file.' );
        check = false;
      }
    }
    
    $('#evtreqs option:selected').each(function(){
    	if($(this).attr('extrainfo') && $('#' + $(this).attr('extrainfo')).val() == ''){
    		alert('Please enter ' + $(this).attr('extrainfo') + '.');
    		check = false;
    	}
    })
  }

  return check;
}