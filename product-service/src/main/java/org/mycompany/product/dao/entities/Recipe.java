package org.mycompany.product.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "recipe")
public class Recipe {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID uuid;
    @Column(name = "creation_time", nullable = false)
    private Instant creationTime = Instant.now();
    @Column(name = "last_updated", nullable = false)
    @Version
    private Instant lastUpdated = Instant.now();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            schema = "app",
            name = "recipe_product",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "product_instance_id"))
    @Size(min = 1, message = "A recipe must contain at least one ingredient!")
    @NotNull(message = "No recipe composition has been provided!")
    private List<ProductInstance> composition;
    @NotBlank(message = "The title cannot be blank!")
    @NotNull(message = "No title has been provided!")
    private String title;

    public Recipe() {
    }

    public Recipe(UUID uuid, Instant creationTime,
                  Instant lastUpdated, String title,
                  List<ProductInstance> composition) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.lastUpdated = lastUpdated;
        this.title = title;
        this.composition = composition;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<ProductInstance> getComposition() {
        return this.composition;
    }

    public void setComposition(List<ProductInstance> composition) {
        this.composition = composition;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}