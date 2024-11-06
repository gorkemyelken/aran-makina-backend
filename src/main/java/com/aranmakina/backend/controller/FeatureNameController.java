package com.aranmakina.backend.controller;

import com.aranmakina.backend.dto.featurename.FeatureNameCreateDTO;
import com.aranmakina.backend.dto.featurename.FeatureNameViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.Result;
import com.aranmakina.backend.service.FeatureNameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/featurenames")
@CrossOrigin
public class FeatureNameController {

    private final FeatureNameService featureNameService;

    public FeatureNameController(FeatureNameService featureNameService) {
        this.featureNameService = featureNameService;
    }

    @GetMapping
    public ResponseEntity<DataResult<List<FeatureNameViewDTO>>> getAll(){
        return new ResponseEntity<>(featureNameService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<FeatureNameViewDTO>> add(@RequestBody FeatureNameCreateDTO featureNameCreateDTO){
        return new ResponseEntity<>(featureNameService.add(featureNameCreateDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{featureNameId}")
    public ResponseEntity<Result> delete(@RequestParam Integer featureNameId){
        return new ResponseEntity<>(featureNameService.delete(featureNameId), HttpStatus.OK);
    }
}
