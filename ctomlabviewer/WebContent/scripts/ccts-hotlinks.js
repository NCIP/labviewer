/*
 * Hotlink code for CCTS.  
 * (See https://wiki.nci.nih.gov/display/CTMS/CCTS+Hotlink+Windows .)
 * 
 * This implements scenario 3 for hotlinking: Each application opens in 
 * its own window and is automatically focused.  The other two scenarios
 * can be implemented with plain HTML.
 * 
 * Requirements: 
 *   - Relies on protoype.js version 1.6 or later.
 *     http://www.prototypejs.org/
 *   - Each application must have a javascript global named
 *     CCTS.appShortName whose value is the link name for that application.
 *     I.e., "caaers" for caAERS, "psc" for PSC, etc.
 * 
 * Limitations:
 *   - If the user opens two application windows independently (e.g.,
 *     from bookmarks or typing the URLs), hotlinks from one to the
 *     other will not work.  This is a side-effect of the Javascript
 *     security model and probably cannot be rectified.
 *   - If the user has configured the browser to open new windows in tabs,
 *     a link to an already-application tab will not result in the target tab
 *     coming to the front.  This happens in both Firefox 2 and IE7 -- it
 *     appears to be by design.
 *
 * Author: Rhett Sutphin
 */
 
if (!window.CCTS) {
  CCTS = { }
}

Object.extend(CCTS, {
  setUpHotlink: function(anchor) {
    if (anchor.target.substring(0,1) == '_') return;
    $(anchor).observe('click', CCTS.hotlink)
  },
  
  hotlink: function(clickEvt) {
    Event.stop(clickEvt)
    var a = Event.element(clickEvt)
    var w = CCTS.findWindow(a.target);
    if (w) {
      w.location.href = a.href
    } else {
      w = window.open(a.href, a.target)
    }
    w.focus()
  },
  
  findWindow: function(appShortName) {
    if (CCTS.appShortName == appShortName) {
      return window;
    } else if (window.opener && !window.opener.closed && window.opener.CCTS && window.opener.CCTS.findWindow) {
      return window.opener.CCTS.findWindow(appShortName);
    } else {
      return null;
    }
  }
})

$(document).observe('dom:loaded', function() {
  // Search for and register hotlinks
  $$("a[target]").each(CCTS.setUpHotlink)
})
