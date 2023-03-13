package org.mycompany.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mycompany.product.config.mixins.PageMixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class CustomSerializationConfig {

    @Autowired
    public void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Page.class, PageMixin.class);
    }
}
