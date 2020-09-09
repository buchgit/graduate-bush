package ru.graduate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
        name = "graph-restaurant-menus",
        attributeNodes = {@NamedAttributeNode(value = "menus", subgraph = "graph-restaurant-menus-dishes")},
        subgraphs = {@NamedSubgraph(
                name = "graph-restaurant-menus-dishes",
                attributeNodes = {@NamedAttributeNode(value = "dishes")})}
)

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Vote> votes;

    public Restaurant() {
    }
}
