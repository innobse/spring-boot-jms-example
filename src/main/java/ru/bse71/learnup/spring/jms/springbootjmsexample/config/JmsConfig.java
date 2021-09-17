package ru.bse71.learnup.spring.jms.springbootjmsexample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import ru.bse71.learnup.spring.jms.springbootjmsexample.services.JmsListenerService;
import ru.bse71.learnup.spring.jms.springbootjmsexample.services.simple.JmsConsumerService;
import ru.bse71.learnup.spring.jms.springbootjmsexample.services.simple.JmsProducerService;
import ru.bse71.learnup.spring.jms.springbootjmsexample.services.template.JmsConsumerTemplateService;
import ru.bse71.learnup.spring.jms.springbootjmsexample.services.template.JmsProducerTemplateService;

/**
 * Created by bse71
 * Date: 16.09.2021
 * Time: 22:50
 */

@Configuration
public class JmsConfig {

    public static final int TIMEOUT_PRODUCING = 2000;

    @Configuration
    @Profile("jms-listener")
    static class JmsListenerConfiguration {

        @Bean
        public JmsListenerService jmsListenerService() {
            return new JmsListenerService();
        }

        @Bean("jmsProducerForListener")
        public JmsProducerService jmsProducerService(@Value("${spring.activemq.broker-url}") String brokerUrl) {
            return new JmsProducerService(brokerUrl);
        }

        @Bean("jmsProducerTemplateForListener")
        public JmsProducerTemplateService jmsProducerService(JmsTemplate jmsTemplate) {
            return new JmsProducerTemplateService(jmsTemplate);
        }
    }

    @Configuration
    @Profile("jms-simple")
    static class JmsSimpleConfiguration {

        @Bean
        public JmsConsumerService jmsConsumerService(@Value("${spring.activemq.broker-url}") String brokerUrl) {
            return new JmsConsumerService(brokerUrl);
        }

        @Bean
        public JmsProducerService jmsProducerService(@Value("${spring.activemq.broker-url}") String brokerUrl) {
            return new JmsProducerService(brokerUrl);
        }
    }

    @Configuration
    @Profile("jms-template")
    static class JmsTemplateConfiguration {

        @Bean
        public JmsConsumerTemplateService jmsConsumerService(JmsTemplate jmsTemplate) {
            return new JmsConsumerTemplateService(jmsTemplate);
        }

        @Bean
        public JmsProducerTemplateService jmsProducerService(JmsTemplate jmsTemplate) {
            return new JmsProducerTemplateService(jmsTemplate);
        }
    }
}
