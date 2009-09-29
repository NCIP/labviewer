CTMS.tabbedFlowUpdateTarget = function(evt) {
    var a = Event.element(evt)
    var tabclass = Element.classNames(a).detect(function(cls) { return cls.slice(0, 3) == "tab" })
    var targetPage = tabclass.slice(3)
    $('_target').name = "_target" + targetPage
    if ($('command')._finish) $('command')._finish.disable()
}

CTMS.tabbedFlowSelectAndSubmit = function(click) {
    Event.stop(click)
    CTMS.tabbedFlowUpdateTarget(click)
    $('command').submit() // command is the default ID for a form created with form:form
}

Event.observe(window, "load", function() {
    $$("li.tab a").each(function(a) {
        Event.observe(a, "click", CTMS.tabbedFlowSelectAndSubmit)
    })
    if ($("flow-prev")) Event.observe("flow-prev", "click", CTMS.tabbedFlowUpdateTarget)
    if ($("flow-update")) Event.observe("flow-update", "click", CTMS.tabbedFlowUpdateTarget)
})