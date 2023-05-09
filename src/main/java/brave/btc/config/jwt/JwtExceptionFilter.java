package brave.btc.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(request, response, e);
        }
    }

    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable e) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Unauthorized");
        body.put("message", e.getMessage());
        body.put("path", request.getServletPath());

        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mapper.writeValue(response.getOutputStream(), body);
    }

}
