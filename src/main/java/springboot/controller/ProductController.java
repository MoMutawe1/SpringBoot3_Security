package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springboot.dto.AuthRequest;
import springboot.dto.Product;
import springboot.dto.UserInfo;
import springboot.service.JwtService;
import springboot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    // we should ask spring security to bypass this endpoint for all upcoming requests.
    // otherwise no one will be able to access it if we authenticate it.. so anyone should be able to pass their username and password and get a token.
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // verify username & password from DB.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }
}
