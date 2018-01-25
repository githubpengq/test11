package com.cotton.service.version;

import com.common.AuthCommonService;
import com.cotton.dao.version.VersionDAO;
import com.cotton.model.version.AppVersion;
import com.cotton.result.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台用户
 *
 * @author qing.peng
 * @date 2017年6月16日上午9:56:24
 */
@Service
public class VersionService extends AuthCommonService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private VersionDAO versionDAO;

    /**
     * 添加版本
     *
     * @param appVersion
     * @return
     */
    public Result doAddVersion(AppVersion appVersion) {
        Result result = new Result();
        AppVersion last = getLastVersion(appVersion.getAppType());
        if (last != null && StringUtils.isNotBlank(last.getAppVersion())) {
            String versionDB = StringUtils.replace(last.getAppVersion(), ".", "");
            String versionIN = StringUtils.replace(appVersion.getAppVersion(), ".", "");
            if (NumberUtils.toInt(versionIN) < NumberUtils.toInt(versionDB)) {
                result.setSuccess(false);
                result.setMsg("app版本不能比当前版本低");
                return result;
            }
        }
        this.versionDAO.save(appVersion);
        result.setSuccess(true);
        result.setMsg("添加成功");
        return result;
    }

    /**
     * 获取最新版本信息
     *
     * @param appType
     * @return
     */
    public AppVersion getLastVersion(String appType) {
        List<AppVersion> list = versionDAO.findByAppTypeOrderByIdDesc(appType);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        System.out.println("最新版本信息ID："+list.get(0).getId());
        return list.get(0);
    }

}