package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.productfeature.ProductFeatureCreateDTO;
import com.aranmakina.backend.dto.productfeature.ProductFeatureViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.ProductFeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productfeatures")
@CrossOrigin
public class ProductFeatureController {
    private final ProductFeatureService productFeatureService;

    public ProductFeatureController(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    @GetMapping
    public ResponseEntity<DataResult<List<ProductFeatureViewDTO>>> getAll(){
        return new ResponseEntity<>(productFeatureService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<ProductFeatureViewDTO>> add(@RequestBody ProductFeatureCreateDTO productFeatureCreateDTO){
        return new ResponseEntity<>(productFeatureService.add(productFeatureCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productFeatureId}")
    public ResponseEntity<Result> delete(@RequestParam Integer productFeatureId){
        return new ResponseEntity<>(productFeatureService.delete(productFeatureId), HttpStatus.OK);
    }
}
