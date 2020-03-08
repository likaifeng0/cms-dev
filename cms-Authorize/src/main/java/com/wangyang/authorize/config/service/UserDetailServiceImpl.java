package com.wangyang.authorize.config.service;

import com.wangyang.authorize.pojo.entity.Permission;
import com.wangyang.authorize.pojo.entity.User;
import com.wangyang.authorize.service.IPermissionService;
import com.wangyang.authorize.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user!=null){
            List<Permission> permissions = permissionService.findByUserId(user.getId());
            permissions.forEach(permission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getEnName());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}
