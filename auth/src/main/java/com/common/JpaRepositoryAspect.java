package com.common;

import com.cotton.model.base.BaseModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;

/**
 * 处理JpaRepository接口中相关方法的切面逻辑
 *
 * @author yongfei.zheng
 * @date 2016年9月30日 下午2:10:40
 */
@Aspect
public class JpaRepositoryAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository.save*(..))")
    public void saveMethod() {
    }

    /**
     * 所有save方法中,如果id不为null则设置updateTime为当前时间,
     * 如果id为null设置createTime和updateTime为当前时间
     *
     * @param pj
     * @throws Throwable
     * @author yongfei.zheng
     * @date 2016年9月30日 下午3:08:33
     */
    @Around("saveMethod()")
    public void setCreateAndUpdateTime(ProceedingJoinPoint pj) throws Throwable {

        Object[] args = pj.getArgs();
        if (args != null && args.length == 1) {
            Object model = args[0];

            if (model != null && model instanceof BaseModel) {
                BaseModel baseModel = (BaseModel) model;
                baseModel.setUpdateTime(new Date());
                if (baseModel.getCreateTime() == null) {
                    baseModel.setCreateTime(baseModel.getUpdateTime());
                }
            } else if (model != null && model instanceof Iterable<?>) {
                Iterator<BaseModel> iterator = ((Iterable<BaseModel>) model).iterator();
                while (iterator.hasNext()) {
                    BaseModel baseModel = iterator.next();
                    baseModel.setUpdateTime(new Date());
                    if (baseModel.getCreateTime() == null) {
                        baseModel.setCreateTime(baseModel.getUpdateTime());
                    }
                }
            }

        }
        pj.proceed();
    }

}