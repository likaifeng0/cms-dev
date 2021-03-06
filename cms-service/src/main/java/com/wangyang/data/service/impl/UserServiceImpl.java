package com.wangyang.data.service.impl;

import com.wangyang.data.repository.UserRepository;
import com.wangyang.data.service.IRoleService;
import com.wangyang.data.service.IUserService;
import com.wangyang.model.pojo.dto.UserDto;
import com.wangyang.model.pojo.entity.Role;
import com.wangyang.model.pojo.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IRoleService roleService;

//    static  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Page<User> pageBy(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public User add(User user){
        User saveUser = userRepository.save(user);
        return saveUser;
    }
    @Override
    public User findById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return  null;
    }

    @Override
    public UserDto findUserDaoById(int id){
        User user = findById(id);
        user.setPassword(null);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        List<Role> roles = roleService.findByUserId(user.getId());
        userDto.setRoles(roles);
        return userDto;
    }


    @Override
    public Optional<User> findOptionalBy(int userId){
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findAllById(Collection<Integer> ids){
        return userRepository.findAllById(ids);
    }
}
