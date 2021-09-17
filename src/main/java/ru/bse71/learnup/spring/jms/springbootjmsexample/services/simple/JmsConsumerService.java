package ru.bse71.learnup.spring.jms.springbootjmsexample.services.simple;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import ru.bse71.learnup.spring.jms.springbootjmsexample.model.MessageBody;

import javax.annotation.PostConstruct;
import javax.jms.*;

/**
 * Created by bse71
 * Date: 16.09.2021
 * Time: 23:16
 */

public class JmsConsumerService {

    private final String brokerUrl;

    public JmsConsumerService(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    @PostConstruct
    public void consumerProcess() {
        new Thread(() -> {
            while(true) {
                ConnectionFactory cf =
                        new ActiveMQConnectionFactory(brokerUrl);
                try (Connection conn = cf.createConnection()) {
                    conn.start();
                    try (Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                        Destination destination =
                                new ActiveMQQueue("myTextMsg");
                        MessageConsumer consumer = session.createConsumer(destination);
                        Message message = consumer.receive();

                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("GOT A MESSAGE: " + textMessage.getText());
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
