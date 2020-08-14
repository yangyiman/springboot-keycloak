package com.neo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String receiverEmail;
    private String text;
    private String subject;
    private String senderName;
}
