package application.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    public  final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailService customUserDetailService;
    private final String xApiKey="xxx-api-key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestXapiKey=request.getHeader("xApiKey");
        System.out.println( request.getRequestId()+"request id"+requestXapiKey);

        if(requestXapiKey==null||!requestXapiKey.equals(xApiKey)){
           throw  new RuntimeException("No permission");
        }

        String token = request.getHeader("Authorization");

        if(token==null || !token.startsWith("Bearer ")){
            filterChain.doFilter(request, response);

            return;
        }

        try{
            token = token.substring(7);

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

        }catch (Exception e){
            throw new RuntimeException("Expired or invalid JWT token"+e.getMessage());
        }


    }
}
