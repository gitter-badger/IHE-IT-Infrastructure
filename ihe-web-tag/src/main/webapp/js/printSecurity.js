/*封鎖右鍵  設在body也行
/ONDRAGSTART="window.event.returnValue=false" 
/ONCONTEXTMENU="window.event.returnValue=false" 
onSelectStart="event.returnValue=false"


*/

document.ondragstart = function () { 
    return false; 
}
document.oncontextmenu = function () { 
    return false; 
}
document.onselectstart = function () {
    return false; 
}

/*封鎖熱鍵列印
var onCtrl = 0;
var browser=navigator.appName;
if(browser=="Netscape"){
    document.captureEvents(Event.KEYDOWN);
    document.onkeydown=function(event){
        if(event.keyCode==17){
            onCtrl = 1;
            return true;
        } else if(event.keyCode==80 && onCtrl==1){
            alert("請勿使用熱鍵進行網頁列印!");
            onCtrl = 0;
            return false;
        }
    }
} else {
    document.onkeydown = function(){
        if(event.keyCode==17){
            onCtrl = 1;
            return true;
        } else if(event.keyCode==80 && onCtrl==1){
            alert("請勿使用熱鍵進行網頁列印!");
            onCtrl = 0;
            return false;
        }
    }
}*/
/*鍵盤 滑鼠 全部封鎖*/
//document.onmousedown=click;
document.onkeydown=click;

/* 
 * document.layers == document.all
if (document.layers) {
    window.captureEvents(Event.MOUSEDOWN);
}
window.onmousedown=click;
*/
if (document.layers) {
    window.captureEvents(Event.KEYDOWN);
}
window.onkeydown=click;

function click(e){
    if (navigator.appName == 'Netscape'){
        if (e.which != 1){
            alert("you can't use mouse and keyboard !");     
            return false;
        }
    } else {
        //event.button 1.left 2.right
        if (event.button != 1) {
            alert("you can't use mouse and keyboard !");
            return false;
        }
    }
}

