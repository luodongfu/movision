package com.zhuhuibao.business.expo.mc;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.zhuhuibao.common.Response;
import com.zhuhuibao.common.constant.MsgCodeConstant;
import com.zhuhuibao.common.util.ShiroUtil;
import com.zhuhuibao.exception.AuthException;
import com.zhuhuibao.mybatis.memCenter.entity.Exhibition;
import com.zhuhuibao.mybatis.memCenter.service.ExhibitionService;
import com.zhuhuibao.shiro.realm.ShiroRealm;
import com.zhuhuibao.utils.MsgPropertiesUtils;
import com.zhuhuibao.utils.pagination.model.Paging;
import com.zhuhuibao.utils.pagination.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会展接口类
 * Created by cxx on 2016/5/11 0011.
 */
@RestController
@RequestMapping("/rest/expo/mc/")
public class ExpoController {
    private static final Logger log = LoggerFactory.getLogger(ExpoController.class);

    @Autowired
    private ExhibitionService exhibitionService;

    /**
     * 我的活动
     */
    @ApiOperation(value="我的活动",notes="我的活动",response = Response.class)
    @RequestMapping(value = "sel_myExpoList", method = RequestMethod.GET)
    public Response findAllMyExhibition(@ApiParam(value = "标题")@RequestParam(required = false)String title,
                                        @ApiParam(value = "审核状态")@RequestParam(required = false)String status,
                                        @RequestParam(required = false)String pageNo, @RequestParam(required = false)String pageSize) {
        Response response = new Response();
        //设定默认分页pageSize
        if (StringUtils.isEmpty(pageNo)) {
            pageNo = "1";
        }
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = "10";
        }
        Paging<Map<String,String>> pager = new Paging<Map<String,String>>(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        Map<String,Object> map = new HashMap<>();
        //查询传参
        map.put("title",title);
        map.put("status",status);
        map.put("type",3);
        Long createId = ShiroUtil.getCreateID();
        map.put("createId", createId);
        //查询
        List<Map<String,String>> exhibitionList = exhibitionService.findAllExhibition(pager,map);
        pager.result(exhibitionList);
        response.setData(pager);
        return response;
    }

    /**
     * 根據id查詢会展详情
     */
    @ApiOperation(value="根據id查詢会展详情",notes="根據id查詢会展详情",response = Response.class)
    @RequestMapping(value = "sel_expo", method = RequestMethod.GET)
    public Response queryExhibitionInfoById(@RequestParam String id)  {
        Response response = new Response();
        Exhibition exhibition = exhibitionService.queryExhibitionInfoById(id);
        response.setData(exhibition);
        return response;
    }

    /**
     * 会展信息编辑更新
     */
    @ApiOperation(value="会展信息编辑更新",notes="会展信息编辑更新",response = Response.class)
    @RequestMapping(value = "upd_expo", method = RequestMethod.POST)
    public Response updateExhibitionInfoById(@ModelAttribute()Exhibition exhibition)  {
        Response response = new Response();
        exhibitionService.updateExhibitionInfoById(exhibition);
        return response;
    }

    /**
     * 会展信息删除
     */
    @ApiOperation(value="会展信息删除",notes="会展信息删除",response = Response.class)
    @RequestMapping(value = "del_expo", method = RequestMethod.POST)
    public Response deleteExhibition(@ApiParam(value = "ids,逗号隔开") @RequestParam String ids)  {
        Response response = new Response();
        String[] idList = ids.split(",");
        for (String id : idList) {
            String is_deleted = "1";
            Exhibition exhibition = new Exhibition();
            exhibition.setIs_deleted(is_deleted);
            exhibition.setId(id);
            exhibitionService.updateExhibitionInfoById(exhibition);
        }
        return response;
    }
}
