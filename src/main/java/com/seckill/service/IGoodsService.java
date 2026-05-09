package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckill.entity.Goods;
import com.seckill.entity.vo.GoodsVO;

import java.util.List;

/**
 * 商品服务。
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 查询商品列表视图对象。
     */
    List<GoodsVO> listGoodsVO();

    /**
     * 根据商品 ID 查询商品详情视图对象。
     */
    GoodsVO getGoodsVOById(Long goodsId);

    /**
     * 扣减普通商品库存。
     */
    boolean reduceStock(Long goodsId);
}
