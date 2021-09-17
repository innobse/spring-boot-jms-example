package ru.bse71.learnup.spring.jms.springbootjmsexample.services.template;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.bse71.learnup.spring.jms.springbootjmsexample.model.MessageBody;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import static ru.bse71.learnup.spring.jms.springbootjmsexample.config.JmsConfig.TIMEOUT_PRODUCING;

/**
 * Created by bse71
 * Date: 16.09.2021
 * Time: 23:16
 */

public class JmsConsumerTemplateService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsConsumerTemplateService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    public void consumerProcess() {
        new Thread(() -> {
            while(true) {
                final Message msg = jmsTemplate.receive("myMsg");
                try {
                    if (msg instanceof ObjectMessage) {
                        MessageBody body = (MessageBody) ((ObjectMessage) msg).getObject();
                        if (body != null) System.out.println("GOT A MESSAGE: " + body);
                    } else {
                        System.out.println("Got message without body");
                    }

                } catch (JMSException e) { }
            }
        }).start();

    }
}
