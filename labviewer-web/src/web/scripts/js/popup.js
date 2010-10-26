
  <!--
	window.name = "mainPage";
  function wopen(url, name, w, h)
  {
  // Fudge factors for window decoration space. In my tests these work well on all platforms & browsers.
  w += 32;
  h += 96;
	var win = window.open(url,
	 name,
	 'width=' + w + ', height=' + h + ', ' +
	 'location=no, menubar=no, ' +
	 'status=no, toolbar=no, scrollbars=no, resizable=no');
	win.resizeTo(w, h);
	win.focus();
  }
  // -->

 function openPI(file,window)
 {
	childWindow = open (file,window	,'menubar=1,resizable=1,status=1,width=950,height=550');
	if (childWindow.opener == null) childWindow.opener = self;
}
