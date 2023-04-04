package fr.univcotedazur.simpletcfs.cli.model;

public class CliItem {

    private Long id;
    private int quantity;
    private CliProduct product;

    public CliItem(int quantity, CliProduct product) {
        this.quantity = quantity;
        this.product = product;
    }

    public CliItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CliProduct getProduct() {
        return product;
    }

    public void setProduct(CliProduct product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CliItem{" +
                "quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}
