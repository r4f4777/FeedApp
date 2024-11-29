package es.unex.pi.model;

import java.time.Instant;

public class Vote {
    private Integer id;
    private Instant publishedAt;
    private User user;
    private Integer voteOption_id;
    private int pollId; 


    public Vote() {}

   

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public Integer getVoteOptionId() {
        return voteOption_id;
    }

    public void setVoteOptionId(Integer id) {
        this.voteOption_id = id;
    }
    

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
