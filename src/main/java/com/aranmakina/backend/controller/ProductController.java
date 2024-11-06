package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.product.ProductCreateDTO;
import com.aranmakina.backend.dto.product.ProductViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DataResult<ProductViewDTO>> add(@RequestBody ProductCreateDTO productCreateDTO){
        return new ResponseEntity<>(productService.add(productCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Result> delete(@RequestParam Integer productId){
        return new ResponseEntity<>(productService.delete(productId), HttpStatus.OK);
    }
}
