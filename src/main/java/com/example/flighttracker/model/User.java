package com.example.flighttracker.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Flight> myFlights = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany
    @JoinTable(name = "flight_passengers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private Set<Flight> otherFlights = new HashSet<>();

    public void addMyFlight(Flight flight){
        myFlights.add(flight);
        flight.setOwner(this);
    }

    public void removeMyFlight(Flight flight){
        myFlights.remove(flight);
        flight.setOwner(null);
    }

    public void addOtherFlight(Flight flight){
        otherFlights.add(flight);
        flight.getPassengers().add(this);
    }

    public void removeOtherFlight(Flight flight){
        otherFlights.remove(flight);
        flight.getPassengers().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(role);
        return grantedAuthoritySet;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
