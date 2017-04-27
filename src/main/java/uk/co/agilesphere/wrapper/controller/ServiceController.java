package uk.co.agilesphere.wrapper.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.agilesphere.wrapped.service.Service;

@RestController
public class ServiceController {

    private Service service;
    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    public ServiceController(Service service) {
        this.service = service;
    }

    @RequestMapping("/service")
    public String service(Model model) {
        logger.info(">> providing service");

        return "XX..."+service.ping()+"...XX";
    }

}