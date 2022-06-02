package com.ecommerce.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class EcommerceErrorController implements ErrorController {


    @GetMapping("/public/error")
    public String handleError(HttpServletRequest request) {
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
            String queryString = request.getQueryString();

            String url;
            if (StringUtils.isBlank(queryString)) {
                url = requestURL.toString();
            } else {
                url = requestURL.append('?').append(queryString).toString();
            }
            var statusCode = Integer.parseInt(status.toString());
            log.debug("Error endpoint called. Status: {} {}", statusCode, url);
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "401";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403";
            }
        }
        return "error";
    }
}
