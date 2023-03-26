package org.mycompany.audit.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfig {

    public static final String AUDIT_TOPIC = "audit_topic";

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(AUDIT_TOPIC)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
