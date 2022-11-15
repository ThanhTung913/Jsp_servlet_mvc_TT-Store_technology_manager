package model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class Product implements java.io.Serializable {

    private int id;
    private String name;
    private int price;
    private int catagory;
    private String description;
    private String image;
    private int status;

    public Product() {
    }

    public Product(int id, String name, int price, int catagory, String description, String image, int status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.catagory = catagory;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public Product(String name, int price, int catagory, String description, int status) {
        this.name = name;
        this.price = price;
        this.catagory = catagory;
        this.description = description;
        this.status = status;
    }

    public Product(String name, int price, int catagory, String description, String image, int status) {
        this.name = name;
        this.price = price;
        this.catagory = catagory;
        this.description = description;
        this.image = image;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCatagory() {
        return catagory;
    }

    public void setCatagory(int catagory) {
        this.catagory = catagory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
