package com.seckill.controller;

import com.seckill.entity.vo.GoodsVO;
import com.seckill.entity.vo.Result;
import com.seckill.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品查询接口。
 * 对外提供秒杀商品列表和商品详情查询能力。
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询商品列表。
     */
    @GetMapping("/list")
    public Result<List<GoodsVO>> list() {
        return Result.success(goodsService.listGoodsVO());
    }

    /**
     * 根据商品 ID 查询商品详情。
     */
    @GetMapping("/detail/{id}")
    public Result<GoodsVO> detail(@PathVariable Long id) {
        return Result.success(goodsService.getGoodsVOById(id));
    }
}
