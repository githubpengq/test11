package com.cotton.web.version;

import com.common.AuthBaseAction;
import com.cotton.model.version.AppVersion;
import com.cotton.result.Result;
import com.cotton.service.version.VersionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * weiwei.li
 */
@Controller
@RequestMapping("authApp/version")
public class AppVersionAction extends AuthBaseAction {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private VersionService versionService;

    @RequestMapping("/getAppVersion")
    @ResponseBody
    public Result getAppVersion(HttpServletRequest request) {
        Result result = new Result();
        String from = request.getParameter("from");
        try {
            AppVersion appVersion = this.versionService.getLastVersion(from);
            if (appVersion == null) {
                result.setSuccess(false);
                result.setMsg("未查询到版本信息");
            } else {
                result.setSuccess(true);
                result.setMsg("查询成功");
                result.setValue(appVersion);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("网络异常");
        }
        return result;
    }
}