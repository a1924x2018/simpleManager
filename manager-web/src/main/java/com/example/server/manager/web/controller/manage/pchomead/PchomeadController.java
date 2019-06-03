package com.example.server.manager.web.controller.manage.pchomead;

import com.example.server.manager.common.enums.ResultEnum;
import com.example.server.manager.common.protocol.ActionResult;
import com.example.server.manager.common.util.ResultTool;
import com.example.server.manager.common.util.TemplateTool;
import com.example.server.manager.dao.mysql.domain.Pchomead;
import com.example.server.manager.service.pchomead.PchomeadService;
import com.example.server.manager.web.controller.base.BaseController;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class PchomeadController extends BaseController {

    @Value("${qiniu.imageUpload.domain}")
    private String domain;
    @Autowired
    private PchomeadService pchomeadService;

    @RequestMapping("/pchomead")
    public String indexRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            prepare(request, response, model, "indexRequest");
            return "manage/screen/pchomead/index";
        } catch (Throwable e) {
            log.error("PchomeadController.indexRequest error", e);
            return TemplateTool.exception(model, TemplateTool.HOME_LAYOUT_DEFAULT_VM, e);
        }
    }

    @RequestMapping("/pchomead/add")
    public String addRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            prepare(request, response, model, "addRequest");
            return "manage/screen/pchomead/add";
        } catch (Throwable e) {
            log.error("PchomeadController.addRequest error", e);
            return TemplateTool.exception(model, TemplateTool.HOME_LAYOUT_DEFAULT_VM, e);
        }
    }

    @RequestMapping("/pchomead/view/{id}")
    public String viewRequest(HttpServletRequest request, HttpServletResponse response, Model model,
                              @PathVariable("id") Long id) {
        try {
            prepare(request, response, model, "viewRequest");
            Pchomead pchomead = pchomeadService.getPchomead(id);
            pchomeadImageUrlAppendDomain(pchomead);
            model.addAttribute("pchomead", pchomead);
            return "manage/screen/pchomead/view";
        } catch (Throwable e) {
            log.error("PchomeadController.viewRequest error", e);
            return TemplateTool.exception(model, TemplateTool.HOME_LAYOUT_DEFAULT_VM, e);
        }
    }

    @RequestMapping("/pchomead/edit/{id}")
    public String editRequest(HttpServletRequest request, HttpServletResponse response, Model model,
                              @PathVariable("id") Long id) {
        try {
            prepare(request, response, model, "editRequest");
            Pchomead pchomead = pchomeadService.getPchomead(id);
            pchomeadImageUrlAppendDomain(pchomead);
            model.addAttribute("pchomead", pchomead);
            return "manage/screen/pchomead/edit";
        } catch (Throwable e) {
            log.error("PchomeadController.editRequest error", e);
            return TemplateTool.exception(model, TemplateTool.HOME_LAYOUT_DEFAULT_VM, e);
        }
    }

    @PostMapping("/json/pchomead/create")
    @ResponseBody
    public Object createPchomead(HttpServletRequest request, HttpServletResponse response,
                                 Pchomead pchomead) {
        try {
            prepare(request, response);
            if (pchomead == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "pchomead must be filled");
            }
            if (pchomead.getImageUrl() == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "imageUrl must be filled");
            }
            if (pchomead.getImageUrl().length() > 255) {
                return ResultTool.error(ResultEnum.PARAM_ERROR.getCode(), "imageUrlLengthError");
            }
            if (pchomead.getOperator() == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "operator must be filled");
            }
            if (pchomead.getGmtStart() != null && pchomead.getGmtEnd() != null) {
                if (pchomead.getGmtEnd().compareTo(pchomead.getGmtStart()) <= 0) {
                    return ResultTool.error(ResultEnum.PARAM_ERROR.getCode(), "gmtError");
                }
            }
            pchomead.setImageUrl(pchomead.getImageUrl().replace(domain, ""));
            if (pchomeadService.createPchomead(pchomead)) {
                return ResultTool.success();
            }
            return ResultTool.error();
        } catch (Throwable e) {
            log.error("PchomeadController.createPchomead error", e);
            return ResultTool.exception(e);
        }
    }

    @PostMapping("/json/pchomead/query/list")
    @ResponseBody
    public Object queryPchomeadList(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "limit", defaultValue = "10") int pagesize,
                                    @RequestParam(value = "filterWord", required = false) String filterWord) {
        try {
            prepare(request, response);
            ActionResult result = new ActionResult();
            List<Pchomead> pchomeadList;
            if (StringUtils.isBlank(filterWord)) {
                pchomeadList = pchomeadService.getPchomeadList(page, pagesize);
            } else {
                result.addResult("filterWord", filterWord);
                pchomeadList = pchomeadService.getPchomeadListByCondition(page, pagesize, filterWord);
            }
            // 取分页后结果
            PageInfo<Pchomead> pageInfo = new PageInfo<Pchomead>(pchomeadList);
            result.addResult("count", String.valueOf(pageInfo.getTotal()));
            for (Pchomead pchomead: pchomeadList) {
                pchomeadImageUrlAppendDomain(pchomead);
            }
            result.addResult("data", pchomeadList);
            return ResultTool.success(result);
        } catch (Throwable e) {
            log.error("PchomeadController.queryPchomeadList error", e);
            return ResultTool.exception(e);
        }
    }

    @PostMapping("/json/pchomead/edit")
    @ResponseBody
    public Object editPchomead(HttpServletRequest request, HttpServletResponse response,
                               Pchomead pchomead) {
        try {
            prepare(request, response);
            if (pchomead == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "pchomead must be filled");
            }
            if (pchomead.getImageUrl() == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "imageUrl must be filled");
            }
            if (pchomead.getImageUrl().length() > 255) {
                return ResultTool.error(ResultEnum.PARAM_ERROR.getCode(), "imageUrlLengthError");
            }
            if (pchomead.getOperator() == null) {
                return ResultTool.error(ResultEnum.PARAM_ERROR, "operator must be filled");
            }
            if (pchomead.getGmtStart() != null && pchomead.getGmtEnd() != null) {
                if (pchomead.getGmtEnd().compareTo(pchomead.getGmtStart()) <= 0) {
                    return ResultTool.error(ResultEnum.PARAM_ERROR.getCode(), "gmtError");
                }
            }
            if (pchomeadService.updatePchomead(pchomead)) {
                return ResultTool.success();
            }
            return ResultTool.error();
        } catch (Throwable e) {
            log.error("PchomeadController.editPchomead error", e);
            return ResultTool.exception(e);
        }
    }

    @PostMapping("/json/pchomead/status/edit")
    @ResponseBody
    public Object editPchomeadStatus(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam Integer value,
                                     @RequestParam Long id) {
        try {
            prepare(request, response);
            if (value == null || id == null || StringUtils.isBlank(operator)) {
                return ResultTool.error(ResultEnum.PARAM_ERROR);
            }
            if (pchomeadService.updatePchomeadStatusById(id, value, operator)) {
                return ResultTool.success();
            }
            return ResultTool.error();
        } catch (Throwable e) {
            log.error("PchomeadController.editPchomeadStatus error", e);
            return ResultTool.exception(e);
        }
    }

    @PostMapping("/json/pchomead/delete")
    @ResponseBody
    public Object deletePchomead(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam Long id) {
        try {
            prepare(request, response);
            if (id == null || StringUtils.isBlank(operator)) {
                return ResultTool.error(ResultEnum.PARAM_ERROR);
            }
            if (pchomeadService.deletePchomeadById(id, operator)) {
                return ResultTool.success();
            }
            return ResultTool.error();
        } catch (Throwable e) {
            log.error("PchomeadController.deletePchomead error", e);
            return ResultTool.exception(e);
        }
    }

    private void pchomeadImageUrlAppendDomain(Pchomead pchomead){
        if (!pchomead.getImageUrl().contains("http://") && !pchomead.getImageUrl().contains("https://")) {
            StringBuilder builder = new StringBuilder();
            pchomead.setImageUrl(builder.append(domain).append(pchomead.getImageUrl()).toString());
        }
    }
}
