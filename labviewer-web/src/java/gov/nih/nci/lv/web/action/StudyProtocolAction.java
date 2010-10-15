package gov.nih.nci.lv.web.action;

public class StudyProtocolAction extends LabViewerAction {
    private static final long serialVersionUID = 1234573645L;
    
    public String execute() throws Exception {
        System.out.println("execute");
        return "";
    }

    public String query() throws Exception {
        System.out.println("queryNAVED");
        return SUCCESS;
    }

}
