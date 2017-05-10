package uk.co.agilesphere.microservicewrapper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.agilesphere.microservicewrapper.delegator.DelegatorRegistry;
import uk.co.agilesphere.microservicewrapper.delegator.DelegatorRegistryEntry;

import java.util.*;

//import uk.co.agilesphere.wrapped.service.Service;

@RestController
public class ServiceController {

    //private Service service;
    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

/*    @Autowired
    public ServiceController(Service service) {
        this.service = service;
    }*/

    @RequestMapping("/service-x")
    public String serviceX(Model model) {
        logger.info(">> providing service X");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-x");
            rtn = (String) entry.getDelegator().invokeMethod();
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service X..." + rtn + "...";
    }

    @RequestMapping("/service-a")
    public String serviceA(Model model) {
        logger.info(">> providing service A");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-a");
            rtn = (String) entry.getDelegator().invokeMethod();
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service A..." + rtn + "...";
    }

    @RequestMapping("/service-b")
    public String serviceB(Model model) {
        logger.info(">> providing service B");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-b");
            String[] params = {"param1"};
            rtn = (String) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service B..." + rtn + "...";
    }

    @RequestMapping("/service-c")
    public String serviceC(Model model) {
        logger.info(">> providing service C");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-c");
            String[] params = {"param1","param2"};
            rtn = (String) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service C..." + rtn + "...";
    }

    @RequestMapping("/service-d")
    public String serviceD(Model model) {
        logger.info(">> providing service D");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-d");
            String[] params = {"param1","param2","param3"};
            rtn = (String) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service D..." + rtn + "...";
    }

    @RequestMapping("/service-e")
    public String serviceE(Model model) {
        logger.info(">> providing service E");
        String[] rtn = {};
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-e");
            String[] params = {"param1","param2","param3"};
            rtn = (String[]) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service E..." + Arrays.toString(rtn) + "...";
    }

    @RequestMapping("/service-f")
    public String serviceF(Model model) {
        logger.info(">> providing service F");
        List rtn = new ArrayList<String>();
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-f");
            String[] params = {"param1","param2","param3"};
            rtn = (List<String>) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service F..." + rtn + "...";
    }

    @RequestMapping("/service-g")
    public String serviceG(Model model) {
        logger.info(">> providing service G");
        Map<String,String> rtn = new HashMap<String,String>();
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("service-g");
            String[] params = {"param1","param2","param3"};
            rtn = (Map<String,String>) entry.getDelegator().invokeMethod(params);
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }
        return "Service G..." + rtn + "...";
    }
}