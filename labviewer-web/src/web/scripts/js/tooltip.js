
var ToolTipEvents = {
	offsetLeft : (-180),
	offsetTop : (15),
	posRef : function(){
		return (
			(document.documentElement.scrollTop)?
				document.documentElement : document.body
			);
	},
	showTooltip : function(e){
		ToolTipEvents._cleanup();
		BubbleTips.bubbleNode.appendChild(this.tooltip);
		Effects.fadeIn(this.tooltip,BubbleTips.opacity);
		ToolTipEvents.followMouse(e);
	},
	hideTooltip : function(e){
		Effects.fadeOut(this.tooltip, ToolTipEvents._cleanup);
	},
	followMouse : function(e){
		if(e == null){ e = window.event };
		var posx = ToolTipEvents.offsetLeft;
		var posy = ToolTipEvents.offsetTop;
		if(e.pageX || e.pageY){
			posx += e.pageX;
			posy += e.pageY;
		} else if(e.clientX || e.clientY) {
			posx += e.clientX + ToolTipEvents.posRef().scrollLeft;
			posy += e.clientY + ToolTipEvents.posRef().scrollTop;
		}
		BubbleTips.bubbleNode.style.top  = (posy) + "px";
		BubbleTips.bubbleNode.style.left = (posx) + "px";
	},
	_cleanup : function(){
		var bubble = BubbleTips.bubbleNode;
		if( bubble.childNodes.length > 0 ){
			bubble.removeChild(bubble.firstChild);
		}
	}
};

var Effects = {
	fadeIn : function (elem,maxOpac){
		elem.fadeIn = Effects._fadeIn;
		elem.maxOpac = maxOpac;
		elem.curOpac = 0;
		this.cancelCurrent();
		elem.fadeIn();
	},
	fadeOut : function (elem,fadeDoneF){
		elem.fadeOut = Effects._fadeOut;
		elem.fadeOutDone = fadeDoneF;
		this.cancelCurrent();
		elem.fadeOut();
	},
	cancelCurrent : function() {
		clearTimeout(window.evtId);
	},
	_fadeIn : function() {
		if( (+this.curOpac) < (+this.maxOpac) ){
			this.curOpac = (+this.curOpac)+(0.15);
			Effects.setOpacity(this,this.curOpac);
			window.fadeInElem = this;
			window.evtId = setTimeout(function(){this.fadeInElem.fadeIn()},30);
		} else {
			Effects.setOpacity(this,this.maxOpac);
			window.fadeInElem = null;
		}
	},
	_fadeOut : function() {
		if( (+this.curOpac) > 0 ){
			this.curOpac = Math.max(0,(+this.curOpac)-(0.15));
			Effects.setOpacity(this,this.curOpac);
			window.fadeOutElem = this;
			window.evtId = setTimeout(function(){this.fadeOutElem.fadeOut()},30);
		} else if(this.fadeOutDone) {
			this.fadeOutDone();
			window.fadeOutElem = null;
		}
	},
	setOpacity : function (elem,opac){
		elem.style.filter="alpha(opacity:"+ ((+opac)*100) +")";
		elem.style.KHTMLOpacity=opac;
		elem.style.MozOpacity=opac;
		elem.style.opacity=opac;
	}
};

var BubbleTips = {
	opacity : "0.90",
	bubbleNode : null,
	activateTipOn : function(type){
		var bubble = document.createElement("span");
		bubble.style.position = "absolute";
		bubble.style.zIndex = "9";
		this.bubbleNode = bubble;
		document.getElementsByTagName("body")[0].appendChild(bubble);
		var tipTags = document.getElementsByTagName(type);
		for(var i=0;i<tipTags.length;i++){
			this.bindBubbleTip(tipTags[i]);
		}
	},
	bindBubbleTip : function(elem) {
		var tipText=elem.getAttribute("title");
		if(tipText==null || tipText.length==0){
			tipText="Tooltip description coming soon.";
		}
		elem.removeAttribute("title");
		var bubble = this.createElem("span","bubbleTooltip");
		var tipTop = this.createElem("span","top");
		tipTop.appendChild(document.createTextNode(tipText));
		bubble.appendChild(tipTop);
		bubble.appendChild(this.createElem("span","bottom"));
		Effects.setOpacity(bubble,this.opacity);
		elem.tooltip = bubble;
		elem.onmouseover = ToolTipEvents.showTooltip;
		elem.onmouseout  = ToolTipEvents.hideTooltip;
		elem.onmousemove = ToolTipEvents.followMouse;
	},
	createElem : function(tag,className){
		var elem = document.createElement(tag);
		elem.className = className;
		elem.style.display = "block";
		return elem;
	}
};


		

//window.onload=function(){
//BubbleTips.activateTipOn("acronym");
//BubbleTips.activateTipOn("dfn");
//};