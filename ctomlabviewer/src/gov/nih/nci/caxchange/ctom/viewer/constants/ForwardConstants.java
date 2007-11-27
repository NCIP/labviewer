/*
 * Created on Dec 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.caxchange.ctom.viewer.constants;

/**
 *
 *<!-- LICENSE_TEXT_START -->
 *
 *The NCICB Common Security Module's User Provisioning Tool (UPT) Software License,
 *Version 3.0 Copyright 2004-2005 Ekagra Software Technologies Limited ('Ekagra')
 *
 *Copyright Notice.  The software subject to this notice and license includes both
 *human readable source code form and machine readable, binary, object code form
 *(the 'UPT Software').  The UPT Software was developed in conjunction with the
 *National Cancer Institute ('NCI') by NCI employees and employees of Ekagra.  To
 *the extent government employees are authors, any rights in such works shall be
 *subject to Title 17 of the United States Code, section 105.    
 *
 *This UPT Software License (the 'License') is between NCI and You.  'You (or
 *'Your') shall mean a person or an entity, and all other entities that control,
 *are controlled by, or are under common control with the entity.  'Control' for
 *purposes of this definition means (i) the direct or indirect power to cause the
 *direction or management of such entity, whether by contract or otherwise, or
 *(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *(iii) beneficial ownership of such entity.  
 *
 *This License is granted provided that You agree to the conditions described
 *below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 *no-charge, irrevocable, transferable and royalty-free right and license in its
 *rights in the UPT Software to (i) use, install, access, operate, execute, copy,
 *modify, translate, market, publicly display, publicly perform, and prepare
 *derivative works of the UPT Software; (ii) distribute and have distributed to
 *and by third parties the UPT Software and any modifications and derivative works
 *thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 *third parties, including the right to license such rights to further third
 *parties.  For sake of clarity, and not by way of limitation, NCI shall have no
 *right of accounting or right of payment from You or Your sublicensees for the
 *rights granted under this License.  This License is granted at no charge to You.
 *
 *1.	Your redistributions of the source code for the Software must retain the
 *above copyright notice, this list of conditions and the disclaimer and
 *limitation of liability of Article 6 below.  Your redistributions in object code
 *form must reproduce the above copyright notice, this list of conditions and the
 *disclaimer of Article 6 in the documentation and/or other materials provided
 *with the distribution, if any.
 *2.	Your end-user documentation included with the redistribution, if any, must
 *include the following acknowledgment: 'This product includes software developed
 *by Ekagra and the National Cancer Institute.'  If You do not include such
 *end-user documentation, You shall include this acknowledgment in the Software
 *itself, wherever such third-party acknowledgments normally appear.
 *
 *3.	You may not use the names 'The National Cancer Institute', 'NCI' 'Ekagra
 *Software Technologies Limited' and 'Ekagra' to endorse or promote products
 *derived from this Software.  This License does not authorize You to use any
 *trademarks, service marks, trade names, logos or product names of either NCI or
 *Ekagra, except as required to comply with the terms of this License.
 *
 *4.	For sake of clarity, and not by way of limitation, You may incorporate this
 *Software into Your proprietary programs and into any third party proprietary
 *programs.  However, if You incorporate the Software into third party proprietary
 *programs, You agree that You are solely responsible for obtaining any permission
 *from such third parties required to incorporate the Software into such third
 *party proprietary programs and for informing Your sublicensees, including
 *without limitation Your end-users, of their obligation to secure any required
 *permissions from such third parties before incorporating the Software into such
 *third party proprietary software programs.  In the event that You fail to obtain
 *such permissions, You agree to indemnify NCI for any claims against NCI by such
 *third parties, except to the extent prohibited by law, resulting from Your
 *failure to obtain such permissions.
 *
 *5.	For sake of clarity, and not by way of limitation, You may add Your own
 *copyright statement to Your modifications and to the derivative works, and You
 *may provide additional or different license terms and conditions in Your
 *sublicenses of modifications of the Software, or any derivative works of the
 *Software as a whole, provided Your use, reproduction, and distribution of the
 *Work otherwise complies with the conditions stated in this License.
 *
 *6.	THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
 *EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
 *FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *<!-- LICENSE_TEXT_END -->
 *
 */


/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ForwardConstants 
{
	public static final String SUCCESS="success" ;
	public static final String FAILURE="failure" ;
	public static final String CANCEL="cancel" ;
	
	public static final String LOAD_HOME_SUCCESS = "LoadHomeSuccess";
	public static final String LOAD_ADD_SUCCESS="LoadAddSuccess";
	public static final String LOAD_SEARCH_SUCCESS="LoadSearchSuccess";
	public static final String LOAD_SEARCH_RESULT_SUCCESS="LoadSearchResultSuccess";	
	public static final String SUBMIT_ADVERSE_EVENT_SUCCESS="SubmitAdverseEventSuccess";
	public static final String LOAD_TO_CTMS_EVENT_SUCCESS="LoadToCTMSSuccess";	
	public static final String READ_SUCCESS="ReadSuccess";
	public static final String UPDATE_SUCCESS="UpdateSuccess";
	public static final String DELETE_SUCCESS="DeleteSuccess";
	public static final String SEARCH_SUCCESS="SearchSuccess";	
	public static final String LOGIN_SUCCESS="LoginSuccess";
	public static final String LOGIN_SUCCESS_HOTLINK="LoginSuccessHotLink";
	public static final String ADMIN_LOGIN_SUCCESS = "AdminLoginSuccess";
	public static final String LOGIN_FAILURE="LoginFailure";	
	public static final String LOAD_ASSOCIATION_SUCCESS="LoadAssociationSuccess";
	public static final String SET_ASSOCIATION_SUCCESS="SetAssociationSuccess";
	public static final String LOAD_PARENT_ASSOCIATION_SUCCESS = "LoadParentAssociationSuccess";
	public static final String SET_PARENT_ASSOCIATION_SUCCESS = "SetParentAssociationSuccess";
	public static final String LOAD_OWNERSHIP_ASSOCIATION_SUCCESS = "LoadOwnershipAssociationSuccess";
	public static final String SET_OWNERSHIP_ASSOCIATION_SUCCESS = "SetOwnershipAssociationSuccess";
	public static final String LOAD_DOUBLEASSOCIATION_SUCCESS = "LoadDoubleAssociationSuccess";
	public static final String SET_DOUBLEASSOCIATION_SUCCESS = "SetDoubleAssociationSuccess";
	public static final String LOAD_PROTECTIONGROUPASSOCIATION_SUCCESS = "LoadProtectionGroupAssociationSuccess";
	public static final String LOAD_PROTECTIONELEMENTPRIVILEGESASSOCIATION_SUCCESS = "LoadProtectionElementPrivilegesAssociationSuccess";
	public static final String LOAD_ROLEASSOCIATION_SUCCESS = "LoadRoleAssociationSuccess";
	public static final String REMOVE_PROTECTIONGROUPASSOCIATION_SUCCESS = "RemoveProtectionGroupAssociationSuccess";
	public static final String SET_ROLEASSOCIATION_SUCCESS = "SetRoleAssociationSuccess";

	
	public static final String READ_FAILURE = "ReadFailure";
	public static final String SEARCH_FAILURE = "SearchFailure";
	public static final String LOAD_PROTECTIONGROUPASSOCIATION_FAILURE = "LoadProtectionGroupAssociationFailure";
	public static final String LOAD_PROTECTIONELEMENTPRIVILEGESASSOCIATION_FAILURE = "LoadProtectionElementPrivilegesAssociationFailure";

	public static final String LOGOUT_SUCCESS="LogoutSuccess";
	public static final String LOGOUT_ACTION="LogoutAction";
	
	public static final String HOME_PAGE="HomePage";
	public static final String ADMIN_HOME_PAGE="AdminHomePage";	
	public static final String LOGIN_PAGE="LoginPage";
	
	
	public static final String TABLE_HOME_PAGE="TableHomePage";
	public static final String LABACTIVITIES_HOME_PAGE="LabActivitiesHomePage";
	
	public static final String DETAILS_PAGE="DetailsPage";

}
