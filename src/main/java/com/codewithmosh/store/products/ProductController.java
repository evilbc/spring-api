package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(@RequestParam Optional<Byte> categoryId) {
        List<Product> products;
        if (categoryId.isPresent()) {
            products = productRepository.findByCategoryId(categoryId.get());
        } else {
            products = productRepository.findWithCategory();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto request,
                                                    UriComponentsBuilder uriBuilder) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) return ResponseEntity.badRequest().build();
        Product product = productMapper.toProduct(request);
        product.setCategory(category.get());
        productRepository.save(product);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        ProductDto dto = productMapper.toDto(product);
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto request, @PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();
        productMapper.update(request, product.get());
        product.get().setCategory(category.get());
        productRepository.save(product.get());
        return ResponseEntity.ok(productMapper.toDto(product.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();

        productRepository.delete(product.get());
        return ResponseEntity.noContent().build();
    }
}
