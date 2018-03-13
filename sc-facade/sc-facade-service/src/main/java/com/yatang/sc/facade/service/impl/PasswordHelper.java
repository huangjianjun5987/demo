package com.yatang.sc.facade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yatang.sc.facade.domain.UserPo;

/**
 * @描述: 密码加密帮助类.
 * @作者: huangjianjun
 * @创建时间: 2017年6月8日 下午9:44:09 .
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void encryptPassword(UserPo user) {

        if(StringUtils.isEmpty(user.getSalt())) {
            user.setSalt(randomNumberGenerator.nextBytes().toHex());
        }

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt())).toHex();

        user.setPassword(newPassword);
    }
}
