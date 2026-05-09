-- ============================================================
-- 秒杀系统 测试数据
-- ============================================================

USE `seckill`;

-- 用户数据 (密码为 123456 的二次MD5)
INSERT INTO `t_user` (`id`, `nickname`, `password`, `salt`) VALUES
(1, 'user1', 'b7797cce01b4b131b433b6acf4add449', '1a2b'),
(2, 'user2', 'b7797cce01b4b131b433b6acf4add449', '1a2b'),
(3, 'user3', 'b7797cce01b4b131b433b6acf4add449', '1a2b');

-- 商品数据
INSERT INTO `t_goods` (`id`, `goods_name`, `goods_title`, `goods_img`, `goods_price`, `goods_stock`) VALUES
(1, 'iPhone 16 Pro', 'Apple iPhone 16 Pro 256GB 原色钛金属', '/img/iphone16.jpg', 8999.00, 1000),
(2, 'MacBook Pro', 'Apple MacBook Pro 14英寸 M4 Pro芯片', '/img/macbook.jpg', 14999.00, 500),
(3, 'AirPods Pro', 'Apple AirPods Pro 2 USB-C接口', '/img/airpods.jpg', 1899.00, 2000),
(4, 'iPad Pro', 'Apple iPad Pro 13英寸 M4芯片', '/img/ipad.jpg', 8999.00, 800),
(5, '小米15 Ultra', '小米15 Ultra 骁龙8至尊版 16GB+512GB', '/img/mi15.jpg', 5999.00, 1500);

-- 秒杀商品数据 (注意: 请根据实际运行时间调整开始和结束时间)
INSERT INTO `t_seckill_goods` (`goods_id`, `seckill_price`, `stock_count`, `start_date`, `end_date`) VALUES
(1, 6999.00, 100, '2026-04-23 10:00:00', '2026-04-23 12:00:00'),
(2, 9999.00, 50,  '2026-04-23 10:00:00', '2026-04-23 12:00:00'),
(3, 999.00,  200, '2026-04-23 10:00:00', '2026-04-23 12:00:00'),
(4, 5999.00, 80,  '2026-04-23 14:00:00', '2026-04-23 16:00:00'),
(5, 3999.00, 150, '2026-04-23 14:00:00', '2026-04-23 16:00:00');
