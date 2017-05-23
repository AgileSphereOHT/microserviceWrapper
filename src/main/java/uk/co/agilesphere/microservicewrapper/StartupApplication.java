package uk.co.agilesphere.microservicewrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import uk.co.agilesphere.microservicewrapper.servlet.WrapperServlet;

import javax.servlet.Servlet;
import java.io.File;

@SpringBootApplication
public class StartupApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(WrapperServlet.class);

    @SuppressWarnings("serial")
    @Bean
    public Servlet dispatcherServlet() {
        return new WrapperServlet();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartupApplication.class);
    }

    public static void main(String[] args) throws Exception {
        if (checkInputParameters(args)) {
            SpringApplication.run(StartupApplication.class, args);
        }
    }

    private static boolean checkInputParameters(String args[]) {
        boolean result = false;

        if (args.length != 2) {
            System.out.println("\nUsage: You must provide --config=<configuration file> --serviceClasspath=<service jar> parameters");
        } else {
            boolean check1 = checkFileArgumentOK(args[0], "--config");
            boolean check2 = checkFileArgumentOK(args[1], "--serviceClasspath");
            if (check1 && check2) {
                result = true;
            } else {
                System.out.println("\nUsage: You must provide --config=<valid configuration file> --serviceClasspath=<valid service jar> parameters");
            }
        }
        return result;
    }

    private static boolean checkFileArgumentOK(String fileArg, String expectedKey) {
        logger.info("Checking input argument = " + fileArg);

        String fileKey = "invalidKey";
        boolean jarFileExists = false;

        int delim = fileArg.indexOf('=');
        if (delim > 0) {
            fileKey = fileArg.substring(0, delim);
            String fileVal = fileArg.substring(delim + 1, fileArg.length());
            File jarFile = new File(fileVal);
            jarFileExists = jarFile.exists();
        }

        return (delim > 0 && fileKey.equals(expectedKey) && jarFileExists);
    }
}
