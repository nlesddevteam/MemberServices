!function(a){function f(a,b){if(!(a.originalEvent.touches.length>1)){a.preventDefault();var c=a.originalEvent.changedTouches[0],d=document.createEvent("MouseEvents");d.initMouseEvent(b,!0,!0,window,1,c.screenX,c.screenY,c.clientX,c.clientY,!1,!1,!1,!1,0,null),a.target.dispatchEvent(d)}}if(a.support.touch="ontouchend"in document,a.support.touch){var e,b=a.ui.mouse.prototype,c=b._mouseInit,d=b._mouseDestroy;b._touchStart=function(a){var b=this;!e&&b._mouseCapture(a.originalEvent.changedTouches[0])&&(e=!0,b._touchMoved=!1,f(a,"mouseover"),f(a,"mousemove"),f(a,"mousedown"))},b._touchMove=function(a){e&&(this._touchMoved=!0,f(a,"mousemove"))},b._touchEnd=function(a){e&&(f(a,"mouseup"),f(a,"mouseout"),this._touchMoved||f(a,"click"),e=!1)},b._mouseInit=function(){var b=this;b.element.bind({touchstart:a.proxy(b,"_touchStart"),touchmove:a.proxy(b,"_touchMove"),touchend:a.proxy(b,"_touchEnd")}),c.call(b)},b._mouseDestroy=function(){var b=this;b.element.unbind({touchstart:a.proxy(b,"_touchStart"),touchmove:a.proxy(b,"_touchMove"),touchend:a.proxy(b,"_touchEnd")}),d.call(b)}}}(jQuery);
!function(e){e.fn.menumaker=function(n){var s=e(this),t=e.extend({title:"Menu",format:"dropdown",breakpoint:768,sticky:!1},n);return this.each(function(){if(s.find("li ul").parent().addClass("has-sub"),"select"!=t.format)s.prepend('<div id="menu-button">'+t.title+"</div>"),e(this).find("#menu-button").on("click",function(){e(this).toggleClass("menu-opened");var n=e(this).next("ul");n.hasClass("open")?n.hide().removeClass("open"):(n.show().addClass("open"),"dropdown"===t.format&&n.find("ul").show())}),multiTg=function(){s.find(".has-sub").prepend('<span class="submenu-button"></span>'),s.find(".submenu-button").on("click",function(){e(this).toggleClass("submenu-opened"),e(this).siblings("ul").hasClass("open")?e(this).siblings("ul").removeClass("open").hide():e(this).siblings("ul").addClass("open").show()})},"multitoggle"===t.format?multiTg():s.addClass("dropdown");else if("select"===t.format){s.append('<select style="width: 100%"/>').addClass("select-list");var n=s.find("select");n.append("<option>"+t.title+"</option>",{selected:"selected",value:""}),s.find("a").each(function(){console.log(e(this).parents("ul").length);var s=e(this),t="";for(i=1;i<s.parents("ul").length;i++)t+="-";n.append('<option value="'+e(this).attr("href")+'">'+t+s.text()+"</option")}),n.on("change",function(){window.location=e(this).find("option:selected").val()})}return t.sticky===!0&&s.css("position","fixed"),resizeFix=function(){e(window).width()>t.breakpoint&&(s.find("ul").show(),s.removeClass("small-screen"),"select"===t.format&&s.find("select").hide()),e(window).width()<=t.breakpoint&&(s.find("ul").hide().removeClass("open"),s.addClass("small-screen"),"select"===t.format&&s.find("select").show())},resizeFix(),e(window).on("resize",resizeFix)})}}(jQuery);

(function($){
	$(document).ready(function(){
	$("#busingMenu").menumaker({
	    title: "Menu",
	    breakpoint: 768,
	    format: "multitoggle"
	});

	});
	})(jQuery);

(function($){
	$(document).ready(function(){

	$("#contractor_menu").menumaker({
	    title: "Menu",
	    breakpoint: 768,
	    format: "multitoggle"
	});

	});
	})(jQuery);


//Force close submenus on ipad and mobile after touch.
function closeMenu()
{
	if ($("#menu-button").hasClass("menu-opened")) {
		$("#menu-button").removeClass("menu-opened");
		}
		
	if ($("#busingMenu ul").hasClass("open")) {			
			$("#busingMenu ul").removeClass("open").css("display","none");		
		}	
	if ($("#contractorMenu ul").hasClass("open")) {			
		$("#contractorMenu ul").removeClass("open").css("display","none");		
	}	
}

