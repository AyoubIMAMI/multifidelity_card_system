package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@ShellComponent
public class CatalogCommands {
    public static final String BASE_URI = "/catalog";
    public static final String DISCOUNTS_URI = "/discounts";
    public static final String STORE_URI = "/store";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("List all the discounts")
    public String discounts() {
        StringBuilder discounts = new StringBuilder("List of discounts:\n");
        for (Map.Entry<Long, Discount> entry : cliContext.getDiscounts().entrySet()) {
            discounts.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return discounts.toString();
    }

    @ShellMethod("Create a new Discount in the backend (create-discount NAME STORE_ID CASH_PRICE POINT_PRICE)")
    public Discount createDiscount(String name, Long storeId, double cashPrice, int pointPrice) {
        Discount res = restTemplate.postForObject(BASE_URI + DISCOUNTS_URI, new Discount(name, storeId, pointPrice), Discount.class);
        cliContext.getDiscounts().put(res.getId(), res);
        return res;
    }

    @ShellMethod("Get a discount from the catalog of the backend with its id (get-discount-by-id DISCOUNT_ID)")
    public Discount getDiscountById(Long discountId) {
        Discount res = restTemplate.getForObject(BASE_URI+DISCOUNTS_URI+"/" + discountId, Discount.class);
        cliContext.getDiscounts().put(res.getId(), res);
        return res;
    }

    @ShellMethod("Get all the discounts from the catalog of the backend witch match with one store (get-discounts-by-store STORE_ID)")
    public String getDiscountsByStore(Long storeId) {
        Discount[] res = restTemplate.getForObject(BASE_URI + DISCOUNTS_URI + STORE_URI + "/" + storeId, Discount[].class);
        Arrays.stream(res).forEach(discount -> cliContext.getDiscounts().put(discount.getId(), discount));
        return Arrays.toString(res);
    }

    @ShellMethod("Update an existing discount in the backend (update-discount-point-price DISCOUNT_ID POINT_PRICE)")
    public Discount updateDiscountPointPrice(Long discountId, int pointPrice) {
        HttpEntity<Integer> entity = new HttpEntity<Integer>(pointPrice);
        Discount res = restTemplate.exchange(BASE_URI + DISCOUNTS_URI + "/" + discountId, HttpMethod.PUT, entity, Discount.class).getBody();
        cliContext.getDiscounts().put(res.getId(), res);
        return res;
    }

    @ShellMethod("Delete a discount in the backend (delete-discount DISCOUNT_ID)")
    public String deleteDiscount(Long discountId) {
        Discount discount = getDiscountById(discountId);
        HttpEntity<Discount> entity = new HttpEntity<Discount>(discount);
        String res = restTemplate.exchange(BASE_URI + DISCOUNTS_URI + "/" + discountId, HttpMethod.DELETE, entity, String.class).getBody();
        cliContext.getDiscounts().remove(discount.getId());
        return res;
    }
}
