package com.ded.BTS.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "ticket_comments")
//@org.hibernate.annotations.SQLRestriction("rec_end_date = TIMESTAMP '9999-01-01 00:00:00'")
public class TicketComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "ticket_comments_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 5000)
    private String content;

    /* Getters and Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
	public TicketComment(Long id, Ticket ticket, User author, String content) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.author = author;
		this.content = content;
	}
	public TicketComment() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
