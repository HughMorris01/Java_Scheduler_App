package model;

import database.DBContacts;
import database.DBCustomers;
import database.DBUsers;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDateTime start;
    private String startDateString;
    private String startTimeString;
    private LocalDateTime end;
    private String endTimeString;
    private int customerId;
    private int userId;
    private Contact contact;
    private Customer customer;
    private User user;

    public Appointment(int appointmentId, LocalDateTime start, LocalDateTime end, String title, String description,
                       String location, String type, int customerId, int userId, int contactId)
    {
        setAppointmentId(appointmentId);
        setStart(start);
        setStartDateString();
        setStartTimeString();
        setEnd(end);
        setEndTimeString();
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
    }

    // Setter Functions
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setType(String type) { this.type = type; }
    public void setStart(LocalDateTime start) { this.start = start; }
    public void setStartDateString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M-d-yyyy");
        startDateString = dtf.format(this.start); }
    public void setStartTimeString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        startTimeString = dtf.format(this.start); }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public void setEndTimeString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        endTimeString = dtf.format(this.end); }
    public void setContact(Contact contact) { this.contact = contact; }
    public void setUser(User user) { this.user = user; }
    public void setCustomer(Customer customer) { this.customer = customer;}
    public void setUserId(int userId) {
        this.userId = userId;
        for(User u : DBUsers.getAllUsers()) {
            if(u.getUserId() == userId) {
                setUser(u);
                break;
            }
        }
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
        for(Contact c : DBContacts.getAllContacts()) {
            if(c.getId() == contactId) {
                setContact(c);
                break;
            }
        }
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        for(Customer c : DBCustomers.getAllCustomers()) {
            if(c.getCustomerId() == customerId) {
                setCustomer(c);
                break;
            }
        }
    }

    // Getter Functions
    public int getAppointmentId() { return appointmentId; }
    public LocalDateTime getStart() { return start; }
    public String getStartDateString() {return startDateString; }
    public String getStartTimeString() { return startTimeString; }
    public LocalDateTime getEnd() { return end; }
    public String getEndTimeString() { return endTimeString; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return this.customer.getDivision().getDivisionName(); }
    public String getType() { return type; }
    public Customer getCustomer() { return customer; }
    public int getCustomerId() { return customerId; }
    public Contact getContact() { return contact; }
    public String getContactName() { return contact.getContactName(); }
    public int getContactId() { return contactId; }
    public User getUser() { return user; }
    public int getUserId() { return userId; }
}
