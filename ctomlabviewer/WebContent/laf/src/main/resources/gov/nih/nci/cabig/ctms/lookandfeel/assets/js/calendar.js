/* This script dynamically loads the appropriate scripts from the calendar subdirectory
    to enable calendar support. Based on similar code in scriptaculous.  */

var CalendarInit = {
    require: function(libraryName) {
      // inserting via DOM fails in Safari 2.0, so brute force approach
      document.write('<script type="text/javascript" src="' + libraryName + '"></script>');
    },
    load: function() {
        var scriptTags = document.getElementsByTagName("script");
        for (var i = 0; i < scriptTags.length; i++) {
            if (scriptTags[i].src && scriptTags[i].src.match(/calendar\.js(\?.*)?$/)) {
                var path = scriptTags[i].src.replace(/calendar\.js(\?.*)?$/,'');
                this.require(path + 'calendar/calendar.js');
                this.require(path + 'calendar/lang/calendar-en.js');
                this.require(path + 'calendar/calendar-setup.js');
                break;
            }
        }
    }
}

CalendarInit.load();