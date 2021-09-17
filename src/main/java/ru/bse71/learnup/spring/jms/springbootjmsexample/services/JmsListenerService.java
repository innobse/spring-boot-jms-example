package ru.bse71.learnup.spring.jms.springbootjmsexample.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import ru.bse71.learnup.spring.jms.springbootjmsexample.model.MessageBody;

/**
 * Created by bse71
 * Date: 17.09.2021
 * Time: 1:42
 */

@Slf4j
public class JmsListenerService {

    @JmsListener(destination = "myTextMsg")
    public void getMessage(String text) {
        log.info("GOT MESSAGE: " + text);
    }

    @JmsListener(destination = "myMsg")
    public void getMessage(@Payload MessageBody messageBody) {
        log.info("GOT MESSAGE: " + messageBody);
    }

}
