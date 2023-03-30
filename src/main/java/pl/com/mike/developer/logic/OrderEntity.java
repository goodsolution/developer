package pl.com.mike.developer.logic;

import javax.persistence.*;

@Entity
@Table(name="orders")
class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "customer_name")
    private String firstName;

    @Column(name = "customer_surname")
    private String lastName;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
