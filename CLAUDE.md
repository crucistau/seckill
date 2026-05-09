# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

秒杀（Flash Sale）系统，基于 Spring Boot 2.7.18 + Java 11 的高并发电商秒杀后端。使用 Redis 做缓存和分布式锁，RabbitMQ 做异步订单处理。

## Build & Run Commands

```bash
# 编译
mvn clean compile

# 运行测试
mvn test

# 打包
mvn clean package

# 启动（需要先启动 MySQL、Redis、RabbitMQ）
mvn spring-boot:run
```

**外部依赖：**
- MySQL 5.7+，数据库 `seckill`，端口 3306
- Redis，端口 6379
- RabbitMQ，端口 5672
- 初始化脚本：`src/main/resources/db/schema.sql` + `data.sql`

## Architecture

```
Controller → Service(interface + impl) → DAO(MyBatis-Plus BaseMapper)
```

**分层约定：**
- `entity/` — 数据库实体，对应表结构
- `dto/` — 接口入参（LoginDTO, SeckillDTO）
- `vo/` — 接口出参和视图对象（GoodsVO, OrderDetailVO, Result\<T\>）
- `dao/` — MyBatis-Plus Mapper 接口，复杂查询在 `resources/mapper/*.xml`
- `service/impl/` — 业务实现，接口以 `I` 前缀（如 `ISeckillService`）

**统一响应：** 所有 API 返回 `Result<T>`（code/message/data），静态工厂方法构建。

**异常处理：** `BusinessException` 携带错误码，`GlobalExceptionHandler`（@RestControllerAdvice）统一捕获。

## Database

5 张表，全部使用 `t_` 前缀：

| 表 | 用途 |
|---|---|
| `t_user` | 用户（MD5+salt 密码） |
| `t_goods` | 商品 |
| `t_seckill_goods` | 秒杀活动商品（时间段、秒杀价、独立库存） |
| `t_order` | 订单（状态机：0新建→1已付→2已发→3已收→4退款→5取消） |
| `t_seckill_order` | 秒杀订单，`(user_id, goods_id)` 唯一约束防重复购买 |

## Concurrency Strategy

- **Redis 预减库存**：秒杀前将库存加载到 Redis，原子递减判断余量
- **RabbitMQ 异步下单**：秒杀请求入队，消费者异步创建订单
- **分布式锁**：防止同一用户并发重复秒杀
- **结果轮询**：前端轮询 `GET /seckill/result` 获取处理结果

## Current Status

项目为骨架状态，核心 Service 方法为 TODO 占位（throw UnsupportedOperationException）。需要实现：秒杀核心流程、库存扣减、订单创建、结果查询等逻辑。