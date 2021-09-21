package com.finastra.domain;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    String sender;
    String receiver;
    String priority;
    List<String> refNo;

    public Transaction(String sender, String receiver, String priority, List<String> refNo) {
        this.sender = sender;
        this.receiver = receiver;
        this.priority = priority;
        this.refNo = refNo;
    }
    public Transaction(){
        this.sender = "";
        this.receiver = "";
        this.priority = "";
        this.refNo = new ArrayList<>();
    }
    @Override
    public String toString(){
        var builder = new StringBuilder();
        builder.append("Sender:   ").append(this.sender);
        builder.append("\n");
        builder.append("Receiver: ").append(this.receiver);
        builder.append("\n");
        builder.append("Priority: ").append(this.priority);
        builder.append("\n");
        builder.append("Transaction Reference: ");
        builder.append("\n");
        for (var ref : refNo ) {
            builder.append(ref).append(" ");
        }
        builder.append("\n");
        return builder.toString();
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        if(priority.equalsIgnoreCase("N")){
            this.priority = "Normal";
        }else{
            this.priority = "Urgent";
        }

    }

    public String getRefNo() {
        var builder = new StringBuilder();
        for (var ref : refNo ) {
            builder.append(ref).append(" ");
        }
        return builder.toString();
    }

    public void setRefNo(String num) {
        this.refNo.add(num);
    }
}
