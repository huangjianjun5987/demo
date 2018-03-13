package com.yatang.sc.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.distribute.locks.zk.ReentrantZkDistributeLock;
import com.busi.distribute.locks.zk.ZkDistributeLockFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.dao.ImAdjustmentDao;
import com.yatang.sc.inventory.dao.ImAdjustmentItemDao;
import com.yatang.sc.inventory.dao.ItemLocSohDao;
import com.yatang.sc.inventory.dao.TranDataDao;
import com.yatang.sc.inventory.domain.ImAdjustmentItemPo;
import com.yatang.sc.inventory.domain.ImAdjustmentPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryListPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryParamPo;
import com.yatang.sc.inventory.domain.ImAdjustmentReceiptPo;
import com.yatang.sc.inventory.domain.ItemInventoryQueryParamPo;
import com.yatang.sc.inventory.domain.ItemLocSoh;
import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.service.ImAdjustmentService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @描述: 库存调整service实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午8:59
 * @版本: v1.0
 */
@Service
@Transactional
public class ImAdjustmentServiceImpl implements ImAdjustmentService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImAdjustmentItemDao imAdjustmentItemDao;

    @Autowired
    private ImAdjustmentDao imAdjustmentDao;


    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    /**
     * 根据传入参数分页查询库存调整列表
     *
     * @param convert1
     * @return
     */
    @Override
    public PageInfo<ImAdjustmentQueryListPo> queryListImAdjustment(ImAdjustmentQueryParamPo convert1) {
        PageHelper.startPage(convert1.getPageNum(), convert1.getPageSize());
        List<ImAdjustmentQueryListPo> imAdjustmentQueryListPoList = imAdjustmentDao.queryListImAdjustment(convert1);
        PageInfo<ImAdjustmentQueryListPo> pageInfo = new PageInfo<ImAdjustmentQueryListPo>(imAdjustmentQueryListPoList);
        return pageInfo;
    }

    /**
     * 根据传入参数id查询库存调整详情
     *
     * @param id
     * @return
     */
    @Override
    public ImAdjustmentQueryListPo getImAdjustment(Long id) {
        ImAdjustmentQueryListPo imAdjustmentQueryListPo = imAdjustmentDao.getImAdjustmentById(id);
        return imAdjustmentQueryListPo;
    }


    @Autowired
    private ItemLocSohDao itemLocSohDao;//库存表


    @Autowired
    private TranDataDao tranDataDao;//记录交易事物

    @Override
    public boolean addAdjustmentReceipt(ImAdjustmentReceiptPo adjustmentReceiptPo) {
        log.info("service---addAdjustmentReceipt--新增库存调整单>>adjustmentReceiptPo:{}", JSON.toJSONString(adjustmentReceiptPo));
      /*  //1.库存调整单中商品校验
        String errorMessage = validAdjustmentReceipt(adjustmentReceiptPo);
        if (StringUtils.isNotBlank(errorMessage)) {
            log.error("service---addAdjustmentReceipt--仓库同步库存调整单校验失败>>errorMessage:{}", errorMessage);
            throw new RuntimeException("service---addAdjustmentReceipt--仓库同步库存调整单校验失败:" + errorMessage);
        }*/
        //生成库存调整单并计算其中的数据
        return generateAdjustmentReceipt(adjustmentReceiptPo);

    }

    /**
     * 库存调整单校验(是否存在)
     *
     * @param imAdjustmentReceiptPo
     * @return
     */
    private String validAdjustmentReceipt(ImAdjustmentReceiptPo imAdjustmentReceiptPo) {


        ImAdjustmentPo imAdjustment = imAdjustmentReceiptPo.getImAdjustment();//同步单主表
        String warehouseCode = imAdjustment.getWarehouseCode();//仓库编码
        if (StringUtils.isBlank(warehouseCode)) {
            log.error("仓库同步----->>仓库编号为空");
            throw new RuntimeException("库同步----->>仓库编号为空");
        }

        List<ImAdjustmentItemPo> imAdjustmentItems = imAdjustmentReceiptPo.getImAdjustmentItems();//需要调整的商品
        if (imAdjustmentItems.size() == 0) {
            log.error("仓库同步----->>库存调整单商品表为空");
            throw new RuntimeException("库同步----->>库存调整单商品表为空");
        }

        StringBuilder errorMessage = new StringBuilder();//错误信息
        //基本信息校验
        for (ImAdjustmentItemPo item : imAdjustmentItems) {//商品校验

            String productCode = item.getProductCode();// 需要做校验
            if (StringUtils.isBlank(productCode)) {
                log.error("仓库同步--》商品编号为空");
                errorMessage.append("仓库同步--》商品编号为空\br");
            } else {

                //从库存表查询是否有该上商品的库存
                ItemInventoryQueryParamPo itemInventoryQueryParamPo = new ItemInventoryQueryParamPo();
                itemInventoryQueryParamPo.setLogicWareHouseCode(warehouseCode);//仓库编码
                itemInventoryQueryParamPo.setProductCode(productCode);//商品编号
                List<ItemLocSoh> locSohs = itemLocSohDao.queryItemInventoryListByParam(itemInventoryQueryParamPo);
                log.info("商品productCode:{} 和仓库编号:{},从item_loc_soh表获取>>locSohs:{}", productCode, warehouseCode, JSON.toJSONString(locSohs));
                if (locSohs.size() == 1) {//库存正常
                    ItemLocSoh itemLocSoh = locSohs.get(0);
                    item.setProductId(itemLocSoh.getItemId());//设置商品id
                    item.setAvCost(new BigDecimal(itemLocSoh.getAvCost()));//移动平均成本
                    item.setStockOnHand(itemLocSoh.getStockOnHand());//获取库存


                } else if (locSohs.size() == 0) {//库存为空或不存在
                    log.error("商品productCode:{} 和仓库编号:{}从item_loc_soh获取库存信息为空", productCode, warehouseCode);
                    errorMessage.append("商品productCode:" + productCode + "和仓库编号为:" + warehouseCode + "从item_loc_soh获取库存信息为空+");
                }

            }

        }
        return errorMessage.toString();
    }

    /**
     * 生成库存调整单
     *
     * @param imAdjustmentReceiptPo
     * @return
     */
    public boolean generateAdjustmentReceipt(ImAdjustmentReceiptPo imAdjustmentReceiptPo) {

        log.info("service---generateAdjustmentReceipt--生成库存调整单>>adjustmentReceiptPo:{}", imAdjustmentReceiptPo);

        //计算库存调整单信息
        ImAdjustmentPo imAdjustment = imAdjustmentReceiptPo.getImAdjustment();//库存调整单主表
        List<ImAdjustmentItemPo> imAdjustmentItems = imAdjustmentReceiptPo.getImAdjustmentItems();//商品子项集合

        Long totalAdjustQuantity = 0L;//总库存调整数量
        // double totalAdjustmentCost = 0.0;
        for (ImAdjustmentItemPo adjustmentItem : imAdjustmentItems) {
            //库存调整数量
            Long quantity = adjustmentItem.getQuantity();//调整数量
            totalAdjustQuantity += quantity;//累加调整数量
//            //库存调整成本
//            double avCost = adjustmentItem.getAvCost().doubleValue();//移动平均成本
//            BigDecimal bigDecimalTotalCost = new BigDecimal(String.valueOf(avCost * adjustmentItem.getQuantity())).setScale(2,
//                    BigDecimal.ROUND_HALF_UP);// 四舍五入处理
//
//            adjustmentItem.setAdjustmentCost(bigDecimalTotalCost);//记录当前商品的调整成本
//            totalAdjustmentCost += bigDecimalTotalCost.doubleValue();//累加成本

        }
//        BigDecimal bigDecimalTotalAdjustmentCost = new BigDecimal(totalAdjustmentCost);
//        imAdjustment.setTotalAdjustmentCost(bigDecimalTotalAdjustmentCost);
        imAdjustment.setTotalQuantity(totalAdjustQuantity);

        //主表插入
        imAdjustment.setStatus(0);//设置为草稿状态
        imAdjustment.setCreateUserName("scm_admin");//设置默认创建用户
        boolean mainInsertSuccess = imAdjustmentDao.insertSelective(imAdjustment) >= 1;
        Long mainKey = imAdjustment.getId();//调整单主表id
        for (ImAdjustmentItemPo item : imAdjustmentItems) {
            item.setAdjustmentId(String.valueOf(mainKey));//设置调整单id
        }
        //批量插入Item
        boolean batchInsertSuccess = imAdjustmentItemDao.batchInsertAdjustmentItem(imAdjustmentReceiptPo.getImAdjustmentItems()) >= 1;
        return mainInsertSuccess && batchInsertSuccess;

    }

    /**
     * 调整库存中的商品
     *
     * @param receiptPo 库存调整单
     */
    public void adjustInventoryItem(ImAdjustmentReceiptPo receiptPo) {

        log.info("service--调整库存中的商品--adjustInventoryItem>>参数>>receiptPo:{}", JSON.toJSONString(receiptPo));
        ImAdjustmentPo imAdjustment = receiptPo.getImAdjustment();
        List<ImAdjustmentItemPo> imAdjustmentItems = receiptPo.getImAdjustmentItems();
        Integer type = imAdjustment.getType();//  类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）
        String warehouseCode = imAdjustment.getWarehouseCode();
        //增加操作:1,3,5
        //调减操作:0，2，4，6
        Integer operateType;//默认增加操作
        //判别库存调整类型
        switch (type) {
            case 1:
            case 3:
            case 5:
                operateType = 1;
                break;
            case 0:
            case 2:
            case 4:
            case 6:
                operateType = 2;
                break;
            default:
                log.error("service---adjustInventoryItem>>库存调整失败>>调整类型出错:{}", type);
                throw new RuntimeException("service---adjustInventoryItem>>库存调整失败>>调整类型出错" + type);
        }

        double totalAdjustmentCost = 0.0;
        for (ImAdjustmentItemPo imAdjustmentItem : imAdjustmentItems) {
            String productId = imAdjustmentItem.getProductId();//商品id
            Long quantity = imAdjustmentItem.getQuantity();//调整数量

            // 库存调整加锁
            String lockPath = "/inventory_" + warehouseCode + productId;//根据仓库编号和商品编号枷锁 //
            RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
            try {
                rLock.lock();
                //查询当前库存情况
                List<ItemLocSoh> itemLocSohs = itemLocSohDao.getItemLocSohByProductId(productId, warehouseCode);
                log.info("根据商品id:{}和仓库编号:{}查询商品记录返回结果={}", productId, warehouseCode, JSON.toJSONString(itemLocSohs));
                if (itemLocSohs.size() == 0) {
                    log.error("service---adjustInventoryItem>>库存调整失败>>根据商品id:{}和仓库编号:{}无该商品记录", productId, warehouseCode);
                    throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>根据商品id" + productId + "和仓库编号:" + warehouseCode + "无该商品记录");
                }

                ItemLocSoh itemLocSoh = itemLocSohs.get(0);
                imAdjustmentItem.setStockOnHand(itemLocSoh.getStockOnHand());//现有库存
                imAdjustmentItem.setAvCost(new BigDecimal(itemLocSoh.getAvCost()));//移动平均价
                //库存调整成本
                double avCost = itemLocSoh.getAvCost();//移动平均成本
                BigDecimal bigDecimalTotalCost = new BigDecimal(String.valueOf(avCost * quantity)).setScale(2,
                        BigDecimal.ROUND_HALF_UP);// 四舍五入处理
                imAdjustmentItem.setAdjustmentCost(bigDecimalTotalCost);//记录当前商品的调整成本
                totalAdjustmentCost += bigDecimalTotalCost.doubleValue();//累加成本
                //1.调整库存
                if (1 == operateType) {//增加操作
                    itemLocSohDao.addInventoryItemNum(productId, quantity, warehouseCode);
                } else {//减少操作
                    //调整
                    Long absQty = Math.abs(quantity);//取绝对值
                    Long stockOnHand = imAdjustmentItem.getStockOnHand();//现有库存
                    if (null == stockOnHand || 0 == stockOnHand) {
                        log.error("service---adjustInventoryItem>>库存调整失败>>库存为空不能进行库存减少操作");
                        throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>库存为为空不能进行库存减少操作");
                    }
                    int i = itemLocSohDao.decreaseInventoryItemNum(productId, absQty, warehouseCode);
                    if (1 != i) {//插入失败
                        log.error("service---adjustInventoryItem>>库存调整失败>>商品id:{},仓库编号:{}的预减库数量:{},因库存不足无法进行减库操作", productId, warehouseCode, absQty);
                        throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>商品id:" + productId + ",仓库编号:" + warehouseCode + "的预减库数量:" + absQty + ",因库存不足无法进行减库操作");
                    }
                }
            } finally {
                rLock.unlock();
            }
            //2.记录tran_data数据  需要记录对应的 商品类型
            TranData tranData = new TranData();
            tranData.setItem(productId);//商品编号
            tranData.setLocation(warehouseCode);//仓库编号
            tranData.setProductCode(imAdjustmentItem.getProductCode());//商品编码
            //商品分类
            tranData.setGroups(imAdjustmentItem.getGroups());//部类
            tranData.setDept(imAdjustmentItem.getDept());
            tranData.setClasss(imAdjustmentItem.getClasss());
            tranData.setSubclass(imAdjustmentItem.getSubclass());
            tranData.setTranCode("22");//库存调整 code=22
            tranData.setTranDate(new Date());
            //交易数量*业务单据中商品平均成本
            tranData.setUnits(quantity);//交易数量
            tranData.setTotalCost(imAdjustmentItem.getAdjustmentCost().doubleValue());//总成本
            tranDataDao.insert(tranData);
        }

        //更新操作
        //库存调整单的总的成本
        imAdjustment.setTotalAdjustmentCost(new BigDecimal(totalAdjustmentCost));
        //更新调整单状态
        imAdjustmentDao.updateAdjustmentReceipt(imAdjustment.getId(), 1, imAdjustment.getTotalAdjustmentCost());
        //批量更新调整单子项
        imAdjustmentItemDao.batchUpdateAdjustmentReceiptItem(imAdjustmentItems);


    }


    /**
     * 新的同步方案
     *
     * @param imAdjustmentReceiptPo
     */
    @Override
    public void addSynAdjustmentReceipt(ImAdjustmentReceiptPo imAdjustmentReceiptPo) {
        log.info("service---addSynAdjustmentReceipt--新增同步库存调整单>>adjustmentReceiptPo:{}", imAdjustmentReceiptPo);
        //生成库存调整单.库存调整单中商品校验

        //计算库存调整单信息
        ImAdjustmentPo imAdjustment = imAdjustmentReceiptPo.getImAdjustment();//库存调整单主表
        List<ImAdjustmentItemPo> imAdjustmentItems = imAdjustmentReceiptPo.getImAdjustmentItems();//商品子项集合


        //库存调整
        Integer type = imAdjustment.getType();//  类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）
        String warehouseCode = imAdjustment.getWarehouseCode();
        //增加操作:1,3,5
        //调减操作:0，2，4，6
        Integer operateType;//默认增加操作
        //判别库存调整类型
        switch (type) {
            case 1:
            case 3:
            case 5:
                operateType = 1;
                break;
            case 0:
            case 2:
            case 4:
            case 6:
                operateType = 2;
                break;
            default:
                log.error("service---adjustInventoryItem>>库存调整失败>>调整类型出错:{}", type);
                throw new RuntimeException("service---adjustInventoryItem>>库存调整失败>>调整类型出错" + type);
        }

        double totalAdjustmentCost = 0.0;
        Long totalAdjustQuantity = 0L;//总库存调整数量
        for (ImAdjustmentItemPo imAdjustmentItem : imAdjustmentItems) {
            String productId = imAdjustmentItem.getProductId();//商品id
            Long quantity = imAdjustmentItem.getQuantity();//调整数量
            totalAdjustQuantity += quantity;//累加调整数量

            // 库存调整加锁
            String lockPath = "/inventory_" + warehouseCode + productId;//根据仓库编号和商品编号枷锁 //
            RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
            try {
                rLock.lock();
                //查询当前库存情况
                List<ItemLocSoh> itemLocSohs = itemLocSohDao.getItemLocSohByProductId(productId, warehouseCode);
                log.info("根据商品id:{}和仓库编号:{}查询商品记录返回结果={}", productId, warehouseCode, JSON.toJSONString(itemLocSohs));
                if (itemLocSohs.size() == 0) {
                    log.error("service---adjustInventoryItem>>库存调整失败>>根据商品id:{}和仓库编号:{}无该商品记录", productId, warehouseCode);
                    throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>根据商品id" + productId + "和仓库编号:" + warehouseCode + "无该商品记录");
                }

                ItemLocSoh itemLocSoh = itemLocSohs.get(0);
                imAdjustmentItem.setStockOnHand(itemLocSoh.getStockOnHand() + quantity);//现有库存
                imAdjustmentItem.setAvCost(new BigDecimal(itemLocSoh.getAvCost()));//移动平均价


                //库存调整成本
                double avCost = itemLocSoh.getAvCost();//移动平均成本
                BigDecimal bigDecimalTotalCost = new BigDecimal(String.valueOf(avCost * quantity)).setScale(2,
                        BigDecimal.ROUND_HALF_UP);// 四舍五入处理
                imAdjustmentItem.setAdjustmentCost(bigDecimalTotalCost);//记录当前商品的调整成本
                totalAdjustmentCost += bigDecimalTotalCost.doubleValue();//累加成本

                //1.调整库存
                if (1 == operateType) {//增加操作
                    if (quantity < 0) {
                        log.error("service---adjustInventoryItem>>库存调整失败>>调增的数量:{}小于0", quantity);
                        throw new RuntimeException("service---adjustInventoryItem>>库存调整失败>>调增的数量:" + quantity + "小于0");
                    }
                    itemLocSohDao.addInventoryItemNum(productId, quantity, warehouseCode);
                } else {//减少操作
                    if (quantity > 0) {
                        log.error("service---adjustInventoryItem>>库存调整失败>>调减的数量:{}大于0", quantity);
                        throw new RuntimeException("service---adjustInventoryItem>>库存调整失败>>调增的数量:" + quantity + "大于0");
                    }
                    //调整
                    Long absQty = Math.abs(quantity);//取绝对值
                    Long stockOnHand = imAdjustmentItem.getStockOnHand();//现有库存
                    if (null == stockOnHand || 0 == stockOnHand) {
                        log.error("service---adjustInventoryItem>>库存调整失败>>库存为空不能进行库存减少操作");
                        throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>库存为为空不能进行库存减少操作");
                    }
                    int i = itemLocSohDao.decreaseInventoryItemNum(productId, absQty, warehouseCode);
                    if (1 != i) {//插入失败
                        log.error("service---adjustInventoryItem>>库存调整失败>>商品id:{},仓库编号:{}的预减库数量:{},因库存不足无法进行减库操作", productId, warehouseCode, absQty);
                        throw new RuntimeException("service--调整库存中的商品--adjustInventoryItem>>库存调整失败>>商品id:" + productId + ",仓库编号:" + warehouseCode + "的预减库数量:" + absQty + ",因库存不足无法进行减库操作");
                    }
                }
            } finally {
                rLock.unlock();
            }
            //2.记录tran_data数据  需要记录对应的 商品类型
            TranData tranData = new TranData();
            tranData.setItem(productId);//商品编号
            tranData.setLocation(warehouseCode);//仓库编号
            tranData.setProductCode(imAdjustmentItem.getProductCode());//商品编码
            //商品分类
            tranData.setGroups(imAdjustmentItem.getGroups());//部类
            tranData.setDept(imAdjustmentItem.getDept());
            tranData.setClasss(imAdjustmentItem.getClasss());
            tranData.setSubclass(imAdjustmentItem.getSubclass());
            tranData.setTranCode("22");//库存调整 code=22
            tranData.setTranDate(new Date());
            //交易数量*业务单据中商品平均成本
            tranData.setUnits(quantity);//交易数量
            tranData.setTotalCost(imAdjustmentItem.getAdjustmentCost().doubleValue());//总成本
            tranDataDao.insert(tranData);


        }
        //调整单操作
        //库存调整单的总的成本
        imAdjustment.setTotalAdjustmentCost(new BigDecimal(totalAdjustmentCost));
        imAdjustment.setTotalQuantity(totalAdjustQuantity);//设置总的数量
        imAdjustment.setCreateTime(new Date());
        imAdjustment.setStatus(1);//直接设置生效
        imAdjustment.setCreateUserName("scm_admin");//设置默认创建用户

        //根据外部编码加锁
        String lockImAdjustmentPath = "/inventory_adjustment" + imAdjustment.getExternalBillNo();
        RLock adJustLock = mRedisDistributedLockFactory.getLock(lockImAdjustmentPath);
        try {
            adJustLock.lock();
            //增加校验库存调整主表是否有已存在当前单据
            String externalBillNo = imAdjustmentReceiptPo.getImAdjustment().getExternalBillNo();
            ImAdjustmentPo imAdjustmentPo = imAdjustmentDao.getImAdjustmentByOutBizCode(externalBillNo);
            if (null != imAdjustmentPo) {
                throw new RuntimeException("外部单号存在重复,跳过插入");
            }
            imAdjustmentDao.insertSelective(imAdjustment);
            //调整单主表id
            Long mainKey = imAdjustment.getId();
            for (ImAdjustmentItemPo item : imAdjustmentItems) {
                //设置调整单id
                item.setAdjustmentId(String.valueOf(mainKey));
            }
            //批量插入Item
            imAdjustmentItemDao.batchInsertAdjustmentItem(imAdjustmentReceiptPo.getImAdjustmentItems());
        } finally {
            adJustLock.unlock();
        }


    }


}
