package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.product.ProductCreateDTO;
import com.aranmakina.backend.dto.product.ProductUpdateDTO;
import com.aranmakina.backend.dto.product.ProductViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<DataResult<List<ProductViewDTO>>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<ProductViewDTO>> add(@RequestBody ProductCreateDTO productCreateDTO) {
        DataResult<ProductViewDTO> result = productService.add(productCreateDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Result> delete(@RequestParam Integer productId){
        return new ResponseEntity<>(productService.delete(productId), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DataResult<ProductViewDTO>> findById(@PathVariable Integer productId){
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<DataResult<ProductViewDTO>> update(
            @PathVariable Integer productId,
            @RequestBody ProductUpdateDTO productUpdateDTO) {
        DataResult<ProductViewDTO> result = productService.update(productId, productUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<DataResult<List<ProductViewDTO>>> getByCategory(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(productService.getProductsByCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<ProductViewDTO>>> searchProducts(@RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProducts(keyword), HttpStatus.OK);
    }
}
