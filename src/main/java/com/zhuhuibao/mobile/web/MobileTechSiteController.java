package com.zhuhuibao.mobile.web;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.zhuhuibao.common.Response;
import com.zhuhuibao.common.constant.MsgCodeConstant;
import com.zhuhuibao.common.constant.TechConstant;
import com.zhuhuibao.common.util.ShiroUtil;
import com.zhuhuibao.exception.AuthException;
import com.zhuhuibao.mybatis.tech.entity.TechExpertCourse;
import com.zhuhuibao.service.MobileTechService;
import com.zhuhuibao.service.payment.PaymentService;
import com.zhuhuibao.utils.MsgPropertiesUtils;
import com.zhuhuibao.utils.pagination.model.Paging;
import com.zhuhuibao.utils.pagination.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * User: zhuangyuhao
 * Date: 2016/11/23
 * Time: 14:09
 */
@RestController
@RequestMapping("/rest/m/tech/site/")
public class MobileTechSiteController {

    @Autowired
    MobileTechService mTechSV;

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "sel_tech_cooperation", method = RequestMethod.GET)
    @ApiOperation(value = "查看技术成果，技术需求信息", notes = "查看技术成果，技术需求信息", response = Response.class)
    public Response queryByChannelInfo(@ApiParam(value = "1:技术成果，2：技术需求") @RequestParam Integer type) {
        Response response = new Response();
        response.setData(mTechSV.getTechCoop(type, 6));
        return response;
    }

    @RequestMapping(value = "sel_all_news", method = RequestMethod.GET)
    @ApiOperation(value = "查询新技术列表", notes = "查询新技术列表", response = Response.class)
    public Response findAllTechNewsList(@ApiParam(value = "页码") @RequestParam(required = false, defaultValue = "1") String pageNo,
                                        @ApiParam(value = "每页显示的数目") @RequestParam(required = false, defaultValue = "10") String pageSize) throws IOException {
        Response response = new Response();
        response.setData(mTechSV.getNewTechList(pageNo,pageSize));
        return response;
    }

    @RequestMapping(value = "sel_new_tech_detail", method = RequestMethod.GET)
    @ApiOperation(value = "查询新技术详情", notes = "查询新技术详情", response = Response.class)
    public Response queryTechNewsDetail(@ApiParam(value = "新闻ID") @RequestParam String newsId) {
        Response response = new Response();
        response.setData(mTechSV.getNewTechDetail(newsId));
        return response;
    }

    @ApiOperation(value = "技术资料列表", notes = "技术资料列表", response = Response.class)
    @RequestMapping(value = "list_tech_data", method = RequestMethod.GET)
    public Response listTechDataPage(@ApiParam(value = "类别ID:1:解决方案 2:技术资料 3:培训资料") @RequestParam(required = false) String fcateId,
                                     @ApiParam(value = "子类别ID") @RequestParam(required = false) String scateId,
                                     @ApiParam(value = "页码") @RequestParam(required = false, defaultValue = "1") String pageNo,
                                     @ApiParam(value = "每页显示的数目") @RequestParam(required = false, defaultValue = "10") String pageSize) {

        return new Response(mTechSV.getTechDataList(fcateId, scateId, pageNo, pageSize));
    }

    @RequestMapping(value = "sel_tech_data_detail", method = RequestMethod.GET)
    @ApiOperation(value = "查询技术资料详情", notes = "查询技术资料详情", response = Response.class)
    public Response previewTechDataDetail(@ApiParam(value = "技术资料ID") @RequestParam() String techDataId) {
        Response response = new Response();
        response.setData(mTechSV.getTechDataDetail(techDataId));
        return response;
    }

    @ApiOperation(value = "查询技术合作详情", notes = "查询技术合作详情", response = Response.class)
    @RequestMapping(value = "sel_tech_coop_detail", method = RequestMethod.GET)
    public Response selTechCoopDetail(@ApiParam(value = "商品ID") @RequestParam String GoodsID,
                                  @ApiParam(value = "商品类型同筑慧币") @RequestParam String type) throws Exception {
        return paymentService.viewGoodsRecord(Long.parseLong(GoodsID),type);
    }

    @RequestMapping(value = "sel_tech_course_list", method = RequestMethod.GET)
    @ApiOperation(value = "查询最新发布的课程(默认10条)", notes = "查询最新发布的课程(默认10条)", response = Response.class)
    public Response findLatestPublishCourse(@ApiParam(value = "地区（省）") @RequestParam(required = false) String province,
                                            @ApiParam(value = "页码") @RequestParam(required = false, defaultValue = "1") String pageNo,
                                            @ApiParam(value = "每页显示的数目") @RequestParam(required = false, defaultValue = "10") String pageSize) {
        Response response = new Response();
        response.setData(mTechSV.getTechCoursePageList(pageNo, pageSize, province));
        return response;
    }

    @RequestMapping(value = "sel_tech_course_detail", method = RequestMethod.GET)
    @ApiOperation(value = "查看培训课程详情", notes = "查看培训课程详情", response = Response.class)
    public Response previewPublishCourseDetail(@ApiParam(value = "培训课程ID") @RequestParam Long courseId) {
        Response response = new Response();
        response.setData(mTechSV.getTechCourseDetail(courseId));
        return response;
    }

    @RequestMapping(value="add_course", method = RequestMethod.POST)
    @ApiOperation(value="申请技术培训课程",notes = "申请技术培训课程",response = Response.class)
    public Response insertTechTrainCourse(@ApiParam(value = "申请技术培训课程")  @ModelAttribute(value="techCourse")TechExpertCourse techCourse)
    {
        Response response = new Response();
        mTechSV.addCourse(techCourse);
        return response;
    }

    @RequestMapping(value="getTechIndexInfo", method = RequestMethod.GET)
    @ApiOperation(value="获取手机端技术&培训-首页-信息",notes = "获取手机端技术&培训-首页-信息",response = Response.class)
    public Response insertTechTrainCourse()
    {
        Response response = new Response();
        response.setData(mTechSV.getTechIndexInfo());
        return response;
    }

}
