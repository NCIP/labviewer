// Copyright (c) 2004-2004 Quadralay Corporation.  All rights reserved.
//

function WWHAPI_Object(ParamHelpURL,
                       ParamTargetWindow)
{
  // Location of the WebWorks Help system to be launched.
  // Location should be an absolute URL.
  //
  this.mAPIURL       = ParamHelpURL + '/wwhelp/wwhimpl/api.htm';
  this.mWindow       = null;
  this.mTargetWindow = ParamTargetWindow;

  this.fLaunchHelp                = WWHAPI_LaunchHelp;
  this.fCloseHelp                 = WWHAPI_CloseHelp;
  this.fDisplayHelp               = WWHAPI_DisplayHelp;
  this.fDisplayHelpWithNavigation = WWHAPI_DisplayHelpWithNavigation;
  this.fDisplayHelpWithContents   = WWHAPI_DisplayHelpWithContents;
  this.fDisplayHelpWithIndex      = WWHAPI_DisplayHelpWithIndex;
  this.fDisplayHelpWithSearch     = WWHAPI_DisplayHelpWithSearch;
  this.fDisplayHelpWithFavorites  = WWHAPI_DisplayHelpWithFavorites;
  this.fDisplayHelpNavigation     = WWHAPI_DisplayHelpNavigation;
  this.fDisplayHelpContents       = WWHAPI_DisplayHelpContents;
  this.fDisplayHelpIndex          = WWHAPI_DisplayHelpIndex;
  this.fDisplayHelpSearch         = WWHAPI_DisplayHelpSearch;
  this.fDisplayHelpFavorites      = WWHAPI_DisplayHelpFavorites;
}

function WWHAPI_LaunchHelp(bParamSingle,
                           ParamContext,
                           ParamTopic,
                           ParamTab)
{
  var  VarParameters;
  var  VarURL;
  var  VarIndex;
  var  VarMaxIndex;


  // Define parameters
  //
  VarParameters = new Array();
  if (bParamSingle)
  {
    VarParameters[VarParameters.length] = "single=true";
  }
  if (ParamContext != null)
  {
    VarParameters[VarParameters.length] = "context=" + ParamContext;
  }
  if (ParamTopic != null)
  {
    VarParameters[VarParameters.length] = "topic=" + ParamTopic;
  }
  if (ParamTab)
  {
    VarParameters[VarParameters.length] = "tab=" + ParamTab;
  }

  // Create URL
  //
  VarURL = this.mAPIURL;
  for (VarIndex = 0, VarMaxIndex = VarParameters.length ; VarIndex < VarMaxIndex ; VarIndex++)
  {
    if (VarIndex == 0)
    {
      VarURL += "?" + VarParameters[VarIndex];
    }
    else
    {
      VarURL += "&" + VarParameters[VarIndex];
    }
  }

  // Check to see if the window exists.  If it does, make the native call directly.
  // Otherwise, initialize the help system at the topic using URL params.
  //
  if ((this.mWindow != null) &&
      (typeof(this.mWindow) != "undefined") &&
      ( ! this.mWindow.closed) &&
      (this.mWindow.WWHHelp != null) &&
      (typeof(this.mWindow.WWHHelp) != "undefined"))
  {
    this.mWindow.focus();
    this.mWindow.WWHHelp.fSetContextDocument(VarURL);
  }
  else
  {
    // Use target window
    //
    if ((typeof(this.mTargetWindow) != "undefined") &&
        (this.mTargetWindow != null))
    {
      this.mWindow = window.open(VarURL, this.mTargetWindow);
    }
    else
    {
      this.mWindow = window.open(VarURL);
    }

    // Ensure window is brought to the foreground
    //
    this.mWindow.focus();
  }
}

function WWHAPI_CloseHelp()
{
  if ((this.mWindow != null) &&
      (typeof(this.mWindow) != "undefined") &&
      ( ! this.mWindow.closed) &&
      (this.mWindow.WWHHelp != null) &&
      (typeof(this.mWindow.WWHHelp) != "undefined"))
  {
    this.mWindow.close();
  }
}

function WWHAPI_DisplayHelp(ParamContext,
                            ParamTopic)
{
  this.fLaunchHelp(true, ParamContext, ParamTopic, null);
}

function WWHAPI_DisplayHelpWithNavigation(ParamContext,
                                          ParamTopic)
{
  this.fLaunchHelp(false, ParamContext, ParamTopic, null);
}

function WWHAPI_DisplayHelpWithContents(ParamContext,
                                        ParamTopic)
{
  this.fLaunchHelp(false, ParamContext, ParamTopic, "contents");
}

function WWHAPI_DisplayHelpWithIndex(ParamContext,
                                     ParamTopic)
{
  this.fLaunchHelp(false, ParamContext, ParamTopic, "index");
}

function WWHAPI_DisplayHelpWithSearch(ParamContext,
                                      ParamTopic)
{
  this.fLaunchHelp(false, ParamContext, ParamTopic, "search");
}

function WWHAPI_DisplayHelpWithFavorites(ParamContext,
                                         ParamTopic)
{
  this.fLaunchHelp(false, ParamContext, ParamTopic, "favorites");
}

function WWHAPI_DisplayHelpNavigation()
{
  this.fLaunchHelp(false, null, null, null);
}

function WWHAPI_DisplayHelpContents()
{
  this.fLaunchHelp(false, null, null, "contents");
}

function WWHAPI_DisplayHelpIndex()
{
  this.fLaunchHelp(false, null, null, "index");
}

function WWHAPI_DisplayHelpSearch()
{
  this.fLaunchHelp(false, null, null, "search");
}

function WWHAPI_DisplayHelpFavorites()
{
  this.fLaunchHelp(false, null, null, "favorites");
}
