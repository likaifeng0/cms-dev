package com.wangyang.authorize.config.service;


import com.wangyang.authorize.pojo.dto.SpringUserDto;
import com.wangyang.authorize.utils.SecurityUtils;
import com.wangyang.data.repository.UserRepository;
import com.wangyang.data.service.IRoleService;
import com.wangyang.data.service.IUserService;
import com.wangyang.model.pojo.entity.Role;
import com.wangyang.model.pojo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;


    @Autowired
    private IRoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")){
            return new org.springframework.security.core.userdetails.User("admin","123456",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

//        User user = userService.findByUsername(username);
//        if(user==null){
//            throw  new UsernameNotFoundException("用户名不存在!!");
//        }
//        if(user!=null){
//            List<Role> roles = roleService.findByUserId(user.getId());
//            if(CollectionUtils.isEmpty(roles)){
//                return null;
//            }
//            user.setRoles(roles);
//        }
        return findByUsername(username);
    }

    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userService.add(user);
        return saveUser;
    }

    public SpringUserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("用户名不存在!!");
        }
        SpringUserDto springUserDto = new SpringUserDto();
        BeanUtils.copyProperties(user, springUserDto);
        List<Role> roles = roleService.findByUserId(user.getId());
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        springUserDto.setRoles(roles);
        return springUserDto;
    }


    public SpringUserDto findById(int id){
        User user = userService.findById(id);
        user.setPassword(null);
        SpringUserDto springUserDto = new SpringUserDto();
        BeanUtils.copyProperties(user, springUserDto);
        List<Role> roles = roleService.findByUserId(user.getId());
        springUserDto.setRoles(roles);
        return springUserDto;
    }

    public SpringUserDto getCurrentUser() {
        Optional<String> username = SecurityUtils.getCurrentUsername();
        return findByUsername(username.get());
    }

    public Optional<String> getCurrentUserName() {
        Optional<String> username = SecurityUtils.getCurrentUsername();
        return username;
    }
}
