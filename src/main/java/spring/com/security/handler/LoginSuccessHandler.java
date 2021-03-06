package spring.com.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);


                handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttributes(httpServletRequest);
    }





    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            }
        }

        if (isUser) {
            return "/user";

        } else if (isAdmin) {
            return "/admin";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}










//package spring.com.security.handler;
//
//        import org.springframework.security.core.Authentication;
//        import org.springframework.security.core.GrantedAuthority;
//        import org.springframework.security.web.DefaultRedirectStrategy;
//        import org.springframework.security.web.RedirectStrategy;
//        import org.springframework.security.web.WebAttributes;
//        import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//        import org.springframework.stereotype.Component;
//
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.HttpSession;
//        import java.io.IOException;
//        import java.util.Collection;
//
//@Component
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication authentication)
//            throws IOException {
//
//        handle(request, response, authentication);
//        clearAuthenticationAttributes(request);
//    }
//
//    protected void handle(HttpServletRequest request,
//                          HttpServletResponse response, Authentication authentication)
//            throws IOException {
//
//        String targetUrl = determineTargetUrl(authentication);
//
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
//
//    protected String determineTargetUrl(Authentication authentication) {
//        boolean isUser = false;
//        boolean isAdmin = false;
//        Collection<? extends GrantedAuthority> authorities
//                = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//                isAdmin = true;
//                break;
//            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
//                isUser = true;
//                break;
//            }
//        }
//
//        if (isUser) {
////            return "/hello";
//            return "/user";
//
//        } else if (isAdmin) {
//            return "/users";
//        } else {
//            throw new IllegalStateException();
//        }
//    }
//
//    protected void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return;
//        }
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }
//
//    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
//        this.redirectStrategy = redirectStrategy;
//    }
//
//    protected RedirectStrategy getRedirectStrategy() {
//        return redirectStrategy;
//    }
//
//
//
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
////                                        HttpServletResponse httpServletResponse,
////                                        Authentication authentication) throws IOException {
////        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
////
////        boolean admin = false;
////
////        for (GrantedAuthority auth : authentication.getAuthorities()) {
////            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
////                admin = true;
////            }
////        }
////
////        if (admin) {
////            httpServletResponse.sendRedirect("/admin");
////        } else {
////            httpServletResponse.sendRedirect("/user");
////        }
////    }
//}
