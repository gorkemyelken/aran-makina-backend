package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.product.ProductCreateDTO;
import com.aranmakina.backend.dto.product.ProductViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.ProductPhotoService;
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
    private final ProductPhotoService productPhotoService;

    public ProductController(ProductService productService, ProductPhotoService productPhotoService) {
        this.productService = productService;
        this.productPhotoService = productPhotoService;
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

    @PostMapping(value = "/{productId}/addPhotos", consumes = {"multipart/form-data"})
    public ResponseEntity<Result> addPhotos(
            @PathVariable Integer productId,
            @RequestPart("photos") List<MultipartFile> photos) throws IOException {
        Result result = productPhotoService.addPhotosToProduct(productId, photos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Result> delete(@RequestParam Integer productId){
        return new ResponseEntity<>(productService.delete(productId), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DataResult<ProductViewDTO>> findById(@PathVariable Integer productId){
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }
}
