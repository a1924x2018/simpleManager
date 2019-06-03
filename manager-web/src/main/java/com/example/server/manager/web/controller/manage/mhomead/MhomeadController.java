package com.example.server.manager.web.controller.manage.mhomead;

import com.example.server.manager.common.util.TemplateTool;
import com.example.server.manager.web.controller.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class MhomeadController extends BaseController {

    @RequestMapping("/mhomead")
    public String indexRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            prepare(request, response, model, "indexRequest");
            return "manage/screen/mhomead/index";
        } catch (Throwable e) {
            log.error("MhomeadController.indexRequest error", e);
            return TemplateTool.exception(model, TemplateTool.HOME_LAYOUT_DEFAULT_VM, e);
        }
    }
}
