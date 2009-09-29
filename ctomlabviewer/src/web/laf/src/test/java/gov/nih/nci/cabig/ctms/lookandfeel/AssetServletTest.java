package gov.nih.nci.cabig.ctms.lookandfeel;

import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Rhett Sutphin
 */
public class AssetServletTest extends TestCase {
    private AssetServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        servlet = new AssetServlet();

        request.setMethod("GET");
    }
    
    public void testRelativePathInfoForbidden() throws Exception {
        request.setPathInfo("../foo/Bar.class");
        servlet.service(request, response);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        assertEquals("Illegal path", response.getErrorMessage());
    }

    public void testPostNotAllowed() throws Exception {
        request.setMethod("POST");
        servlet.service(request, response);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }

    public void testResourceReturned() throws Exception {
        request.setPathInfo("/test-only.css");
        assertTestCssReturned();
    }

    public void testMimeTypes() throws Exception {
        assertEquals("image/png", servlet.contentType("foo.png"));
        assertEquals("image/gif", servlet.contentType("zip/bar.gif"));
        assertEquals("text/css", servlet.contentType("style/zero.css"));
    }

    private void assertTestCssReturned() throws IOException, ServletException {
        servlet.service(request, response);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("text/css", response.getContentType());
        assertEquals("Content doesn't match test-only.css",
            "p { font-size: 120% }\n", response.getContentAsString());
    }
}
