package nl.management.finance.contact;

import nl.management.finance.user.models.User;

import javax.persistence.*;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Column
    private String name;

    @Column
    private String iban;

    public Contact() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
