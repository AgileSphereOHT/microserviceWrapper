package uk.co.agilesphere.microservicewrapper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.agilesphere.microservicewrapper.delegator.DelegatorRegistry;
import uk.co.agilesphere.microservicewrapper.delegator.DelegatorRegistryEntry;

//import uk.co.agilesphere.wrapped.service.Service;

@RestController
public class ServiceController {

    //private Service service;
    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

/*    @Autowired
    public ServiceController(Service service) {
        this.service = service;
    }*/

    @RequestMapping("/service")
    public String service(Model model) {
        logger.info(">> providing service");
        String rtn = "";
        try {
            DelegatorRegistry registry = new DelegatorRegistry("delegators/delegators.properties");
            registry.registerDelegators();
            DelegatorRegistryEntry entry = registry.getEntry("urlkey");
            rtn = (String) entry.getDelegator().invokeMethod();

/*            Class clazz = Class.forName("uk.co.agilesphere.wrapped.service.Service");
            System.out.println("Clazz=" + clazz.getSimpleName());
            Constructor<?> cons = clazz.getDeclaredConstructor(String.class);
            //Object obj = clazz.newInstance();
            Object obj = cons.newInstance("PINGBACK");
            //Class[] cArg = new Class[1];
            //cArg[0] = String.class;
            Method meth = clazz.getDeclaredMethod("ping", new Class[]{});
            Object ret = meth.invoke(obj, new Object[]{});
            rtn = (String) ret;*/
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        } /*catch (ClassNotFoundException cnfe) {
            System.out.println("CNFE");
        } catch (InstantiationException ie) {
        System.out.println("CNFE");
        } catch (NoSuchMethodException nsme) {
            System.out.println("NSME");
        } catch (InvocationTargetException ite) {
            System.out.println("ITE");
        } catch (IllegalAccessException iae) {
            System.out.println("IAE");
        }*/
        //return "XX..."+service.ping()+"...XX";
        return "XX..." + rtn + "...XYZ";
    }
}