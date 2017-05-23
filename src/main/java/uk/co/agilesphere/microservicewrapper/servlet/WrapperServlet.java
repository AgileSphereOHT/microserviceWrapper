package uk.co.agilesphere.microservicewrapper.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import uk.co.agilesphere.microservicewrapper.delegator.DelegatorRegistry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class WrapperServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(WrapperServlet.class);

    @Autowired
    private Environment env;

    private DelegatorRegistry registry;

    public WrapperServlet() {
        logger.info("Entering WrapperServlet constructor()");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("Entering WrapperServlet.init()");

        String configFilename = env.getProperty("config");
        String serviceClasspath = env.getProperty("serviceClasspath");
        logger.info("Initialisation files: config = " + configFilename + "  " + serviceClasspath);

        registry = new DelegatorRegistry(serviceClasspath, configFilename);
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        super.service(servletRequest, servletResponse);
        logger.info("Entering WrapperServlet.service()");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Entering WrapperServlet.doGet()");

        String returnValue = "";

        try {
            String key = getRegistryKey(request);
            logger.info(">> Providing service = " + key);

            Map<String, String[]> paramMap = request.getParameterMap();

            returnValue = registry.getResult(key, paramMap);

            response.getWriter().print("Service X..." + returnValue + "...");

        } catch (Exception e) {
            logger.error("Wrapper Servlet Exception " + e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, getStacktraceAsString(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Entering WrapperServlet.doPost()");
        doGet(req, resp);
    }

    private String getStacktraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private String getRegistryKey(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String lessContextPath = uri.substring(request.getContextPath().length());
        int ix = lessContextPath.indexOf("/") + 1;
        String key = lessContextPath.substring(ix);
        logger.info("Registry Key=" + key);
        return key;
    }
}
