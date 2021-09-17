package ru.bse71.learnup.spring.jms.springbootjmsexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by bse71
 * Date: 17.09.2021
 * Time: 0:18
 */

@Data
@AllArgsConstructor
public class MessageBody implements Serializable {

    private String text;
    private int priority;
}
