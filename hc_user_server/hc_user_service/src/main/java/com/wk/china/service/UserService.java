package com.wk.china.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hchina.common.enums.ExceptionEnums;
import com.hchina.common.exception.HChinaException;
import com.hchina.common.pojo.PageResult;
import com.hchina.common.utils.CodecUtils;
import com.hchina.common.utils.NumberUtils;
import com.wk.china.mapper.UserMapper;
import com.wk.user.pojo.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringValueResolver;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    static final String KEY_PREFIX = "user:code:phone:";




    /**
     * 发送短信
     * @param phone
     * @return
     */
    public Boolean sendVertifyCode(String phone) {
        //1、生成验证码
//        String code = NumberUtils.generateCode(6);
//
//        try {
//            //2、保存redis
//            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
//            //3、发送消息
//            Map<String, String> map = new HashMap<>();
//            map.put("code", code);
//            map.put("phone", phone);
//            amqpTemplate.convertAndSend("wlkg.sms.exchange", "sms.vertify.code", map);
//            return true;
//        }catch (AmqpException e){
//            e.printStackTrace();
//            return false;
//        }
        //1.生成验证码
        String code = NumberUtils.generateCode(6);

        try {
            //2.保存redis
            //user:code:phone:18018121321
            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);

            //3.发送消息

            Map<String, String> map = new HashMap<>();
            map.put("code", code);
            map.put("phone", phone);
            amqpTemplate.convertAndSend("wlkg.sms.exchange", "sms.verify.code", map);
            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User check(User user) {
        return userMapper.selectOne(user);
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     */
    public void register(User user, String code) {
        //- 1）校验短信验证码
        String prefix = KEY_PREFIX + user.getPhone();
        String redisCode = redisTemplate.opsForValue().get(prefix);
        System.out.println(redisCode);
        // 检查验证码是否正确
        if (!code.equals(redisCode)) {
            // 不正确，返回
            throw new HChinaException(ExceptionEnums.INVALID_VERFIY_CODE);
        }
        //- 2）生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //- 3）对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        //- 4）写入数据库
        user.setCreated(new Date());

        if (userMapper.insert(user) == 1) {
            //- 5）删除Redis中的验证码
            redisTemplate.delete(prefix);
        }
    }

    /**
     * 实现用户查询
     * @param username
     * @param password
     * @return
     */

    public User queryUser(String username, String password) {
        //1、现根据用户名查询
        User user = new User();
        user.setUsername(username);
        User resUser = userMapper.selectOne(user);
        if (resUser == null){
            throw new HChinaException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }
        //比对密码
        String md5Pwd = CodecUtils.md5Hex(password,resUser.getSalt());
        if (!md5Pwd.equals(resUser.getPassword())){
            throw new HChinaException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }else {
            return resUser;
        }
    }

    public PageResult<User> queryUserByPage(Integer page, Integer rows) {
        //开始分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(User.class);

        Page<User> pageInfo = (Page<User>) userMapper.selectByExample(example);

        return new PageResult<>(pageInfo.getTotal(),pageInfo);
    }
}