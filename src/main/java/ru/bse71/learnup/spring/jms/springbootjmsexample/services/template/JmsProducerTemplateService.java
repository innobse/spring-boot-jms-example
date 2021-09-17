package ru.bse71.learnup.spring.jms.springbootjmsexample.services.template;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.bse71.learnup.spring.jms.springbootjmsexample.model.MessageBody;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import static ru.bse71.learnup.spring.jms.springbootjmsexample.config.JmsConfig.TIMEOUT_PRODUCING;

/**
 * Created by bse71
 * Date: 16.09.2021
 * Time: 23:16
 */

public class JmsProducerTemplateService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsProducerTemplateService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    public void publishProcess() {

        new Thread(() -> {
            while(true) {
                jmsTemplate.send("myMsg", session -> {
                    final ObjectMessage activeMQObjectMessage = new ActiveMQObjectMessage();
                    activeMQObjectMessage.setObject(new MessageBody("myText", 1));
                    return activeMQObjectMessage;
                });
                try {
                    Thread.sleep(TIMEOUT_PRODUCING);
                } catch (InterruptedException e) { }
            }
        }).start();
    }
}
