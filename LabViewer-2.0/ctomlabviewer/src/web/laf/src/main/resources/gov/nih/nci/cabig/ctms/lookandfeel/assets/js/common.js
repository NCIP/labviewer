////// NAMESPACE 
// for ctms-commons shared functions and classes
var CTMS = { }

////// INDICATOR HANDLING

CTMS.INDICATOR_REF_COUNTS = { };

// this stuff should technically be synchronized.  Let see if it causes a problem.
CTMS.showIndicator = function(id) {
    if (!CTMS.INDICATOR_REF_COUNTS[id]) CTMS.INDICATOR_REF_COUNTS[id] = 0;
    CTMS.INDICATOR_REF_COUNTS[id] += 1
    CTMS.updateIndicatorVisibility(id)
}

CTMS.hideIndicator = function(id) {
    if (!CTMS.INDICATOR_REF_COUNTS[id]) CTMS.INDICATOR_REF_COUNTS[id] = 0;
    CTMS.INDICATOR_REF_COUNTS[id] -= 1;
    if (CTMS.INDICATOR_REF_COUNTS[id] < 0) CTMS.INDICATOR_REF_COUNTS[id] = 0;
    CTMS.updateIndicatorVisibility(id)
}

CTMS.updateIndicatorVisibility = function(id) {
    if (CTMS.INDICATOR_REF_COUNTS[id] > 0) {
        $(id).reveal();
    } else {
        $(id).conceal();
    }
}

////// CALENDAR POPUP HANDLERS

CTMS.registerCalendarPopups = function(containerId) {
    var sel = "input.date"
    if (containerId) sel = "#" + containerId + " " + sel
    $$(sel).each(function(input) {
        var anchorId = input.id + "-calbutton"
        Calendar.setup(
            {
                inputField  : input.id,
                button      : anchorId,
                ifFormat    : "%m/%d/%Y", // TODO: get this from the configuration
                weekNumbers : false
            }
        );
    })
}

Element.observe(window, "load", function() {
    CTMS.registerCalendarPopups()
});

////// PROTOTYPE EXTENSIONS

Element.addMethods( {
    // Like prototype's hide(), but uses the visibility CSS prop instead of display
    conceal: function() {
        for (var i = 0; i < arguments.length; i++) {
            var element = $(arguments[i]);
            element.style.visibility = 'hidden';
        }
    },

    // Like prototype's show(), but uses the visibility CSS prop instead of display
    reveal: function() {
        for (var i = 0; i < arguments.length; i++) {
            var element = $(arguments[i]);
            element.style.visibility = 'visible';
        }
    },

    // Disable all form elements contained in this element and add the class "disabled"
    disableDescendants: function() {
        for (var i = 0; i < arguments.length; i++) {
            var element = $(arguments[i]);
            element.addClassName("disabled")
            element.descendants().each(function(elt) {
                if (elt.disable) elt.disable()
            })
        }
    },

    // Enable all form elements contained in this element and remove the class "disabled"
    enableDescendants: function() {
        for (var i = 0; i < arguments.length; i++) {
            var element = $(arguments[i]);
            element.removeClassName("disabled")
            element.descendants().each(function(elt) {
                if (elt.enable) elt.enable()
            })
        }
    }
} );

