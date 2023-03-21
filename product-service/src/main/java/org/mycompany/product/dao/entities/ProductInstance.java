package org.mycompany.product.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;
@Entity
@Table(schema = "app", name = "product_instance")
public class ProductInstance {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @NotNull(message = "No product has been provided!")
    private Product product;
    @Positive(message = "The weight value must be positive!")
    private int weight;

    public ProductInstance() {
    }

    public ProductInstance(UUID uuid, Product product, int weight) {
        this.uuid = uuid;
        this.product = product;
        this.weight = weight;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}