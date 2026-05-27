package com.cool.spring.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(Item item) {
        items.add(item);
        item.setOrder(this);
    }

    public void replaceItems(List<Item> newItems) {

        items.clear();

        if (newItems != null) {
            newItems.forEach(this::addItem);
        }
    }
}
