package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.featurename.FeatureNameViewDTO;
import com.aranmakina.backend.dto.user.UserViewDTO;
import com.aranmakina.backend.exception.results.DataResult;
import com.aranmakina.backend.exception.results.ErrorDataResult;
import com.aranmakina.backend.exception.results.SuccessDataResult;
import com.aranmakina.backend.model.FeatureName;
import com.aranmakina.backend.model.User;
import com.aranmakina.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<List<UserViewDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserViewDTO> userViewDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(userViewDTOs, "Kullanıcılar listelendi.");
    }
}
