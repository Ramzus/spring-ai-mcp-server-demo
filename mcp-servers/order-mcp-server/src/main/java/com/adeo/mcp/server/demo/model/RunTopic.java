package com.adeo.mcp.server.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "runTopics")
public class RunTopic {

    @Id
    private String id;
    private String ticketId;
    private LocalDateTime date;
    private Integer resolutionTime;
    private String businessUnit;
    private List<String> messages;
    private List<String> modulesConcerned;
    private String resolutionProcedure;
    private String personInCharge;

    public RunTopic() {
    }

    public RunTopic(String ticketId, LocalDateTime date, Integer resolutionTime, String businessUnit,
                   List<String> messages, List<String> modulesConcerned, String resolutionProcedure,
                   String personInCharge) {
        this.ticketId = ticketId;
        this.date = date;
        this.resolutionTime = resolutionTime;
        this.businessUnit = businessUnit;
        this.messages = messages;
        this.modulesConcerned = modulesConcerned;
        this.resolutionProcedure = resolutionProcedure;
        this.personInCharge = personInCharge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(Integer resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getModulesConcerned() {
        return modulesConcerned;
    }

    public void setModulesConcerned(List<String> modulesConcerned) {
        this.modulesConcerned = modulesConcerned;
    }

    public String getResolutionProcedure() {
        return resolutionProcedure;
    }

    public void setResolutionProcedure(String resolutionProcedure) {
        this.resolutionProcedure = resolutionProcedure;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    @Override
    public String toString() {
        return "RunTopic{" +
                "id='" + id + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", date=" + date +
                ", resolutionTime=" + resolutionTime +
                ", businessUnit='" + businessUnit + '\'' +
                ", messages=" + messages +
                ", modulesConcerned=" + modulesConcerned +
                ", resolutionProcedure='" + resolutionProcedure + '\'' +
                ", personInCharge='" + personInCharge + '\'' +
                '}';
    }
}
