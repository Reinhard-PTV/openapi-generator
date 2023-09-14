/**
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1-SNAPSHOT).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package org.openapitools.api;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.LoggingLevel;

@Component
public class PetApiValidator extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(Exception.class)
            .log(LoggingLevel.ERROR, "${exception.message}: ${exception.stacktrace}")
            .handled(true)
            .process("validationErrorProcessor");
        
        from("direct:validate-addPet")
            .id("validate-addPet")
            .to("bean-validator://validate-request")
            .to("direct:addPet")
            .to("bean-validator://validate-response");
        
        from("direct:validate-deletePet")
            .id("validate-deletePet")
            .to("direct:deletePet");
        
        from("direct:validate-findPetsByStatus")
            .id("validate-findPetsByStatus")
            .to("direct:findPetsByStatus")
            .to("bean-validator://validate-response");
        
        from("direct:validate-findPetsByTags")
            .id("validate-findPetsByTags")
            .to("direct:findPetsByTags")
            .to("bean-validator://validate-response");
        
        from("direct:validate-getPetById")
            .id("validate-getPetById")
            .to("direct:getPetById")
            .to("bean-validator://validate-response");
        
        from("direct:validate-updatePet")
            .id("validate-updatePet")
            .to("bean-validator://validate-request")
            .to("direct:updatePet")
            .to("bean-validator://validate-response");
        
        from("direct:validate-updatePetWithForm")
            .id("validate-updatePetWithForm")
            .to("bean-validator://validate-request")
            .to("direct:updatePetWithForm");
        
        from("direct:validate-uploadFile")
            .id("validate-uploadFile")
            .to("bean-validator://validate-request")
            .to("direct:uploadFile")
            .to("bean-validator://validate-response");
        
    }
}
