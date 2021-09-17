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

import static ru.bse71.learnup.spring.jms.springbootjmsexample.config.JmsConfig.TIMEOUT_PRODUCING;

/**
 * Created by bse71
 * Date: 16.09.2021
 * Time: 23:16
 */

public class JmsProducerService {

    private final String brokerUrl;

    public JmsProducerService(@Value("${spring.activemq.broker-url}") String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    @PostConstruct
    public void publishProcess() {

        new Thread(() -> {
            while(true) {
                ConnectionFactory cf =
                        new ActiveMQConnectionFactory(brokerUrl);
                try (Connection conn = cf.createConnection();
                     Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

                    Destination destination = new ActiveMQQueue("myTextMsg");
                    MessageProducer producer = session.createProducer(destination);
                    TextMessage message = session.createTextMessage();
                    message.setText("Hello world!");
                    producer.send(message);
                } catch (JMSException err) {
                    System.out.println("Something wrong! " + err.getMessage());
                }
                try {
                    Thread.sleep(TIMEOUT_PRODUCING);
                } catch (InterruptedException e) { }
            }
        }).start();
    }
}
