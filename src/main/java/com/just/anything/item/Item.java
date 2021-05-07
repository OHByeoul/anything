package com.just.anything.item;

import com.just.anything.domain.Category;
import com.just.anything.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속전략
@DiscriminatorColumn(name="dtype")
@Getter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categorys = new ArrayList<>();
    /*
    * 재고 증가
    * */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int result = this.stockQuantity - quantity;
        if(result >= 0 ){
            this.stockQuantity = result;
        } else {
            throw new NotEnoughStockException("need more stock");
        }
    }
}
