# Overseas Healthcare Service - Microservice Wrapper

## Overview

The following project is part of a solution for an Overseas Healthcare service commissioned by the UK Department of Health.

The goal is to provide a configurable wrapper around a service of library functions. 

The wrapper is a Spring Boot enabled web application, delivered as an executable jar and running in an embedded Tomcat container.

It is configured by a property file which contains mappings from the url being called to the library functions to be executed.

## Building the wrapper application

The Microservice wrapper provides a command line interface which initiates a Spring Boot loaded, servlet based application.

Built with maven     

    mvn clean package
    
To produce e.g.             

    microservice-wrapper-0.0.1-SNAPSHOT.jar

## Command line format

The command line format is    

    java jar <wrapper application>.jar --config=<config property file> --serviceClasspath=<service jar>.jar

e.g. run with a configuration file [ --config ] and the wrapped library [ --serviceClasspath ] at the root of the project

    java -jar target/microservice-wrapper-0.0.1-SNAPSHOT.jar --config=delegator.properties --serviceClasspath=dummy-service-0.0.1-SNAPSHOT.jar

## Implementation

### Config mapping
Internally a single "WrapperServlet" sits behind a single wildcard url endpoint.

The servlet path (the full uri less the servlet context) is extracted as a key to an entry in the configuration file which provides a mapping to a class and method call within the wrapped service

The property file maps selected service methods via their class and signatures to a specified key and the format looks like...
    
    url-key=<service class>,<service method>,<return type>,<zero to many parameter names>

At the moment all parameters are Strings, and all return values are of type String or Collections of type String.

For example with a wrapped service library with methods like 

    public void pingVoid()  
    public String pingAsString()  
    public String pingOneParamAsString(String param1)  
    public String pingTwoParamAsString(String param1, String param2)  
    public String pingThreeParamAsString(String param1, String param2, String param3) 
    public String[] pingThreeParamAsArray(String param1, String param2, String param3)  
    public List<String> pingThreeParamAsList(String param1, String param2, String param3)  
    public Map<String,String> pingThreeParamAsMap(String param1, String param2, String param3)

A configuration file exposing the above methods would look like

    service-x=uk.co.agilesphere.dummyservice.service.Service,pingVoid,String
    service-a=uk.co.agilesphere.dummyservice.service.Service,pingAsString,String
    service-b=uk.co.agilesphere.dummyservice.service.Service,pingOneParamAsString,String,param1
    service-c=uk.co.agilesphere.dummyservice.service.Service,pingTwoParamAsString,String,param1,param2
    service-d=uk.co.agilesphere.dummyservice.service.Service,pingThreeParamAsString,String,param1,param2,param3
    service-e=uk.co.agilesphere.dummyservice.service.Service,pingThreeParamAsArray,Array,param1,param2,param3
    service-f=uk.co.agilesphere.dummyservice.service.Service,pingThreeParamAsList,List,param1,param2,param3
    service-g=uk.co.agilesphere.dummyservice.service.Service,pingThreeParamAsMap,Map,param1,param2,param3

### Servlet initiation and service registration

The Wrapper servlet on initiation uses the property file to drive the creation of a "DelegatorRegistry" which provides an entry for every (valid) property key / value pair.

The service jar is added to the class path dynamically via a Spring application context class loader.

Then using java reflection the existence of service methods matching the property file definitions is checked, and if a match is made a 'primed' method (a.k.a. a Delegator) is stored in the Registry.

## Service invocation
As a request is made to the servlet, the servlet path element of the url requested is extracted, along with the named parameters being passed in.

The servlet path is used as a key to the Registry and the 'primed' method invoked applying the String parameters passed in.

The return value is then added to the Servlet response and passed back to the requestor.

eg. in line with the above service and configuration, with the following url call the <service-c> keyed function would be called, passing in two parameters and returning a String.

    http://localhost:8080/service-c?param1=value1&param2=value2

## Current limitations and future extension
Though the current development works and establishes the technical framework the choice had been made to provide an initial basic implementation.

The following outline some areas of known limitation and how we may develop the wrapper further.

- Wrapped service methods can only take String parameters and return Strings or collections (inc arrays) of string type. There may be a need to allow for multipart responses, or handle json request and response payloads.
- Method calls are passed over positionally, even though we have the names available. We could allow named parameter submission, which could then be used to do additional validation and provide more specific error messages.
- We may need to address handling of re-entrant methods and thread synchronisation. We therefore may need cacheing of multiple instances of Delegators? 
- The handling of different String based return types currently produces a basic String format. This needs more development to allow adaptation to different return requirements.

## License

This document is released under [CC0](LICENSE.md).

