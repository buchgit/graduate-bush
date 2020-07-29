package ru.graduate.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column
    @NotNull
    private LocalDateTime date;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDateTime date) {
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }
}
