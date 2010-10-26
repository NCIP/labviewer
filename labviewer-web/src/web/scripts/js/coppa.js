function setFocusToFirstControl() {
    for (var f=0; f < document.forms.length; f++) {
        for(var i=0; i < document.forms[f].length; i++) {
            var elt = document.forms[f][i];
            if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
                try {
                    elt.focus();
                    return;
                } catch(er) {
                }
            }
        }
    }
}
