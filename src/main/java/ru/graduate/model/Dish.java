package ru.graduate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Menu menu;

    //http://stackoverflow.com/a/43051227/548473
    //in kopecks 
    @Column
    @Range(min = 1, max = 10000000)
    private int price;

    public Dish() {
    }

    public Dish(Menu menu, int price) {
        this.menu = menu;
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
