package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.featurename.FeatureNameCreateDTO;
import com.aranmakina.backend.dto.featurename.FeatureNameViewDTO;
import com.aranmakina.backend.exception.results.*;
import com.aranmakina.backend.model.FeatureName;
import com.aranmakina.backend.repository.FeatureNameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureNameService {

    private final FeatureNameRepository featureNameRepository;

    private final ModelMapper modelMapper;

    public FeatureNameService(FeatureNameRepository featureNameRepository, ModelMapper modelMapper) {
        this.featureNameRepository = featureNameRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<List<FeatureNameViewDTO>> getAll() {
        List<FeatureName> featureNames = featureNameRepository.findAll();
        if (featureNames.isEmpty()) {
            return new ErrorDataResult<>("Özellik isimleri bulunamadı.");
        }
        List<FeatureNameViewDTO> featureNameViewDTOs = featureNames.stream()
                .map(featureName -> modelMapper.map(featureName, FeatureNameViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(featureNameViewDTOs, "Özellik isimleri listelendi.");
    }

    public DataResult<FeatureNameViewDTO> add(FeatureNameCreateDTO featureNameCreateDTO) {
        FeatureName featureName = modelMapper.map(featureNameCreateDTO, FeatureName.class);
        featureNameRepository.save(featureName);
        FeatureNameViewDTO featureNameViewDTO = modelMapper.map(featureName, FeatureNameViewDTO.class);
        return new SuccessDataResult<>(featureNameViewDTO, "Özellik ismi eklendi.");
    }

    public Result delete(Integer featureNameId) {
        this.featureNameRepository.deleteById(featureNameId);
        return new SuccessResult("Özellik ismi silindi.");
    }
}
