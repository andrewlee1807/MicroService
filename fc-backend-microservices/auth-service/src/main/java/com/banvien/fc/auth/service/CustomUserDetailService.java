package com.banvien.fc.auth.service;

import com.banvien.fc.auth.entities.UserEntity;
import com.banvien.fc.auth.repository.UserRepository;
import com.banvien.fc.auth.security.CustomUserDetail;
import com.banvien.fc.auth.security.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).get(0);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
        CustomUserDetail rs = new CustomUserDetail(user.getUsername(), user.getPassword(), grantedAuthorities);
        Long outletId = userRepository.getOutletId(user.getUserid());
        rs.setOutletId(outletId != null ? outletId: userRepository.getEmployeeOutletId(user.getUserid()));
        rs.setUserId(user.getUserid());
        return rs;
    }

    public UserDetails loadUserByUsernameAndRole(String username, String role, Long countryId) {
//        UserEntity user = userRepository.findByUsernameAndRoleCode(username, role);
        UserEntity user = userRepository.findByUsernameAndRoleCodeAndCountryId(username, role, countryId);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
        CustomUserDetail rs = new CustomUserDetail(username + ":" + role + ":" + countryId, user.getPassword(), grantedAuthorities);
        Long outletId = userRepository.getOutletId(user.getUserid());
        rs.setOutletId(outletId != null ? outletId: userRepository.getEmployeeOutletId(user.getUserid()));
        rs.setUserId(user.getUserid());
        rs.setCountryId(countryId);
        return rs;
    }

    public UserDetails loadUserByUsernameAndPassword(String username, String password) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Long outletIdRs = null;
        Long userIdRs = null;
        List<UserEntity> entities = userRepository.findByUsernameAndPassword(username, MyPasswordEncoder.getInstance().encode(password));
        for (UserEntity entity : entities) {
            if (entity.getRole() != null) {
                grantedAuthorities.add(new SimpleGrantedAuthority(entity.getRole().getCode()));
            }
            Long outletId = userRepository.getOutletId(entity.getUserid());
            if (outletId != null) {
                outletIdRs = outletId;
                userIdRs = entity.getUserid();
            }
        }
        CustomUserDetail rs = new CustomUserDetail(username, entities.get(0).getPassword(), grantedAuthorities);
        rs.setOutletId(outletIdRs);
        rs.setUserId(userIdRs);
        if (userIdRs == null) {
            rs.setUserId(entities.get(0).getUserid());
        }
        return rs;
    }
}
