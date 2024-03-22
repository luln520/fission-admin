package net.lab1024.sa.admin.lock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.common.RedissonLockUtils;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁/防重复提交 aop
 */
@Aspect
@Component
@Slf4j
public class RedissonLockAop {

    /**
     * 切点，拦截被 @RedissonLockAnnotation 修饰的方法
     */
    @Pointcut("@annotation(net.lab1024.sa.admin.lock.RedissonLockAnnotation)")
    public void redissonLockPoint() {
    }

    @Around("redissonLockPoint()")
    @ResponseBody
    public ResponseDTO checkLock(ProceedingJoinPoint pjp) throws Throwable {
        String language = "";
        try {
            //当前线程名
            String threadName = Thread.currentThread().getName();
            log.info("线程{}------进入分布式锁aop------", threadName);
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] values = pjp.getArgs();
            String[] names = ((CodeSignature) pjp.getSignature()).getParameterNames();
            for (int i = 0; i < names.length; i++) {
                map.put(names[i], values[i]);
            }
            String param = "";
            if(map.size() == 1){
                if(StringUtils.isNotEmpty(map.get("userReq").toString())){
                    Object object = map.get("userReq");
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
                    param = jsonObject.get("username").toString();
                }

            }else{
                language = map.get("language").toString();
                if(StringUtils.isNotEmpty(map.get("uid").toString())){
                    param = map.get("uid").toString();
                }
            }


//        //获取该注解的实例对象
//        RedissonLockAnnotation annotation = ((MethodSignature) pjp.getSignature()).
//                getMethod().getAnnotation(RedissonLockAnnotation.class);
//        //生成分布式锁key的键名，以逗号分隔
//        String keyParts = annotation.keyParts();
            StringBuffer keyBuffer = new StringBuffer();
            if (StringUtils.isEmpty(param)) {
                log.info("线程{} keyParts设置为空，不加锁", threadName);
                return (ResponseDTO) pjp.proceed();
            } else {
                keyBuffer.append(param);
                String key = keyBuffer.toString();
                log.info("线程{} 要加锁的key={}", threadName, key);
                //获取锁
                if (RedissonLockUtils.tryLock(key, 3000, 5000, TimeUnit.MILLISECONDS)) {
                    try {
                        log.info("线程{} 获取锁成功", threadName);
                        return (ResponseDTO) pjp.proceed();
                    } finally {
                        RedissonLockUtils.unlock(key);
                        log.info("线程{} 释放锁", threadName);
                    }
                } else {
                    log.info("线程{} 获取锁失败", threadName);
                    if(language.equals("zh")){
                        return  ResponseDTO.userErrorParam("请求超时");
                    }else{
                        return  ResponseDTO.userErrorParam("Request timed out");
                    }
                }
            }
        }catch (Exception e){
            if(language.equals("zh")){
                return  ResponseDTO.userErrorParam("操作繁忙，请稍后重试");
            }else{
                return  ResponseDTO.userErrorParam("Operation is busy, please try again later");
            }
        }
    }
}
