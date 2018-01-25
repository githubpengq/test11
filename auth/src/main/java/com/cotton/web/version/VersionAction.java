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
@RequestMapping("auth/version")
public class VersionAction extends AuthBaseAction {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private VersionService versionService;
    @RequestMapping("/doAddVersion")
    @ResponseBody
    public Result doAddVersion(HttpServletRequest request, AppVersion appVersion) {
        Result result = new Result();
        try {
            appVersion.setAppName("兴疆棉");
            appVersion.setAppVersion("0.0.1");
            appVersion.setAppType("ios");
            appVersion.setForceUpdate(1);
            result = this.versionService.doAddVersion(appVersion);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("网络异常");
        }
        return result;
    }
}