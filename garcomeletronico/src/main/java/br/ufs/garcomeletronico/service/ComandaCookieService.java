package br.ufs.garcomeletronico.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class ComandaCookieService {

    private static final String COOKIE_NAME = "comandaId";

    // Salvar a comanda no cookie
    public void salvar(HttpServletResponse response, String comandaId) {
        Cookie cookie = new Cookie(COOKIE_NAME, comandaId);
        cookie.setMaxAge(24 * 60 * 60); // 1 dia em segundos
        cookie.setPath("/"); // cookie disponível em todas as rotas
        cookie.setHttpOnly(false); 
        response.addCookie(cookie);
    }

    // Resgatar a comanda do cookie
    public String resgatar(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null; // não encontrado
    }

    // Limpar o cookie
    public void limpar(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0); // expira imediatamente
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

