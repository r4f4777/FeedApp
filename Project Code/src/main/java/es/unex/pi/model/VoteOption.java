package es.unex.pi.model;

public class VoteOption {
    private Integer id;  // Asegúrate de incluir el ID si es necesario
    private String caption;
    private int presentationOrder;
    private Integer poll_id;

    public VoteOption() {}

    // Getter y setter para el ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getPollId() {
        return poll_id;
    }

    public void setPollId(Integer id) {
        this.poll_id = id;
    }
   

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }
}
