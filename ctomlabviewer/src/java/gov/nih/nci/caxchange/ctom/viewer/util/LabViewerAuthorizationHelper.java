/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.cabig.ctms.suite.authorization.CsmHelper;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembershipLoader;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.provisioning.AuthorizationManagerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * LabViewerAuthorizationHelper will check if a user is authorized for lab
 * viewer
 * <P>
 * 
 * @author Lisa Kelley
 */
public class LabViewerAuthorizationHelper
{
	private static final Logger log = Logger.getLogger(LabViewerAuthorizationHelper.class);
	private AuthorizationManager authorizationManager;
	private SuiteRoleMembershipLoader authorizationHelper;
	
	private synchronized AuthorizationManager getAuthorizationManager()
	{
		if (authorizationManager == null)
		{
		    authorizationManager = (AuthorizationManagerImpl)ObjectFactory.getObject("csmUserProvisioningManager");
		}
		
		return authorizationManager;
	}
	
	private synchronized SuiteRoleMembershipLoader getAuthorizationHelper()
	{
        if (authorizationHelper == null)
        {
            authorizationHelper = new SuiteRoleMembershipLoader();
            authorizationHelper.setAuthorizationManager(getAuthorizationManager());
        }
        
        return authorizationHelper;
    }
	
	private long getUserId(String username) throws SuiteAuthorizationAccessException
	{
		long userId = 0;

		User user = getAuthorizationManager().getUser(username);
		if (user == null)
		{
			throw new SuiteAuthorizationAccessException("Username %s is not a CSM user", username);
		}
		else
		{
			userId = user.getUserId().longValue();
			log.debug("UserId = " + userId);
		}

		return userId;
	}
	
	public Map<SuiteRole, SuiteRoleMembership> getUserRoleMemberships(String username) throws SuiteAuthorizationAccessException
	{
		long userId = getUserId(username);
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getAuthorizationHelper().getRoleMemberships(userId);
	    if (userRoleMemberships.isEmpty())
        {
	        throw new SuiteAuthorizationAccessException("Username %s has no associated CSM roles", username);
        }
	    
	    return userRoleMemberships;
	}

	public Set<SuiteRole> getUserRoles(String username) throws SuiteAuthorizationAccessException
	{	
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getUserRoleMemberships(username);
		
		return userRoleMemberships.keySet();
	}
	
	public SuiteRoleMembership getUserRoleMembership(String username, SuiteRole role) throws SuiteAuthorizationAccessException
	{
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getUserRoleMemberships(username);
	    
	    SuiteRoleMembership userRoleMembership = userRoleMemberships.get(role);
	    if (userRoleMembership == null)
        {
	        throw new SuiteAuthorizationAccessException("Username %s has no CSM protection groups associated with role %s", username, role.getDisplayName());
        }

		return userRoleMembership;
	}
	
	// eventually remove ///////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isAuthorized(String username)
	{
		boolean authorized = false;

		try
		{
			AuthorizationManager authorizationManager = SecurityServiceProvider.getAuthorizationManager(CsmHelper.SUITE_APPLICATION_NAME);
			if (authorizationManager != null)
			{
				User user = authorizationManager.getUser(username);
				if (user == null)
				{
					log.error("Username: " + username + " is not a CSM user");
				}
				else
				{
					authorized = true;
				}
			}
		}
		catch (CSConfigurationException e)
		{
			log.error("Error during authorization check", e);
		}
		catch (CSException e)
		{
			log.error("Error during authorization check", e);
		}

		return authorized;
	}
	
}
