package tn.esprit.twin1.brogrammers.eventify.Eventify.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Organization
 *
 */
@Entity

public class Organization implements Serializable {
	
	
	   
	private int id;
	private String organizationName;
	private String organizationType;
	private Date creationDate;
	private static final long serialVersionUID = 1L;
	
	private List<Event> events;
	
	private User user;
	
	private List<Organizer> organizers;
	
	
	@OneToMany(mappedBy="organization")
	public List<Organizer> getOrganizers() {
		return organizers;
	}

	public void setOrganizers(List<Organizer> organizers) {
		this.organizers = organizers;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization() {
		super();
	}   
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}   
	public String getOrganizationType() {
		return this.organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}   
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@OneToMany(mappedBy="organization", cascade={CascadeType.ALL})
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void assignOrganizerToThisOrganization(List<Organizer> organizers )
	{
		this.setOrganizers(organizers);
		for(Organizer o : organizers )
		{
			o.setOrganization(this);
		}
	}
 

	
}
