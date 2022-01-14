package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava2.HasId;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class MenuItem extends NamedEntity implements HasId, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "restaurant_id", nullable = false)
//    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
//    @NotBlank
    private LocalDate date;

    @Column(name = "price", nullable = false)
    private int price;

    public MenuItem(Integer id, String name, Restaurant restaurant, LocalDate date, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.date = date;
        this.price = price;
    }
}
