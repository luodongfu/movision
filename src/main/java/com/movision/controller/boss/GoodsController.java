package com.movision.controller.boss;

import com.movision.common.Response;
import com.movision.facade.boss.GoodsListFacade;
import com.movision.mybatis.goods.entity.GoodsVo;
import com.movision.utils.pagination.model.Paging;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author zhanglei
 * @Date 2017/2/23 9:51
 */
@RestController
@RequestMapping("/boss/goods")
public class GoodsController {

    @Autowired
    GoodsListFacade goodsFacade = new GoodsListFacade();


    /**
     * 商品管理*--商品列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "商品列表（分页）", notes = "商品列表（分页）", response = Response.class)
    @RequestMapping(value = "query_goods_list", method = RequestMethod.POST)
    public Response queryGoodsList(@RequestParam(required = false, defaultValue = "1") String pageNo,
                                   @RequestParam(required = false, defaultValue = "10") String pageSize
    ) {
        Response response = new Response();
        Paging<GoodsVo> pager = new Paging<>(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        List<GoodsVo> list = goodsFacade.queryGoodsList(pager);
        pager.result(list);
        response.setData(pager);
        return response;
    }

    /**
     * 商品管理-删除商品
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除商品", notes = "删除商品", response = Response.class)
    @RequestMapping(value = "delete_goods", method = RequestMethod.POST)
    public Response deleteGoods(@ApiParam(value = "商品编号") @RequestParam(required = false) Integer id) {
        Response response = new Response();
        int result = goodsFacade.deleteGoods(id);
        if (response.getCode() == 200) {
            response.setMessage("删除成功");
        }
        response.setData(result);
        return response;
    }

    /**
     * 商品管理---条件查询
     *
     * @param name
     * @param producttags
     * @param brand
     * @param protype
     * @param isdel
     * @param allstatue
     * @param minorigprice
     * @param maxorigprice
     * @param minprice
     * @param maxprice
     * @param minstock
     * @param maxstock
     * @param minsales
     * @param maxsales
     * @param mincollect
     * @param maxcollect
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "条件查询", response = Response.class)
    @RequestMapping(value = "query_goods_condition", method = RequestMethod.POST)
    public Response queryGoodsCondtion(@ApiParam(value = "商品名称") @RequestParam(required = false) String name,
                                       @ApiParam(value = "商品标签") @RequestParam(required = false) String producttags,
                                       @ApiParam(value = "商品品牌") @RequestParam(required = false) String brand,
                                       @ApiParam(value = "商品分类") @RequestParam(required = false) String protype,
                                       @ApiParam(value = "商品状态") @RequestParam(required = false) String isdel,
                                       @ApiParam(value = "商品类型") @RequestParam(required = false) String allstatue,
                                       @ApiParam(value = "最小原价") @RequestParam(required = false) String minorigprice,
                                       @ApiParam(value = "最大原价") @RequestParam(required = false) String maxorigprice,
                                       @ApiParam(value = "最小折扣价") @RequestParam(required = false) String minprice,
                                       @ApiParam(value = "最大折扣价") @RequestParam(required = false) String maxprice,
                                       @ApiParam(value = "最小库存") @RequestParam(required = false) String minstock,
                                       @ApiParam(value = "最大库存") @RequestParam(required = false) String maxstock,
                                       @ApiParam(value = "最小销售量") @RequestParam(required = false) String minsales,
                                       @ApiParam(value = "最大销售量") @RequestParam(required = false) String maxsales,
                                       @ApiParam(value = "最小收藏") @RequestParam(required = false) String mincollect,
                                       @ApiParam(value = "最大收藏") @RequestParam(required = false) String maxcollect,
                                       @RequestParam(required = false) String pageNo,
                                       @RequestParam(required = false) String pageSize
    ) {
        Response response = new Response();
        Paging<GoodsVo> pager = new Paging<GoodsVo>(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        List<GoodsVo> list = goodsFacade.queryGoodsCondition(name, producttags, brand, protype, isdel, allstatue, minorigprice, maxorigprice, minprice, maxprice, minstock, maxstock, minsales, maxsales, mincollect, maxcollect, pager);
        if (response.getCode() == 200) {
            response.setMessage("查询成功");
        }
        pager.result(list);
        response.setData(pager);
        return response;

    }

    /**
     * 商品管理--上架
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "上架", notes = "上架", response = Response.class)
    @RequestMapping(value = "grounding_goods", method = RequestMethod.POST)
    public Response queryByGoods(@ApiParam(value = "商品id") @RequestParam(required = false) Integer id) {
        Response response = new Response();
        int result = goodsFacade.queryByGoods(id);
        if (response.getCode() == 200) {
            response.setMessage("上架成功");
        }
        response.setData(result);
        return response;
    }

    /**
     * 商品管理--推荐到热门
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "推荐到热门", notes = "推荐到热门", response = Response.class)
    @RequestMapping(value = "recommend_ishot", method = RequestMethod.POST)
    public Response recommendishot(@ApiParam(value = "商品id") @RequestParam(required = false) Integer id) {
        Response response = new Response();
        int result = goodsFacade.queryHot(id);
        if (response.getCode() == 200) {
            response.setMessage("上架成功");
        }
        response.setData(result);
        return response;
    }

    /**
     * 商品管理--推荐到精选
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "推荐到精选", notes = "推荐到精选", response = Response.class)
    @RequestMapping(value = "recommend_isessence", method = RequestMethod.POST)
    public Response recommendisessence(@ApiParam(value = "商品id") @RequestParam(required = false) Integer id) {
        Response response = new Response();
        int result = goodsFacade.queryisessence(id);
        if (response.getCode() == 200) {
            response.setMessage("上架成功");
        }
        response.setData(result);
        return response;
    }

    /**
     * 商品管理*--修改推荐日期
     *
     * @param id
     * @param recommenddate
     * @return
     */
    @ApiOperation(value = "修改推荐日期", notes = "修改推荐日期", response = Response.class)
    @RequestMapping(value = "update_recommenddate", method = RequestMethod.POST)
    public Response updateDate(@ApiParam(value = "商品id") @RequestParam(required = false) String id,
                               @ApiParam(value = "推荐日期") @RequestParam(required = false) String recommenddate) {
        Response response = new Response();
        Map<String, Integer> map = goodsFacade.updateDate(id, recommenddate);
        if (response.getCode() == 200) {
            response.setMessage("修改成功");
        }
        response.setData(map);
        return response;
    }
}
