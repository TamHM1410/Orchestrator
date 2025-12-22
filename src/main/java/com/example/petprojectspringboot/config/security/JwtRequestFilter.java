package com.example.petprojectspringboot.config.security;

import com.example.petprojectspringboot.user.UserEntity;
import com.example.petprojectspringboot.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    public  final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer ")){
            filterChain.doFilter(request, response);

            return;
        }

        try{
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                System.out.println(token);

                Date expiration = jwtTokenUtils.getExpiration(token);

                if(expiration!=null){
                    Date now = new Date((System.currentTimeMillis()));
                    if(!expiration.before(now)){

                        Claims claims = jwtTokenUtils.parseToken(token);
                        String username = claims.getSubject();

                        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){

                            UserDetails userDetail=customUserDetailService.loadUserByUsername(username);

                            if(userDetail != null){
                                Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();
                                if(authorities == null) {
                                    authorities = new ArrayList<>();
                                }

                                UsernamePasswordAuthenticationToken authToken =
                                        new UsernamePasswordAuthenticationToken(userDetail, null, authorities);

                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                            }


                        }
                        filterChain.doFilter(request, response);


                    }else{
                        throw new RuntimeException("Expired or invalid JWT token");
                    }
                }
            }

        }catch (Exception e){
            throw new RuntimeException("Expired or invalid JWT token"+e.getMessage());
        }


    }
}
