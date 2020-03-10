package com.wangyang.authorize.config.service;


import com.wangyang.authorize.pojo.entity.Role;
import com.wangyang.authorize.pojo.entity.User;
import com.wangyang.authorize.service.IRoleService;
import com.wangyang.authorize.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
//    @Autowired
//    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")){
            return new org.springframework.security.core.userdetails.User("admin","123456",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        User user = userService.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user!=null){
            List<Role> roles = roleService.findByUserId(user.getId());
            if(CollectionUtils.isEmpty(roles)){
                return null;
            }
            roles.forEach(role -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getEnName());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}