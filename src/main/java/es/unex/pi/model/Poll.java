package es.unex.pi.model;

import java.time.Instant;


public class Poll {
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private Integer id;
    private Integer user_id;
    

    public Poll() {}

    
    public Integer getUserId() {
    	return user_id;
    }
    
    public void setUserId(Integer user_id) {
    	this.user_id = user_id;
    }

    public Integer getId() {
    	return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
     public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
     
    public Instant getPublishedAt() {
    	return publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }
}
