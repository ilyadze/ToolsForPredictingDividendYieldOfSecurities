package com.dashko.common.service.mail;

public interface IMailService {

    public void send(String emailTo, String subject, String message);
}
