function toggle(target, sw)
{
  obj=(document.all) ? document.all[target] : document.getElementById(target);
  obj.style.display=sw;
}

function seniorHighCheck(frm)
  {
    var tog = false;
    var i=0;
    var grades = frm.gradeLevel;
    
    for(i=0; i < grades.options.length; i++)
    {
      if(grades.options[i].selected)
      {
        if((grades.options[i].text == "LEVEL I")
          ||(grades.options[i].text == "LEVEL II")
          ||(grades.options[i].text == "LEVEL III"))
        {
          tog = true;
        }
      }
    }

    if(tog == true)
    {
      toggle('crs-header', 'inline');
      toggle('crs-footer', 'inline');
    }
    else
    {
      toggle('crs-header', 'none');
      toggle('crs-footer', 'none');
      frm.documentCourse.selectedIndex = 0;
    }
  }

  function openWindow(url,id,w,h) {
    window.open(url,id,"toolbar=0,location=no,top=50,left=50,directories=0,status=0,menbar=0,scrollbars=0,resizable=0,width="+w+",height="+h);

         if (navigator.appName == 'Netscape') {
                 popUpWin.focus();
         }
  }

  function resetMsg()
  {
    var cell  = document.getElementById("msg");

    cell.innerHTML = "<img src='images/spacer.gif' width='1' height='5'><BR>";
  }

  function validateDocument(frm)
  {
    var check = true;
    
    if(frm.documentType.selectedIndex==0)
    {
      alert("Please indicate the document type.");
      check = false;
    }
    else if(frm.documentSubject.selectedIndex==0)
    {
      alert("Please indicate the document subject.");
      check = false;
    }
    else if(frm.gradeLevel.selectedIndex==0)
    {
      alert("Please indicate the document grade level.");
      check = false;
    }
    else if((frm.keyword1.value=='')&&(frm.keyword2.value=='')&&(frm.keyword3.value=='')&&(frm.keyword4.value==''))
    {
      alert("Please enter at least one keyword.");
      check = false;
    }
    else if(((frm.keyword1.value != '')&&((frm.keyword1.value==frm.keyword2.value)||(frm.keyword1.value==frm.keyword3.value)
      || (frm.keyword1.value==frm.keyword4.value))) || ((frm.keyword2.value != '')&&((frm.keyword2.value==frm.keyword3.value)
      || (frm.keyword2.value==frm.keyword4.value))) || ((frm.keyword3.value != '')&&(frm.keyword3.value==frm.keyword4.value)))
    {
      alert("All keywords must be unique.");
      check = false;
    }
    else if(frm.uploadfile.value=='')
    {
      alert("Please choose the file to import.");
      check = false;
    }
    else if((frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL I")
      || (frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL II")
      || (frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL III"))
    {
      if(frm.documentCourse.selectedIndex==0)
      {
        alert("Please indicate course.");
        check = false;
      }
    }

    return check;
  }

  function validateQuestion(frm)
  {
    var check = true;
    
    if(frm.questionType.selectedIndex==0)
    {
      alert("Please indicate the question type.");
      check = false;
    }
    else if(frm.questionSubject.selectedIndex==0)
    {
      alert("Please indicate the question subject.");
      check = false;
    }
    else if(frm.gradeLevel.selectedIndex==0)
    {
      alert("Please indicate the question grade level.");
      check = false;
    }
    else if((frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL I")
      || (frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL II")
      || (frm.gradeLevel.options[frm.gradeLevel.selectedIndex].text=="LEVEL III"))
    {
      if(frm.documentCourse.selectedIndex==0)
      {
        alert("Please indicate course.");
        check = false;
      }
    }

    return check;
  }

  function questionTypeCheck(frm)
  {
    var tog = false;
    var i=0;
    
    if(frm.questionType.selectedIndex >= 1)
    {
      //alert(frm.questionType.options[frm.questionType.selectedIndex].text);
      
      if(frm.questionType.options[frm.questionType.selectedIndex].text == 'MULTIPLE CHOICE SINGLE ANSWER')
      {
        toggle('ans_header', 'none');
        toggle('ans_footer', 'none');
        toggle('tf_header', 'none');
        toggle('tf_footer', 'none');
        toggle('mcsa_header', 'inline');
        toggle('mcsa_footer', 'inline');
        toggle('mcma_header', 'none');
        toggle('mcma_footer', 'none');
      }
      else if(frm.questionType.options[frm.questionType.selectedIndex].text == 'MULTIPLE CHOICE MULTIPLE ANSWER')
      {
        toggle('ans_header', 'none');
        toggle('ans_footer', 'none');
        toggle('tf_header', 'none');
        toggle('tf_footer', 'none');
        toggle('mcsa_header', 'none');
        toggle('mcsa_footer', 'none');
        toggle('mcma_header', 'inline');
        toggle('mcma_footer', 'inline');
      }
      else if(frm.questionType.options[frm.questionType.selectedIndex].text == 'TRUE/FALSE')
      {
        toggle('ans_header', 'none');
        toggle('ans_footer', 'none');
        toggle('tf_header', 'inline');
        toggle('tf_footer', 'inline');
        toggle('mcsa_header', 'none');
        toggle('mcsa_footer', 'none');
        toggle('mcma_header', 'none');
        toggle('mcma_footer', 'none');
      }
      else if(frm.questionType.options[frm.questionType.selectedIndex].text == 'FILL-IN-THE-BLANK')
      {
        toggle('ans_header', 'inline');
        toggle('ans_footer', 'inline');
        toggle('tf_header', 'none');
        toggle('tf_footer', 'none');
        toggle('mcsa_header', 'none');
        toggle('mcsa_footer', 'none');
        toggle('mcma_header', 'none');
        toggle('mcma_footer', 'none');
      }
      else
      {
        toggle('ans_header', 'none');
        toggle('ans_footer', 'none');
        toggle('tf_header', 'none');
        toggle('tf_footer', 'none');
        toggle('mcsa_header', 'none');
        toggle('mcsa_footer', 'none');
        toggle('mcma_header', 'none');
        toggle('mcma_footer', 'none');
      }
    }   
  }