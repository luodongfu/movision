package com.movision.controller.app;

import com.movision.common.Response;
import com.movision.facade.comment.FacadeComments;
import com.movision.mybatis.comment.entity.CommentVo;
import com.movision.utils.pagination.model.Paging;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author zhurui
 * @Date 2017/1/22 14:30
 */
@RestController
@RequestMapping("/app/comment/")
public class CommentController {

    @Autowired
    public FacadeComments facadeComments;

    @ApiOperation(value = "评论列表", notes = "返回帖子评论列表", response = Response.class)
    @RequestMapping(value = "commentLsit", method = RequestMethod.POST)
    public Response queryCommentLsit(@ApiParam(value = "帖子id") @RequestParam String postid,
                                     @ApiParam(value = "用户id") @RequestParam(required = false) String userid,
                                     @ApiParam(value = "排序(0表示按发表时间正序  空默认按发表时间倒叙)") @RequestParam(required = false) String type,
                                     @ApiParam(value = "第几页") @RequestParam(required = false, defaultValue = "1") String pageNo,
                                     @ApiParam(value = "每页多少条") @RequestParam(required = false, defaultValue = "10") String pageSize) {
        Response response = new Response();

        Paging<CommentVo> pager = new Paging<>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        List<CommentVo> commentVo = facadeComments.queryCommentsByLsit(pager, postid, type, userid);
        pager.result(commentVo);

        if (response.getCode() == 200) {
            response.setMessage("查询成功");
        }
        response.setData(pager);
        return response;
    }

    @ApiOperation(value = "评论点赞接口", notes = "返回评论的点赞次数", response = Response.class)
    @RequestMapping(value = "/CommentZanSum", method = RequestMethod.POST)
    public Response updateCommentZanSum(@ApiParam(value = "评论id") @RequestParam String commentid,
                                        @ApiParam(value = "用户id") @RequestParam String userid) {
        Response response = new Response();
        int zansum = facadeComments.doZanWithComment(commentid, userid);
        if (response.getCode() == 200) {
            response.setMessage("操作成功");
        }
        response.setData(zansum);
        return response;
    }

    /**
     * 帖子发表评论接口
     *
     * @param userid
     * @param content
     * @param fuid
     * @param postid
     * @return
     */
    @ApiOperation(value = "发表评论接口", notes = "发表评论接口", response = Response.class)
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Response insertComment(@ApiParam(value = "用户id") @RequestParam String userid,
                                  @ApiParam(value = "评论内容") @RequestParam String content,
                                  @ApiParam(value = "父评论id") @RequestParam(required = false) String fuid,
                                  @ApiParam(value = "帖子id") @RequestParam String postid) {
        Response response = new Response();
        int i = facadeComments.insertComment(userid, content, fuid, postid);
        if (i == 1) {
            if (response.getCode() == 200) {
                response.setMessage("操作成功");
            }
        } else if (i == 2) {
            response.setCode(500);
            response.setMessage("评论字数超过最大限制");
        } else {
            response.setCode(500);
            response.setMessage("评论发表失败");
        }

        return response;
    }

    /**
     * 评论举报
     *
     * @param userid
     * @param commentid
     * @param comment
     * @return
     */
    @ApiOperation(value = "评论举报", notes = "用于评论举报", response = Response.class)
    @RequestMapping(value = "updateCommentByinform", method = RequestMethod.POST)
    public Response updateCommentByinform(@ApiParam(value = "举报人") @RequestParam String userid,
                                          @ApiParam(value = "举报评论id") @RequestParam String commentid,
                                          @ApiParam(value = "举报原因") @RequestParam String comment) {
        Response response = new Response();
        int resault = facadeComments.updateCommentByinform(userid, comment, commentid);
        response.setMessage("操作成功");
        response.setData(resault);
        return response;
    }
}
