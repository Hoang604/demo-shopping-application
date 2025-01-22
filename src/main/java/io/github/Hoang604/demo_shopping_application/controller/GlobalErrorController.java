package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public RedirectView handleError(HttpServletRequest request) {
        // Lấy mã trạng thái lỗi (ví dụ: 404, 500)
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Nếu là lỗi 404, chuyển hướng 301 về /home
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                RedirectView redirectView = new RedirectView("/home", true);
                redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // 301
                return redirectView;
            }
        }

        // Nếu là lỗi khác (500, 403...), chuyển hướng tạm thời (tuỳ chỉnh)
        return new RedirectView("/error-page", true); // Thay "/error-page" bằng URL của bạn
    }
}