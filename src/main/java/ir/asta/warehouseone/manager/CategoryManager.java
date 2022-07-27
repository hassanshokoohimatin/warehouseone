package ir.asta.warehouseone.manager;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryManager {

    public String categoryCodeGenerator(){
        return UUID.randomUUID().toString();
    }
}
