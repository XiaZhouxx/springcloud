package com.xz.oauth.server.config;

import com.xz.oauth.server.domain.TbPermission;
import com.xz.oauth.server.domain.TbUser;
import com.xz.oauth.server.service.impl.TbPermissionService;
import com.xz.oauth.server.service.impl.TbUserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xz
 * @Description 自定义校验用户和获取认证的权限名
 * @date 2020/2/14 0014 14:33
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    TbUserService tbUserService;

    @Autowired
    TbPermissionService tbPermissionService;

    /**
     * Security 用于查询角色权限信息
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 用户用于认证
        TbUser user = tbUserService.getByUserName(s);
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        checkUser(user);
        if(user != null) {
            // 获取用户的权限 用于授权
            List<TbPermission> permissions = tbPermissionService.findByUserId(user.getId());
            if(!CollectionUtils.isEmpty(permissions)) {
                permissions.forEach(p -> {
                    // 用于系统标识的权限名 也可以直接值只查询这个字段 返回一个 String []
                /*List<String> permissions = tbPermissionService.findByUserId(user.getId());
                String[] permiss = new String[permissions.size()];
                permissions.toArray(permiss);
                User.withUsername("").password("").authorities(permiss);*/
                    // enname = 权限名 访问某个资源拥有某个权限才能访问 获取这个用户的所有权限 + jwt中
                    GrantedAuthority g = new SimpleGrantedAuthority(p.getEnname());
                    grantedAuthorities.add(g);
                });
            }
            return new LoginUser(user.getId(), user.getUsername(), user.getPassword(), grantedAuthorities);
        }
        return null;
    }

    /**
     * 封装校验用户逻辑
     * @author xz
     * @date 2020/6/5
     */
    private void checkUser(TbUser user) {
        if (user == null) {
            throw new UsernameNotFoundException("用户名有误！");
        }
    }
}
