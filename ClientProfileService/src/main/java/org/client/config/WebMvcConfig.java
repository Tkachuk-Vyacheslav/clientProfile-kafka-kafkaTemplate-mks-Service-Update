package org.client.config;

import lombok.extern.slf4j.Slf4j;
import org.client.controller.AddressController;
import org.client.controller.IndividualController;
import org.client.dto.IndividualDto;
import org.client.service.AddressService;
import org.client.service.IndividualService;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig {


    @Bean
    public IndividualController individualController(IndividualService individualService) {
        return new IndividualController(individualService);
    }


    @Bean
    public AddressController addressController(AddressService addressService) {
        return new AddressController(addressService);
    }

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            WebEndpointsSupplier webEndpointsSupplier,
            ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier,
            EndpointMediaTypes endpointMediaTypes,
            CorsEndpointProperties corsProperties,
            WebEndpointProperties webEndpointProperties,
            Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(
                webEndpointProperties, environment, basePath);
        log.debug("WebMvcEndpointHandlerMapping found");
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints,
                endpointMediaTypes, corsProperties.toCorsConfiguration(),
                new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping, null);

    }
    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties,
                                               Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() &&
                (StringUtils.hasText(basePath) ||
                        ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
