package com.aranmakina.backend.service;

import com.aranmakina.backend.dto.user.UserCreateDTO;
import com.aranmakina.backend.dto.user.UserViewDTO;
import com.aranmakina.backend.exception.results.*;
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

    public DataResult<UserViewDTO> addUser(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        userRepository.save(user);
        UserViewDTO userViewDTO = modelMapper.map(user, UserViewDTO.class);
        return new SuccessDataResult<>(userViewDTO, "Kullanıcı eklendi.");
    }

    public Result deleteUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new SuccessResult("Kullanıcı silindi.");
        } else {
            return new ErrorResult("Kullanıcı bulunamadı.");
        }
    }
}