package com.zhuhuibao.business.common;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.zhuhuibao.common.Response;
import com.zhuhuibao.common.constant.MsgCodeConstant;
import com.zhuhuibao.common.util.ShiroUtil;
import com.zhuhuibao.exception.AuthException;
import com.zhuhuibao.mybatis.common.entity.SysResearch;
import com.zhuhuibao.mybatis.common.service.SysResearchService;
import com.zhuhuibao.mybatis.memCenter.entity.Message;
import com.zhuhuibao.mybatis.memCenter.service.MemberService;
import com.zhuhuibao.utils.MsgPropertiesUtils;
import com.zhuhuibao.utils.oss.ZhbOssClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传
 */
@RestController
/*@RequestMapping("/rest/common")*/
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    MemberService memberService;

    @Autowired
    ZhbOssClient zhbOssClient;

    @Autowired
    SysResearchService sysResearchService;

    @ApiOperation(value = "上传图片，返回url", notes = "上传图片，返回url", response = Response.class)
    @RequestMapping(value = "/rest/common/upload_img", method = RequestMethod.POST)
    public Response uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        Response result = new Response();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession(false);
        if (session != null) {
            String url = zhbOssClient.uploadObject(file,"img",null);
            result.setData(url);
            return result;
        }else {
            log.error("上传图片失败");
            throw new AuthException(MsgCodeConstant.un_login, MsgPropertiesUtils.getValue(String.valueOf(MsgCodeConstant.un_login)));
        }

    }

    @ApiOperation(value = "上传图片，返回url(免登陆)", notes = "上传图片，返回url(免登陆)", response = Response.class)
    @RequestMapping(value = "/rest/common/upload_img_without_login", method = RequestMethod.POST)
    public Response upload_img_without_login(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        Response result = new Response();
            String url = zhbOssClient.uploadObject(file,"img",null);
            result.setData(url);
            return result;
    }

    /**
     *留言
     * @return
     * @throws IOException
     */
    @ApiOperation(value="留言",notes="留言",response = Response.class)
    @RequestMapping(value = "/rest/common/add_message", method = RequestMethod.POST)
    public Response message(@ModelAttribute Message message) {
        Response response = new Response();
        Long createid = ShiroUtil.getCreateID();
        if(createid!=null){
            message.setCreateid(String.valueOf(createid));
            memberService.saveMessage(message);
        }else {
            throw new AuthException(MsgCodeConstant.un_login,MsgPropertiesUtils.getValue(String.valueOf(MsgCodeConstant.un_login)));
        }
        return response;
    }

    @ApiOperation(value="提升计划",notes="提升计划",response = Response.class)
    @RequestMapping(value = "/rest/common/add_research", method = RequestMethod.POST)
    public Response addResearch(@ApiParam @ModelAttribute  SysResearch sysResearch){

        sysResearchService.insert(sysResearch);

        return new Response();
    }
}
