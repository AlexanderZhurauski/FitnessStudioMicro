package org.mycompany.product.web.controllers;

import org.mycompany.product.core.dto.product.ProductCreateDTO;
import org.mycompany.product.core.dto.product.ProductDTO;
import org.mycompany.product.service.api.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateDTO product) {
        this.productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public Page<ProductDTO> getUserPage(Pageable pageable) {
        return this.productService.getPage(pageable);
    }

    @PutMapping("/{uuid}/dt_update/{lastUpdated}")
    public ResponseEntity<String> updateUser(@PathVariable UUID uuid,
                                              @PathVariable Instant lastUpdated,
                                              @RequestBody ProductCreateDTO product) {
        this.productService.update(uuid, lastUpdated, product);
        return ResponseEntity.ok()
                .build();
    }
}
