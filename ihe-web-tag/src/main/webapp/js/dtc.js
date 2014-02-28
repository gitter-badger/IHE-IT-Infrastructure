/* prototype
function showResponse(originalRequest) {
    rspText = originalRequest.responseText;
    if (rspText.length > 0) {
        result = JSON.parse(rspText);
        if (result.isSuccess) {
            alert("成功");
        } else {
            alert(result.returnMessage);
        }
    }
}

function doAjaxRequest(url, params, asyn, doOnComplete) {
    var queryString = "";
    if (params.length > 0) {
        for (var idx = 0, size = params.length; idx < size; idx++) {
            if (queryString.length > 0)
                queryString += "&";
            param = params[idx];
            queryString += (param.name + "=" + param.value);
        }
    }

    var myAjax = new Ajax.Request(url, {method: 'post', asynchronous: asyn, parameters: queryString, onComplete: doOnComplete});
}
*/

// jQuery
function doAjaxRequest(url, params, asyn, doOnComplete) {
    var queryString = "";
    if (params.length > 0) {
        for (var idx = 0, size = params.length; idx < size; idx++) {
            if (queryString.length > 0)
                queryString += "&";
            param = params[idx];
            queryString += (param.name + "=" + param.value);
        }
    }
    
    $.ajax({
        type: "POST",
        url: url,
        data: queryString,
        success: doOnComplete
      });
}

var win=null; 
//window open
//function windowOpen(mypage,myname,w,h,scroll,pos){ 
function windowOpen(mypage, myname, w, h, pos) {
    if (pos == "random") {
        LeftPosition = (screen.width) ? Math.floor(Math.random() * (screen.width - w)) : 100;
        TopPosition = (screen.height) ? Math.floor(Math.random() * ((screen.height - h) - 75)) : 100;
    }
    if (pos == "center") {
        LeftPosition = (screen.width) ? (screen.width - w) / 2 : 100;
        TopPosition = (screen.height) ? (screen.height - h) / 2 : 100;
    } else if ((pos != "center" && pos != "random") || pos == null) {
        LeftPosition = 0;
        TopPosition = 20
    }
    settings = 'width=' + w + ',height=' + h + ',top=' + TopPosition + ',left=' + LeftPosition +
               ',scrollbars=yes,location=no,directories=no,status=no,menubar=no,toolbar=no,resizable=yes';
    win = window.open("about:blank", myname, settings, true);
    win.location.href = mypage;
    win.focus();
}
//視窗最大化
function resizeWindow(win) {
  if (win.screen) {
      var w = win.screen.availWidth;
      var h = win.screen.availHeight;
      win.moveTo(0, 0);
      win.resizeTo(w, h);
  }
}
//計算天數
function dateCount(beginDate, endDate){
    var arrbeginDate, Date1, Date2, arrendDate, iDays;
    arrbeginDate=  beginDate.split("/");
    Date1 = new Date(arrbeginDate[1] + '/' + arrbeginDate[2] + '/' + arrbeginDate[0]);
    arrendDate=  endDate.split("/");
    Date2=  new  Date(arrendDate[1]  +  '/'  +  arrendDate[2]  +  '/'  +  arrendDate[0]);
    iDays = parseInt(Math.abs(Date1 - Date2)/1000/60/60/24); 
    return iDays;  
}

//取網址變數尾碼
var topLocation = window.top.location.search.substring(1);
function getParameter ( queryString, parameterName ) {
//Add "=" to the parameter name (i.e. parameterName=value)
 var parameterName = parameterName + "=";

  if ( queryString.length > 0 ) {
      // Find the beginning of the string
      begin = queryString.indexOf ( parameterName );
      // If the parameter name is not found, skip it, otherwise return the value
      if ( begin != -1 ) {
      // Add the length (integer) to the beginning
          begin += parameterName.length;
          // Multiple parameters are separated by the "&" sign
          end = queryString.indexOf ( "&" , begin );
          if ( end == -1 ) {
              end = queryString.length
          }
          // Return the string
          return unescape ( queryString.substring ( begin, end ) );
      }
      // Return "null" if no parameter has been found
      return "null";
  }
}

function doPrint() {
    window.print();
}

//回傳IE版本
function msieversion() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");
    if ( msie >= 0 )        // is Microsoft Internet Explorer; return version number
        return parseFloat(ua.substring(msie+5, ua.indexOf(";", msie)));
    else
        return 0;    // is other browser
}
  
function doIframeOnload() {
}

function findContainer(currentWindow) {
    if (currentWindow.isContainer)
        return currentWindow;
    if (currentWindow.parent && currentWindow.parent != currentWindow)
        return findContainer(currentWindow.parent);
    return null;
}

function findPortal(currentWindow) {
    if (currentWindow.isPortal)
        return currentWindow;
    if (currentWindow.parent)
        return findPortal(currentWindow.parent);
    return null;
}

function findIndex(currentWindow) {
    if (currentWindow.isIndex)
        return currentWindow;
    if (currentWindow.parent)
        return findIndex(currentWindow.parent);
    return null;
}

function findLogin(currentWindow) {
    if (currentWindow.isLogin)
        return currentWindow;
    if (currentWindow.parent)
        return findLogin(currentWindow.parent);
    return null;
}

