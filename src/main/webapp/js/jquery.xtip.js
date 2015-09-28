/*

	jquery.xtip 1.0 for jQuery 1.7

	this work is licensed under bobef license:
	USE WITHOUT RESTRICTIONS AT YOUR OWN RESPONSIBILITY

	originaly created for *undisclosed at this time* by bobef; http//bobef.net

*/

(function(){

	var defaults = {
		content: null,						//string or dom element
		timeout: 15, 						//hide the tip after this number of seconds. <= 0 means stay forever
		arrow: true,						// true, 'big', false
		hoverHide: true, 					//hide the tip when mouse enter and then leave it
		hoverArea: 0,						//if hoverHide is true and this is > 0 creates an area of this size bigger than the tip and only hide the tip when mouse leaves this area, not just the tip
		hoverAreaIncludeElement: true,		//the area will extend around the tip and around the element
		clickHide: true,					//if to hide the tip when the document is clicked outside of it
		exclusive: true,					//true hides all other tips before showing this one
		edgeOffset: 3,						//minimum offset of the window edges
		position: 'bottom',					// position: left|right|center, align: top|bottom|center || position: top|bottom|center align: left|right|center
		align: 'left',
		attr: 'title',						//if content is empty tries to take the content from this attribute
		removeAttr: true,					//if to remove the attribute after getting its value
		css: {'max-width': '300px'},		//css to be applied on the tip holder element
		'class': null,						//class name to add to the tip holder
		hoverAreaClass: null,				//class name to add to the tip hover area
		fadeIn: 'slow',
		fadeOut: 'slow',
		bindEvent: null,					//over | enter | focus | click - trigger the tip on this event on the element
		bindEventOut: true,					//if to hide the tip on out event (click doesn't have out event though)
		delay: 0,							//used with bindEvent. how long to wait before displaying the tip
		onShow: null,						//callback
		onHide: null,						//callback
		outResult: null						//provide object here to be filled with the tip structure in the tip member. used for advanced control
	};
	
	var id = 0;
	var tips = {};
	var activetips = 0;
	
	function docclick(e) {
		if(activetips > 0) {
			var target = jQuery(e.target);
			if(!target.data('xtip') && target.closest('.xtip').length == 0) {
				for(var i in tips) {
					deactivate(tips[i], undefined, undefined, true);
					delete tips[i];
				}
			}
		}
	}
	
	function activate(element, options) {

		//construct the tip
		
		var tip = {
			id: 0,
			element: element,
			holder: null,
			content: null,
			arrow: null,
			hoverArea: null,
			container: null,
			arrowWidth: 12,
			arrowHeight: 12,
			borderRadius: {topLeft: 0, topRight: 0, bottomLeft: 0, bottomRight: 0},
			timeout: null,
			mouseLeave: (options.onLeave instanceof Function ? options.onLeave : function() {tip.element.hideTip();}),
			options: options,
			isAlive: true
		};
		
		//check content first
		
		if(tip.options.content instanceof Function) {
			tip.options.content = tip.options.content.call(tip.element);
			if(tip.options.content === false) return;
		}
	
		if(!tip.options.content) {
			if(typeof tip.options.attr == 'string') {
				tip.options.content = tip.element.attr(tip.options.attr);
				if(tip.options.removeAttr) tip.element.attr(tip.options.attr, null);
			}
			if(typeof tip.options.content != 'string' || tip.options.content.length == 0) return;
		}
		
		///////////////
		
		if(tip.options.exclusive) jQuery.hideAllTips();
		else tip.element.hideTip();
		
		if(id == 0) jQuery(document).on('click', docclick);
		
		tip.id = ++id;
		tip.holder = jQuery('<div class="xtip xtip-holder" style="z-index: 9999; position: absolute;" />').hide();
		tip.content = jQuery('<div class="xtip-content" />');
		
		if(tip.options.hoverArea === true) tip.options.hoverArea = 20;
		
		if(typeof options['class'] == 'string') tip.holder.addClass(options['class']);
		if(tip.options.arrow == 'big') tip.holder.addClass('big-arrow');
		tip.options.css = tip.holder.css(tip.options.css);
		
		jQuery('body').append(tip.holder.append(tip.content));
		
		if(tip.options.arrow != false) {
		
			tip.arrow = jQuery('<div class="xtip-arrow"><div class="xtip-arrow-inner" /></div>');
			tip.holder.prepend(tip.arrow);
		
			tip.arrowWidth = parseInt(tip.arrow.css('border-left-width')) + parseInt(tip.arrow.css('border-right-width'));
			tip.arrowHeight = parseInt(tip.arrow.css('border-top-width')) + parseInt(tip.arrow.css('border-bottom-width'));

			tip.borderRadius = {
				topLeft: parseInt(tip.content.css('border-top-left-radius')),
				topRight: parseInt(tip.content.css('border-top-right-radius')),
				bottomLeft: parseInt(tip.content.css('border-bottom-left-radius')),
				bottomRight: parseInt(tip.content.css('border-bottom-right-radius'))
			};
		}

		if(tip.options.scrollTo == 'center') tip.options.scrollTo = {center: true};
		
		tips[tip.id] = tip;
		tip.element.data('xtip', tip.id);
		
		function mouseleave() {
			tip.mouseLeave.call(tip);
		}
		
		if(tip.options.outResult instanceof Object) tip.options.outResult.tip = tip;
		
		//procceed with the tip display
		
		tip.content.append(tip.options.content);
		
		var arrowtop = 0;
		var arrowleft = 0;
		var tiptop = 0;
		var tipleft = 0;
		var offset = tip.element.offset();
		var tipw = tip.holder.outerWidth();
		var tiph = tip.holder.outerHeight();
		var elw = tip.element.outerWidth();
		var elh = tip.element.outerHeight();
		var win = jQuery(window);
		var winscrollleft = win.scrollLeft() + tip.options.edgeOffset;
		var winscrolltop = win.scrollTop() + tip.options.edgeOffset;
		var winw = win.width() - tip.options.edgeOffset;
		var winh = win.height() - tip.options.edgeOffset;
		var align = tip.options.align;
		var position = tip.options.position;
		var positionclass = null;
		
		function position_top() {
			arrowtop = tiph;
			tiptop = offset.top - (tiph + tip.arrowHeight);
			positionclass = 'top';
		}
		
		function position_bottom() {
			arrowtop = -tip.arrowWidth;
			tiptop = offset.top + elh + tip.arrowHeight;
			positionclass = 'bottom';
		}
		
		function position_left() {
			arrowleft = tipw;
			tipleft = offset.left - (tipw + tip.arrowWidth);
			positionclass = 'left';
		}
		
		function position_right() {
			arrowleft = -tip.arrowWidth;
			tipleft = offset.left + elw + tip.arrowWidth;
			positionclass = 'right';
		}
		
		function position_center_v() {	
			tiptop = offset.top + (elh - tiph) / 2;
			positionclass = 'center';
		}
		
		function position_center_h() {	
			tipleft = offset.left + (elw - tipw) / 2;
			positionclass = 'center';
		}
		
		function position_original_v() {
			if(position == 'top') position_top();
			else if(position == 'bottom') position_bottom();
			else position_center_v();
		}
		
		function position_original_h() {
			if(position == 'left') position_left();
			else if(position == 'right') position_right();
			else position_center_h();
		}
		
		function center_arrow_h() {
			arrowleft = tipw / 2 - tip.arrowWidth / 2;
		}
		
		function center_arrow_v() {
			arrowtop = tiph / 2 - tip.arrowHeight / 2;
		}
		
		function limit_position_v(flags) {	
			var TRIED_TOP = 1;
			var TRIED_BOTTOM = 2;
			var exitst = tiptop <= winscrolltop;
			var exitsb = tiptop + tiph >= winscrolltop + winh;
			if(exitst || exitsb) {
				if(exitst && exitsb) {
					tiptop = winscrolltop + winh / 2 - tiph / 2;
					center_arrow_h();
				}
				else {
					if((flags&TRIED_TOP) == 0 && position != 'top') {
						position_top();
						limit_position_v(flags|TRIED_TOP);
					}
					else if((flags&TRIED_BOTTOM) == 0 && position != 'bottom') {
						position_bottom();
						limit_position_v(flags|TRIED_BOTTOM);
					}
					else position_original_v();
				}
			}
		}
		
		function limit_position_h(flags) {	
			var TRIED_LEFT = 1;
			var TRIED_RIGHT = 2;
			var exitsl = tipleft <= winscrollleft;
			var exitsr = tipleft + tipw >= winscrollleft + winw;
			if(exitsl || exitsr) {
				if(exitsl && exitsr) {
					tipleft = winscrollleft + winw / 2 - tipw / 2;
					center_arrow_v();
				}
				else {
					if((flags&TRIED_LEFT) == 0 && position != 'left') {
						position_left();
						limit_position_h(flags|TRIED_LEFT);
					}
					else if((flags&TRIED_RIGHT) == 0 && position != 'right') {
						position_right();
						limit_position_h(flags|TRIED_RIGHT);
					}
					else position_original_h();
				}
			}
		}
		
		function limit_arrow_h() {
			var max = tipw - tip.arrowWidth - (position == 'top' ? tip.borderRadius.topRight : tip.borderRadius.bottomRight);
			var min = (position == 'top' ? tip.borderRadius.topLeft : tip.borderRadius.bottomLeft);
			if(arrowleft > max || arrowleft < min) center_arrow_h();
		}
		
		function limit_arrow_v() {
			var max = tiph - tip.arrowHeight - (position == 'left' ? tip.borderRadius.bottomLeft : tip.borderRadius.bottomRight);
			var min = (position == 'left' ? tip.borderRadius.topLeft : tip.borderRadius.topRight);
			if(arrowtop > max || arrowtop < min) center_arrow_v();
		}
		
		function align_left() {
			tipleft = offset.left;
			arrowleft = (position == 'top' ? tip.borderRadius.topLeft : tip.borderRadius.bottomLeft) + elw / 2 - tip.arrowWidth / 2;
		}
		
		function align_right() {
			tipleft = offset.left + (elw - tipw);
			arrowleft = (tipw - elw) + ((position == 'top' ? tip.borderRadius.topRight : tip.borderRadius.bottomRight) + elw / 2 - tip.arrowWidth / 2);
		}
		
		function align_top() {
			tiptop = offset.top;
			arrowtop = (position == 'left' ? tip.borderRadius.topLeft : tip.borderRadius.topRight) + elh / 2 - tip.arrowHeight / 2;
		}
		
		function align_bottom() {
			tiptop = offset.top + (elh - tiph);
			arrowtop = (tiph - elh) + ((position == 'left' ? tip.borderRadius.bottomLeft : tip.borderRadius.bottomRight) + elh / 2 - tip.arrowHeight / 2);
		}
		
		function align_center_h() {	
			tipleft = offset.left + (elw - tipw) / 2;
			center_arrow_h();
		}
		
		function align_center_v() {	
			tiptop = offset.top + (elh - tiph) / 2;
			center_arrow_v();
		}
		
		function align_original_h() {
			if(align == 'left') align_left();
			else if(align == 'right') align_right();
			else align_center_h();
		}
		
		function align_original_v() {
			if(align == 'top') align_top();
			else if(align == 'bottom') align_bottom();
			else align_center_v();
		}
		
		function limit_align_h(flags) {	
			var TRIED_LEFT = 1;
			var TRIED_RIGHT = 2;
			var exitsl = tipleft <= winscrollleft;
			var exitsr = tipleft + tipw >= winscrollleft + winw;
			if(exitsl || exitsr) {
				if(exitsl && exitsr) {
					tipleft = winscrollleft + winw / 2 - tipw / 2;
					center_arrow_h();
				}
				else {
					if((flags&TRIED_LEFT) == 0 && align != 'left') {
						align_left();
						limit_align_h(flags|TRIED_LEFT);
					}
					else if((flags&TRIED_RIGHT) == 0 && align != 'right') {
						align_right();
						limit_align_h(flags|TRIED_RIGHT);
					}
					else align_original_h();
				}
			}
		}
		
		function limit_align_v(flags) {	
			var TRIED_TOP = 1;
			var TRIED_BOTTOM = 2;
			var exitst = tiptop <= winscrolltop;
			var exitsb = tiptop + tiph >= winscrolltop + winh;
			if(exitst || exitsb) {
				if(exitst && exitsb) {
					tiptop = winscrolltop + winh / 2 - tiph / 2;
					center_arrow_v();
				}
				else {
					if((flags&TRIED_TOP) == 0 && align != 'top') {
						align_top();
						limit_align_v(flags|TRIED_TOP);
					}
					else if((flags&TRIED_BOTTOM) == 0 && align != 'bottom') {
						align_bottom();
						limit_align_v(flags|TRIED_BOTTOM);
					}
					else align_original_v();
				}
			}
		}
		
		if(position == 'bottom' || position == 'top') {
			if(align == 'top') align = 'left';
			else if(align == 'bottom') align = 'right';
			position_original_v();
			limit_position_v(0);
			align_original_h();
			limit_align_h(0);
			if(tip.options.arrow != false) limit_arrow_h();
		}
		else /*(position == 'left' || position == 'right')*/ {
			if(align == 'left') align = 'top';
			else if(align == 'right') align = 'bottom';
			position_original_h();
			limit_position_h(0);
			align_original_v();
			limit_align_v(0);
			if(tip.options.arrow != false) limit_arrow_v();
		}

		tip.holder.css({'left': tipleft + 'px', 'top': tiptop + 'px'}).addClass('xtip-' + positionclass);
		if(tip.options.arrow != false) tip.arrow.css({'left': arrowleft + 'px', 'top': arrowtop + 'px'});
			
		if(tip.options.hoverArea > 0) {
			tip.hoverArea = jQuery('<div class="xtip-hover-area" style="position: absolute;" />').hide();
			if(typeof tip.options.hoverAreaClass == 'string') tip.hoverArea.addClass(tip.options.hoverAreaClass);

			var r1 = {left: tipleft, top: tiptop, right: tipleft + tipw, bottom: tiptop + tiph};
			var r2 = tip.options.hoverAreaIncludeElement ? {left: offset.left, top: offset.top, right: offset.left + elw, bottom: offset.top + elh} : r1;
			var r = {
				top: (r1.top < r2.top ? r1.top : r2.top) - tip.options.hoverArea,
				right: (r1.right > r2.right ? r1.right : r2.right) + tip.options.hoverArea,
				bottom: (r1.bottom > r2.bottom ? r1.bottom : r2.bottom) + tip.options.hoverArea,
				left: (r1.left < r2.left ? r1.left : r2.left) - tip.options.hoverArea
			};
			var limit = {top: winscrolltop, right: winscrollleft + winw, bottom: winscrolltop + winh, left: winscrollleft};
			if(r.top < limit.top) r.top = limit.top;
			if(r.right > limit.right) r.right = limit.right;
			if(r.bottom > limit.bottom) r.bottom = limit.bottom;
			if(r.left < limit.left) r.left = limit.left;
			
			tip.hoverArea.css({
				left: (r.left) + 'px',
				top: (r.top) + 'px',
				width: (r.right - r.left) + 'px',
				height: (r.bottom - r.top) + 'px',
				'z-index': tip.holder.css('z-index') - 1
			})
			.appendTo(jQuery('body'))
			.append(tip.holder.css({left: r1.left - r.left, top: r1.top - r.top}));
			
			tip.container = tip.hoverArea;
		}
		else {
			tip.container = tip.holder;
		}
		
		if(tip.options.timeout > 0) {
			tip.timeout = setTimeout(mouseleave, tip.options.timeout * 1000);
			
			var c;
			if(tip.options.hoverArea > 0 && tip.options.hoverAreaIncludeElement) c = tip.hoverArea;
			else c = tip.holder;
			
			c.on('mouseenter', function() {
				if(tip.timeout) {
					clearTimeout(tip.timeout);
					tip.timeout = null;
				}	
			});
			c.on('mouseleave', function() {
				if(tip.isAlive && !tip.options.hoverHide) tip.timeout = setTimeout(mouseleave, tip.options.timeout * 1000);
			});
		}
		
		if(tip.options.hoverHide) {
			if(tip.options.hoverArea > 0) {
				if(tip.options.hoverAreaIncludeElement) tip.hoverArea.one('mouseleave', mouseleave);
				else tip.holder.one('mouseleave', function() {tip.hoverArea.one('mouseleave', mouseleave);});
			}
			else {
				tip.holder.one('mouseleave', mouseleave);
			}
		}
		
		tip.container.addClass('xtip');
		tip.container.data('xtip', tip.id);
		if(tip.container !== tip.holder) tip.holder.show();
		tip.container.fadeIn(tip.options.fadeIn);
		
		++activetips;
		
		if(tip.options.onShow instanceof Function) tip.options.onShow.call(tip);
	}
	
	function deactivate(tip, callback, immediate, fromclick) {
		if(!tip) return;

		if(fromclick && !tip.options.clickHide) return;
		
		if(immediate == undefined && !(callback instanceof Function)) {
			immediate = callback;
			callback = null;
		}
		
		tip.element.data('xtip', null);
		if(tip.timeout) {
			clearTimeout(tip.timeout);
			tip.timeout = null;
		}
		
		function done(element) {
			if(tip.options.onHide instanceof Function) tip.options.onHide.call(tip);
			if(callback instanceof Function) callback.call(tip);
			tip.isAlive = false;
			tip.holder = tip.arrow = tip.content = tip.hoverArea = null;
			element.remove();
			
			--activetips;
		}
		
		if(immediate) done(tip.container);
		else tip.container.fadeOut(tip.options.fadeOut, function(){done(jQuery(this));});
	}
	
	jQuery.stopAllTips = function() {
		jQuery('.xtip').stop(true, true);
	}

	jQuery.hideAllTips = function(callback, immediate) {
		var tmp = tips;
		tips = {};
		for(var i in tmp) deactivate(tmp[i], callback, immediate);
		return this;
	};
	
	jQuery.fn.stopTip = function() {
		this.each(function() {
			var me = jQuery(this);
			var tip = null;
			if(me.hasClass('xtip')) tip = me;
			else {
				var id = me.data('xtip');
				if(id > 0) jQuery('.xtip').each(function() {
					var me = jQuery(this);
					var id = me.data('xtip');
					if(id > 0) {
						tip = me;
						return false;
					}
				});
			}
			if(tip) tip.stop(true, true);
		});
		return this;
	};
	
	jQuery.fn.hideTip = function(callback, immediate) {
		this.each(function() {
			var me = jQuery(this);
			var tip = me.data('xtip');
			if(tip > 0) {
				deactivate(tips[tip], callback, immediate);
				delete tips[tip];
			}
		});
		return this;
	};
	
	jQuery.fn.showTip = function(content, _options) {
		
		var event = null;
		var delay = 0;
		var bindeventout = true;

		if(!(content instanceof Function) && content instanceof Object && !content.jquery) {
			_options = content;
			content = null;
		}
		
		var options = jQuery.extend({}, defaults, _options); //make copy of options so we don't modify the original object
		
		if(options instanceof Object) {
			if(content) options.content = content;
			event = options.bindEvent;
			delay = options.delay;
			bindeventout = options.bindEventOut && (event == 'focus' || event != 'click');
		}

		if(event) {
			delete options.bindEvent;
			delete options.bindEventOut;
			delete options.delay;
				
			this.each(function() {
				var me = jQuery(this);
				var mycontent = content || options.content;
				var myoptions = jQuery.extend({}, defaults, options);
				
				if(!mycontent && typeof myoptions.attr == 'string') {
					mycontent = me.attr(myoptions.attr);
					if(myoptions.removeAttr) me.attr(myoptions.attr, null);
				}				
				myoptions.content = mycontent;
				
				var timeout = null;
				var show, hide;
				
				show = function() {
					if(timeout === null) {
						if(delay > 0) {
							timeout = setTimeout(show, delay);
							return;
						}
					}
					else {
						clearTimeout(timeout);
						timeout = null;
					}
					me.showTip(myoptions);
				};
				
				hide = function() {
					if(timeout !== null) {
						clearTimeout(timeout);
						timeout = null;
					}
					if(bindeventout) me.hideTip();
				};
				
				if(event == 'focus') {
					me.on('focusin', show);
					me.on('focusout', hide);
				}
				else if(event == 'over') {
					me.on('mouseover', show);
					me.on('mouseout', hide);
				}
				else if(event == 'enter') {
					me.on('mouseenter', show);
					me.on('mouseleave', hide);
				}
				else if(event == 'click') {
					me.on('click', function(){me.showTip(myoptions);});
				}
			});
		}
		else {
			activate(this.first(), options);
		}
		
		return this;
	};

})();