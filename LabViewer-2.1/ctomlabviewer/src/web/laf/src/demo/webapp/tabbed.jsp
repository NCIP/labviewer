<%@ page import="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@ page import="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>

<%
    /* Mock flow, tab, and summary.  In a real application, these will come from the controller or command */
    Flow<String> flow = new Flow<String>("Import codes");
    flow.addTab(new Tab<String>("Select", "Select", "dc"));
    flow.addTab(new Tab<String>("Organize", "Organize", "dc"));
    flow.addTab(new Tab<String>("Import", "Import", "dc"));
    flow.addTab(new Tab<String>("Review", "Review", "dc"));

    Map<String, String> summary = new LinkedHashMap<String, String>();
    summary.put("Participant", "Curabitur Tellus");
    summary.put("Study", "Nunc vel odio. Vivamus mattis libero a dolor in at nibh donec mattis fermentum ligula");

    request.setAttribute("flow", flow);
    request.setAttribute("tab", flow.getTab(1));
    request.setAttribute("summary", summary);
%>

<%@taglib prefix="laf" uri="http://gforge.nci.nih.gov/projects/ctmscommons/taglibs/laf" %>
<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
</head>
<body>
<laf:box title="${pageHelp}${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}">
    <laf:flashMessage/>
    <form action="#">
        <laf:tabFields tab="${tab}"/>
        <laf:division>
            <div class="row">
                <div class="label"><label for="input0"><span class="required-indicator">*</span>Lorem</label></div>
                <div class="value"><input type="text" id="input0" /></div>
            </div>
            <div class="row error">
                <div class="label"><label for="input1">Ipsum dolor sit amet</label></div>
                <div class="value">
                    <input type="text" value="consectetuer" id="input1" />
                    <ul class="errors">
                        <li>There was some trouble with ipsum</li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="label"><label for="input2"><span class="required-indicator">*</span>Elit</label></div>
                <div class="value"><textarea id="input2" rows="4" cols="30"></textarea></div>
            </div>
            <div class="row">
                <div class="label">Vestibulum</div>
                <div class="value">
                    <input type="text" value="18.4">
                    ante
                    <select>
                        <option>felis</option>
                        <option>augue</option>
                        <option>dignissim</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="label">Morbi</div>
                <div class="value">
                    <label><input type="radio" name="radio"> At</label>
                    <label><input type="radio" name="radio"> Nibh</label>
                    <label><input type="radio" name="radio" checked> Sed</label>
                </div>
            </div>
        </laf:division>
        <laf:division title="Vivamus A">
            <div class="content">
                <div class="row">
                    <div class="label">Scelerisque sapien</div>
                    <div class="value">
                        <input type="text">
                        eu
                        <input type="text">
                    </div>
                </div>
                <div class="row">
                    <div class="label">Faucibus orci</div>
                    <div class="value">
                        <input type="text">
                        eu
                        <input type="text">
                    </div>
                </div>
            </div>
        </laf:division>
        <laf:division title="Vivamus B">
            <div class="content">
                <div class="row">
                    <div class="label">Scelerisque sapien</div>
                    <div class="value">
                        <input type="text" disabled="disabled">
                        eu
                        <input type="text" disabled="disabled">
                    </div>
                </div>
                <div class="row">
                    <div class="label">Faucibus orci</div>
                    <div class="value">
                        <input type="text" value="9 * 6" disabled="disabled">
                        eu
                        <input type="text" value="42" disabled="disabled">
                    </div>
                </div>
            </div>
        </laf:division>
        <laf:tabControls tab="${tab}" flow="${flow}" willSave="${true}"/>
    </form>
</laf:box>
</body>
</html>

