package com.movision.facade.boss;

import com.movision.common.constant.JurisdictionConstants;
import com.movision.mybatis.bossUser.entity.BossUser;
import com.movision.mybatis.bossUser.service.BossUserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于权限认证
 *
 * @Author zhurui
 * @Date 2017/4/5 11:46
 */
@Service
public class commonalityFacade {

    @Autowired
    private BossUserService bossUserService;


    /**
     * @param userid    登录用户
     * @param operation 操作(增删改查)
     * @param kind      操作种类(帖子，评论)
     * @param kindid    种类id
     * @return
     */
    public Map verifyUserJurisdiction(Integer userid, Integer operation, Integer kind, Integer kindid) {
        Map map = new HashMap();
        BossUser i = bossUserService.queryUserByAdministrator(userid);//根据登录用户id查询当前用户有哪些权限
        if (i.getIscircle().equals(JurisdictionConstants.JURISDICTION_TYPE.groupOwner.getCode())) {//圈主
            if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.add.getCode()) && kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//圈主可以在自己圈子中添加评论
                map.put("resault", 1);
                return map;
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.add.getCode()) && kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//添加评论
                map.put("resault", 2);
                return map;
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.delete.getCode()) && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())))) {//圈主可以删除该圈子的帖子和评论
                Map ma = new HashedMap();
                if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//帖子
                    ma.put("userid", userid);
                    //根据操作类型id查询此操作是否属于该用户范畴
                    List<Integer> list = bossUserService.queryPostByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//帖子评论
                    ma.put("userid", userid);
                    List<Integer> list = bossUserService.queryCommentByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                }
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.update.getCode()) && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode()))) {//圈主可以编辑改圈子中的本人帖子
                Map ma = new HashedMap();
                if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//帖子
                    ma.put("userid", userid);
                    //根据操作类型id查询此操作是否属于该用户范畴
                    List<Integer> list = bossUserService.queryPostByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//帖子评论
                    ma.put("userid", userid);
                    List<Integer> list = bossUserService.queryCommentByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else {
                    map.put("resault", -1);
                    return map;
                }
            } else {
                map.put("resault", -1);
                return map;
            }
        } else if (i.getCirclemanagement().equals(JurisdictionConstants.JURISDICTION_TYPE.groupManage.getCode())) {//圈子管理员
            if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.add.getCode())) {//圈主可以在自己圈子中添加帖子
                if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//添加帖子
                    map.put("resault", 1);
                    return map;
                } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//添加评论
                    map.put("resault", 2);
                    return map;
                } else {
                    map.put("resault", -1);
                    return map;
                }
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.add.getCode()) && kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//添加评论
                map.put("resault", 2);
                return map;
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.delete.getCode()) && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())))) {//圈主可以删除该圈子的帖子和评论
                Map ma = new HashedMap();
                if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//帖子
                    ma.put("userid", userid);
                    //根据操作类型id查询此操作是否属于该用户范畴
                    List<Integer> list = bossUserService.queryPostByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//帖子评论
                    ma.put("userid", userid);
                    List<Integer> list = bossUserService.queryCommentByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else {
                    map.put("resault", -1);
                    return map;
                }
            } else if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.update.getCode()) && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode()))) {//圈主可以编辑改圈子中的本人帖子
                Map ma = new HashedMap();
                if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//帖子
                    ma.put("userid", userid);
                    //根据操作类型id查询此操作是否属于该用户范畴
                    List<Integer> list = bossUserService.queryPostByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//帖子评论
                    ma.put("userid", userid);
                    List<Integer> list = bossUserService.queryCommentByUserid(ma);
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t) == kindid) {
                            map.put("resault", 1);
                            return map;
                        } else if (list.size() == t) {//代表最后一条也不匹配
                            map.put("resault", -1);
                            return map;
                        }
                    }
                } else {
                    map.put("resault", -1);
                    return map;
                }
            } else {
                map.put("resault", -1);
                return map;
            }
        } else if (i.getContributing().equals(JurisdictionConstants.JURISDICTION_TYPE.speciallyInvite.getCode())) {//特邀嘉宾
            //只能设置帖子为精选池和评论帖子
            if (operation.equals(JurisdictionConstants.JURISDICTION_TYPE.add.getCode()) && operation.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode())) {//添加评论
                map.put("resault", 2);
                return map;
            } else if (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.update.getCode()) && operation.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode())) {//操作帖子加入精选池
                map.put("resault", 1);
                return map;
            } else {
                map.put("resault", -1);
                return map;
            }
        } else if (i.getIssuper().equals(1) || i.getCommon().equals(1)) {//操作权限为最高权限
            map.put("resault", 1);
            return map;
        } else {
            map.put("resault", -1);
            return map;
        }
        return map;
    }


    /**
     * 登录用户查询校验
     *
     * @param userid
     * @param operation
     * @param kind
     * @param kindid
     * @return
     */
    public Map verifyUserByQueryMethod(Integer userid, Integer operation, Integer kind, Integer kindid) {
        Map map = new HashMap();
        BossUser i = bossUserService.queryUserByAdministrator(userid);//根据登录用户id查询当前用户有哪些权限
        if (i.getIssuper().equals(1) || i.getCommon().equals(1)) {//操作权限为最高权限
            map.put("resault", 1);
            return map;
        } else if (i.getIscircle().equals(JurisdictionConstants.JURISDICTION_TYPE.groupOwner.getCode())
                && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || kind.equals(JurisdictionConstants.JURISDICTION_TYPE.circle.getCode()))) {//圈主可以查看
            map.put("resault", userid);
            return map;
        } else if (i.getCirclemanagement().equals(JurisdictionConstants.JURISDICTION_TYPE.groupManage.getCode())
                && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || kind.equals(JurisdictionConstants.JURISDICTION_TYPE.circle.getCode()))) {//圈子管理员可以查看
            map.put("resault", userid);
            return map;
        } else if (i.getContributing().equals(JurisdictionConstants.JURISDICTION_TYPE.speciallyInvite.getCode())
                && (kind.equals(JurisdictionConstants.JURISDICTION_TYPE.post.getCode()) || kind.equals(JurisdictionConstants.JURISDICTION_TYPE.comment.getCode()))) {//特邀嘉宾可以查看
            map.put("resault", userid);
            return map;
        } else {
            map.put("resault", -1);
            return map;
        }
    }
}
