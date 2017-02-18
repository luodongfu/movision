package com.movision.mybatis.coupon.service;

import com.movision.mybatis.coupon.entity.Coupon;
import com.movision.mybatis.coupon.mapper.CouponMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shuxf
 * @Date 2017/2/18 11:11
 */
@Service
public class CouponService {

    @Autowired
    private CouponMapper couponMapper;

    private Logger log = LoggerFactory.getLogger(CouponService.class);

    public List<Coupon> queryCanUseCoupon(int userid) {
        try {
            log.info("根据用户id查询用户名下所有可用优惠券列表");
            return couponMapper.queryCanUseCoupon(userid);
        } catch (Exception e) {
            log.error("根据用户id查询用户名下所有可用优惠券列表失败");
            throw e;
        }
    }

    public List<Coupon> queryBeUsedCoupon(int userid) {
        try {
            log.info("根据用户id查询用户名下所有已用优惠券列表");
            return couponMapper.queryBeUsedCoupon(userid);
        } catch (Exception e) {
            log.error("根据用户id查询用户名下所有已用优惠券列表失败");
            throw e;
        }
    }

    public List<Coupon> queryHaveOverdueCoupon(int userid) {
        try {
            log.info("根据用户id查询用户名下所有过期失效的优惠券列表");
            return couponMapper.queryHaveOverdueCoupon(userid);
        } catch (Exception e) {
            log.error("根据用户id查询用户名下所有过期失效的优惠券列表失败");
            throw e;
        }
    }
}
