package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.Product;
import springboot.entity.UserInfo;
import springboot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/welcome")   // permit all requests to access the resource.
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")   // permit all requests to access the resource.
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")   // access is restricted for Admin only.
    public List<Product> getAllTheProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")   // both Admin and User are allowed to access the resource.
    public Product getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }
}
