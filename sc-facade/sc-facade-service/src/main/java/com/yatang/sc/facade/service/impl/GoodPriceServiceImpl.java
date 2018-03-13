package com.yatang.sc.facade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.utils.BigDemicalUtil;
import com.yatang.sc.facade.dao.ProdSellInfoEventsDao;
import com.yatang.sc.facade.dao.SellPriceInfoDao;
import com.yatang.sc.facade.dao.SellSectionPriceDao;
import com.yatang.sc.facade.domain.GoodsSellPriceQueryParamPo;
import com.yatang.sc.facade.domain.ProdBatchParameterPo;
import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdSellInfoEventsPo;
import com.yatang.sc.facade.domain.RegionBasicPo;
import com.yatang.sc.facade.domain.SellPriceInfoPo;
import com.yatang.sc.facade.domain.SellSectionPricePo;
import com.yatang.sc.facade.service.GoodPriceService;
import com.yatang.sc.facade.service.ProdPurchaseService;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/5/23 14:03
 * @版本: v1.0
 */
@Service("goodPriceService")
@Transactional
public class GoodPriceServiceImpl implements GoodPriceService {

	@Autowired
	private RedisDistributedLockFactory mRedisDistributedLockFactory;

	@Autowired
	private SellPriceInfoDao sellPriceInfoDao;

	@Autowired
	private SellSectionPriceDao sellSectionPriceDao;


	@Autowired
	private ProdSellInfoEventsDao prodSellInfoEventsDao;
	@Autowired
	private  ProdPriceChangeServiceImpl prodPriceChangeService;


	@Autowired
	private ProdPurchaseService prodPurchaseServiceImpl;


	/**
	 * 添加销售价格管理
	 *
	 * @param sellPriceInfoPo
	 * @return
	 */
	@Override
	public Long insertSellPrice(SellPriceInfoPo sellPriceInfoPo) {

		String lockPath = "/prod_sell_" + sellPriceInfoPo.getProductId()+sellPriceInfoPo.getBranchCompanyId();//根据商品id和分公司id枷锁
		RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
		try {
			rLock.lock();
			//判断该销售关系是否已经存在
			GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = BeanConvertUtils.convert(sellPriceInfoPo, GoodsSellPriceQueryParamPo.class);
			int i1 = sellPriceInfoDao.checkSellPriceInfo(goodsSellPriceQueryParamPo);
			if (i1>0){//该销售关系已经存在
				return null;
			}
		sellPriceInfoPo.setCreateTime(new Date());//设置创建时间
		sellPriceInfoPo.setStatus(1);//设置状态为启用（默认为启用）
		sellPriceInfoPo.setDeleteStatus(0);//设置为未删除状态
		sellPriceInfoPo.setAuditStatus(1);//审核状态:1:已提交;2:已审核;3:已拒绝
		sellPriceInfoPo.setFirstCreated(1);//第一次创建使用:1:是;0:否

		// 插入信息
		int id = sellPriceInfoDao.insert(sellPriceInfoPo);
		List<SellSectionPricePo> sellSectionPrices = sellPriceInfoPo.getSellSectionPrices();
		for (SellSectionPricePo s : sellSectionPrices){
			s.setSellPriceId(sellPriceInfoPo.getId());
			s.setDeleteStatus(0);//设置为未删除状态
		}
		int i = sellSectionPriceDao.insertPriceInfo(sellSectionPrices);
		//新增销售关系事件表
			ProdSellInfoEventsPo prodSellInfoEventsPo = new ProdSellInfoEventsPo();
			prodSellInfoEventsPo.setCreateTime(new Date());//设置创建时间
			prodSellInfoEventsPo.setCreateUserId(sellPriceInfoPo.getCreateUserId());//设置创建人
			prodSellInfoEventsPo.setPriceId(sellPriceInfoPo.getId());//设置关联id
			prodSellInfoEventsPo.setSerializedPayload(JSON.toJSONString(sellPriceInfoPo));//设置序列化数据
			prodSellInfoEventsDao.insert(prodSellInfoEventsPo);
			//记录价格变动记录
			//封装参数
			ProdPriceChangePo paramProdPrice = getParamProdPrice(sellPriceInfoPo);
			prodPriceChangeService.addProdPriceChange(paramProdPrice);
		return sellPriceInfoPo.getId();
		}finally {
			rLock.unlock();
		}
	}



	/**
	 * 删除销售区间价格（伪删除处理）
	 *
	 * @param
	 */
	@Override
	public boolean deleteSellPriceById(Long id,String userId) {
		// 1.删除主表id
		SellPriceInfoPo sellPriceInfoPo = new SellPriceInfoPo();
		sellPriceInfoPo.setId(id);
		sellPriceInfoPo.setDeleteStatus(1);
		sellPriceInfoPo.setModifyTime(new Date());
		sellPriceInfoPo.setModifyUserId(userId);
		int i = sellPriceInfoDao.deleteByPrimaryKey(sellPriceInfoPo);
		//2.修改附表状态
		int i1 = sellSectionPriceDao.deleteByPrimaryKey(id, 1);
		//删除修改记录表数据(物理删除)
		int priceId = prodSellInfoEventsDao.deleteByPriceId(id);


		return i>=1&&i1>=1;
	}


	@Override
	public List<RegionBasicPo> queryRegionListBySupplierId(String supplierId) {
		return null;
	}



	@Override
	public SellPriceInfoPo queryPriceDetailById(Long id) {
		//先调用dao查询销售关系主表和当前价格区间
		SellPriceInfoPo sellPriceInfoPo = sellPriceInfoDao.selectByPrimaryKey(id);
		//添加采购价
		sellPriceInfoPo.setPurchasePrice(this.getPurchasePrice(sellPriceInfoPo.getBranchCompanyId(),sellPriceInfoPo.getProductId()));

		//再查询该销售关系下的审核中的区间价格
		ProdSellInfoEventsPo prodSellInfoEventsPo=prodSellInfoEventsDao.queryByPriceId(id);
		if (prodSellInfoEventsPo!=null){
			SellPriceInfoPo sellPriceInfoPoInReview=JSONObject.parseObject(prodSellInfoEventsPo.getSerializedPayload(),SellPriceInfoPo.class);
			List<SellSectionPricePo> sellSectionPricePos=sellPriceInfoPoInReview.getSellSectionPrices();
			List<SellSectionPricePo> sellSectionPricePosNew=new ArrayList<>();
			//设置唯一的主键索引，用于前端遍历用
			if (sellSectionPricePos!=null && sellSectionPricePos.size()>0){
				Long index=1L;
				for (SellSectionPricePo sellSectionPricePo:sellSectionPricePos){
					sellSectionPricePo.setId(index);
					sellSectionPricePosNew.add(sellSectionPricePo);
					index++;
				}
			}
			sellPriceInfoPoInReview.setSellSectionPrices(sellSectionPricePosNew);
			sellPriceInfoPoInReview.setPurchasePrice(sellPriceInfoPo.getPurchasePrice());
			sellPriceInfoPo.setSellPricesInReview(sellPriceInfoPoInReview);
		}
		//计算毛利率
		this.addGrossRate(sellPriceInfoPo);
		return sellPriceInfoPo;
	}



	/**
	 * 修改销售价格
	 *
	 * @param sellPriceInfoPo
	 * @return
	 */
	@Override
	public boolean updateSellPrice(SellPriceInfoPo sellPriceInfoPo) {
		sellPriceInfoPo.setModifyTime(new Date());//设置修改时间
		sellPriceInfoPo.setStatus(1);//设置状态为启用（默认为启用）
		sellPriceInfoPo.setDeleteStatus(0);//设置为未删除状态
		sellPriceInfoPo.setAuditStatus(1);//审核状态:1:已提交;2:已审核;3:已拒绝
		sellPriceInfoPo.setFirstCreated(0);//第一次创建使用:1:是;0:否
		// 插入信息
		List<SellSectionPricePo> sellSectionPrices = sellPriceInfoPo.getSellSectionPrices();
		for (SellSectionPricePo s : sellSectionPrices){
			s.setSellPriceId(sellPriceInfoPo.getId());
			s.setDeleteStatus(0);//设置为未删除状态
		}
		//新增销售关系事件表
		ProdSellInfoEventsPo prodSellInfoEventsPo = new ProdSellInfoEventsPo();
		prodSellInfoEventsPo.setCreateTime(new Date());//设置创建时间
		prodSellInfoEventsPo.setCreateUserId(sellPriceInfoPo.getCreateUserId());//设置创建人
		prodSellInfoEventsPo.setPriceId(sellPriceInfoPo.getId());//设置关联id
		prodSellInfoEventsPo.setSerializedPayload(JSON.toJSONString(sellPriceInfoPo));//设置序列化数据
		prodSellInfoEventsDao.insert(prodSellInfoEventsPo);

		//修改主表
		SellPriceInfoPo sellPriceInfoPo1 = new SellPriceInfoPo();
		sellPriceInfoPo1.setId(sellPriceInfoPo.getId());//设置修改id
		sellPriceInfoPo1.setAuditStatus(1);//设置状态为已提交
		int ic = sellPriceInfoDao.updateByPrimaryKeySelective(sellPriceInfoPo1);
		//记录价格变动记录
		//封装参数
		//查询当前版本价格
		ProdPriceChangePo paramProdPrice = getParamProdPrice(sellPriceInfoPo);
		SellPriceInfoPo priceInfoPo = sellPriceInfoDao.selectByPrimaryKey(sellPriceInfoPo.getId());//查询以前版本
		paramProdPrice.setPrice(priceInfoPo.getLowestPrice());//设置以前价格
		paramProdPrice.setCreateUserId(sellPriceInfoPo.getModifyUserId());
		paramProdPrice.setCreateTime(sellPriceInfoPo.getModifyTime());
		prodPriceChangeService.addProdPriceChange(paramProdPrice);
		return ic>=1;
	}


	/**
	 * 根据商品信息和过滤条件查询价格详情
	 *
	 * @param goodsSellPriceQueryParamPo
	 * @return
	 */
	@Override
	public List<SellPriceInfoPo> getGoodsSellPrice(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo) {
		List<SellPriceInfoPo> goodsSellPrice = sellPriceInfoDao.getGoodsSellPrice(goodsSellPriceQueryParamPo);
		return goodsSellPrice;
	}

	/**
	 * 根据传入商品id查询所有与商品有关的商品销售区域价格信息
	 *
	 * @param productId
	 * @return
	 */
	@Override
	public List<SellPriceInfoPo> getGoodsSellPriceByProductId(String productId) {
		List<SellPriceInfoPo> goodsSellPrice = sellPriceInfoDao.getGoodsSellPriceByProductId(productId);
		return goodsSellPrice;
	}

	/**
	 * 根据传入参数分页查询商品销售区域信息
	 *
	 * @param goodsSellPriceQueryParamPo
	 * @return
	 */
	@Override
	public PageInfo<SellPriceInfoPo> pageQuerySellPrice(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo) {
		PageHelper.startPage(goodsSellPriceQueryParamPo.getPageNum(),goodsSellPriceQueryParamPo.getPageSize());
		List<SellPriceInfoPo> goodsSellPrice = sellPriceInfoDao.getGoodsSellPrice(goodsSellPriceQueryParamPo);
		PageInfo<SellPriceInfoPo> pageInfo = new PageInfo<SellPriceInfoPo>(goodsSellPrice);

		//补充 审核中的区间价格 查询 用于页面展示  yinyuxin
		if (pageInfo.getList()!=null && pageInfo.getList().size()>0){
			List<SellPriceInfoPo> newSellPriceInfoPos=new ArrayList<>();
			for (SellPriceInfoPo sellPriceInfoPo:pageInfo.getList()){
				ProdSellInfoEventsPo prodSellInfoEventsPo=prodSellInfoEventsDao.queryByPriceId(sellPriceInfoPo.getId());
				if (prodSellInfoEventsPo!=null){
					SellPriceInfoPo sellPricePoInReview=JSONObject.parseObject(prodSellInfoEventsPo.getSerializedPayload(),SellPriceInfoPo.class);
					List<SellSectionPricePo> sellSectionPricePoList=sellPricePoInReview.getSellSectionPrices();
					List<SellSectionPricePo> sellSectionPricePoListnew=new ArrayList<>();
					Long index=1L;
					if (sellSectionPricePoList!=null && sellSectionPricePoList.size()>0){
						for (SellSectionPricePo sellSectionPricePo:sellSectionPricePoList){
							sellSectionPricePo.setId(index);
							sellSectionPricePoListnew.add(sellSectionPricePo);
							index++;
						}
						sellPricePoInReview.setSellSectionPrices(sellSectionPricePoListnew);
					}
					sellPriceInfoPo.setSellPricesInReview(sellPricePoInReview);
				}
				sellPriceInfoPo=this.addGrossRate(sellPriceInfoPo);
				//计算价格区间毛利率  yinyuxin
				sellPriceInfoPo.setPurchasePrice(prodPurchaseServiceImpl.queryPurchasePriceForProdSell(goodsSellPriceQueryParamPo.getProductId(),goodsSellPriceQueryParamPo.getBranchCompanyId()));
				newSellPriceInfoPos.add(sellPriceInfoPo);

			}
			pageInfo.setList(newSellPriceInfoPos);
		}

		return pageInfo;
	}

	/**
	 * 根据传入参数查询该商品在该子公司是否已配置
	 *
	 * @param goodsSellPriceQueryParamPo
	 * @returnP
	 */
	@Override
	public int checkSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo) {
		int i = sellPriceInfoDao.checkSellPriceInfo(goodsSellPriceQueryParamPo);
		return i;
	}

	/**
	 * 根据传入参数修改销售区间价状态
	 *
	 * @param convert1
	 * @return
	 */
	@Override
	public Boolean updateSellPriceStatus(SellPriceInfoPo convert1) {
		convert1.setModifyTime(new Date());//设置修改时间
		int i = sellPriceInfoDao.updateSellPriceStatus(convert1);


		return i>=1;
	}

	/**
	 * 根据传入的信息批量修改启用禁用状态
	 *
	 * @param convert1
	 * @return
	 */
	@Override
	public Boolean prodBatchUpdate(ProdBatchParameterPo convert1) {
		int i = sellPriceInfoDao.prodBatchUpdate(convert1);
		return i>=1;
	}

	/**
	 * 根据传入的信息批量上架
	 *
	 * @param convert1
	 * @param sellPriceInfoPos
	 * @return
	 */
	@Override
	public Boolean prodBatchUpdateInfo(ProdBatchParameterPo convert1, List<SellPriceInfoPo> sellPriceInfoPos) {
		Boolean aBoolean=true;
		if (convert1.getProductIds().size()>0){
			//批量修改商品销售状态为启用
			 aBoolean = this.prodBatchUpdate(convert1);

		}
		//批量新增商品与子公司的销售关系
		if (null!=sellPriceInfoPos&&sellPriceInfoPos.size()>0) {
			 aBoolean = this.prodBatchAdd(sellPriceInfoPos);
			for (SellPriceInfoPo sellPriceInfoPo:sellPriceInfoPos){
					//记录价格变动记录
					//封装参数
					ProdPriceChangePo paramProdPrice = getParamProdPrice(sellPriceInfoPo);
					prodPriceChangeService.addProdPriceChange(paramProdPrice);
			}
		}
		return aBoolean;

	}

	/**
	 * 根据采购关系id查询采购关系（包含已删除）
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SellPriceInfoPo getGoodsSellPriceById(Long id) {
		return sellPriceInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public List<SellPriceInfoPo> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds) {
		return sellPriceInfoDao.getGoodsSellPriceByProductIdsAndCompanyId(branchCompanyId, productIds);
	}

	/**
	 * 根据传入的信息批量增加商品与子公司的销售关系
	 *
	 * @param convert
	 * @return
	 */
	public Boolean prodBatchAdd(List<SellPriceInfoPo> convert) {
		int i=0;
		for (SellPriceInfoPo sellPriceInfoPo:convert){
			String lockPath = "/prod_sell_" + sellPriceInfoPo.getProductId()+sellPriceInfoPo.getBranchCompanyId();//根据商品id和分公司id枷锁
			RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
			try {
				rLock.lock();
				GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = BeanConvertUtils.convert(sellPriceInfoPo, GoodsSellPriceQueryParamPo.class);
				int i1 = sellPriceInfoDao.checkSellPriceInfo(goodsSellPriceQueryParamPo);
				if (i1>0){//该销售关系已经存在
					return true;
				}
				sellPriceInfoPo.setCreateTime(new Date());//设置创建时间
				sellPriceInfoPo.setStatus(1);//设置状态为启用（默认为启用）
				sellPriceInfoPo.setDeleteStatus(0);//设置为未删除状态
				sellPriceInfoPo.setAuditStatus(1);//审核状态:1:已提交;2:已审核;3:已拒绝
				sellPriceInfoPo.setFirstCreated(1);//第一次创建使用:1:是;0:否

				// 插入信息
				int id = sellPriceInfoDao.insert(sellPriceInfoPo);
				List<SellSectionPricePo> sellSectionPrices = sellPriceInfoPo.getSellSectionPrices();
				for (SellSectionPricePo s : sellSectionPrices){
					s.setSellPriceId(sellPriceInfoPo.getId());
					s.setDeleteStatus(0);//设置为未删除状态
				}
				 i = sellSectionPriceDao.insertPriceInfo(sellSectionPrices);
				//新增销售关系事件表
				ProdSellInfoEventsPo prodSellInfoEventsPo = new ProdSellInfoEventsPo();
				prodSellInfoEventsPo.setCreateTime(new Date());//设置创建时间
				prodSellInfoEventsPo.setCreateUserId(sellPriceInfoPo.getCreateUserId());//设置创建人
				prodSellInfoEventsPo.setPriceId(sellPriceInfoPo.getId());//设置关联id
				prodSellInfoEventsPo.setSerializedPayload(JSON.toJSONString(sellPriceInfoPo));//设置序列化数据
				prodSellInfoEventsDao.insert(prodSellInfoEventsPo);
				return i >= 1;
			}finally {
				rLock.unlock();
			}
		}
		// 插入信息
		return i>=1;
	}


	/**
	 * 审核完成修改状态为已完成
	 *
	 * @param sellPriceInfoPo
	 * @return
	 */
	public Boolean updateAuditSellPrice(SellPriceInfoPo sellPriceInfoPo){
		int i;
		//删除修改记录表数据
		i=prodSellInfoEventsDao.deleteByPriceId(sellPriceInfoPo.getId());
		if (2==sellPriceInfoPo.getAuditStatus()){//已审核状态
			sellPriceInfoPo.setFirstCreated(0);//第一次创建使用:1:是;0:否(默认0)
			sellPriceInfoPo.setAuditTime(new Date());
			//修改原有主表数据
			 i = sellPriceInfoDao.updateByPrimaryKeySelective(sellPriceInfoPo);
			//删除关联子表数据
			i=sellSectionPriceDao.deleteByPriceId(sellPriceInfoPo.getId());
			for (SellSectionPricePo sectionPrice:sellPriceInfoPo.getSellSectionPrices()){
				sectionPrice.setSellPriceId(sellPriceInfoPo.getId());
			}
			i=sellSectionPriceDao.insertPriceInfo(sellPriceInfoPo.getSellSectionPrices());

		}else {//为已拒绝
			//修改当前表数据状态为已拒绝
			SellPriceInfoPo sellPriceInfoPo1=new SellPriceInfoPo();
			sellPriceInfoPo1.setId(sellPriceInfoPo.getId());
			sellPriceInfoPo1.setAuditStatus(sellPriceInfoPo.getAuditStatus());
			sellPriceInfoPo1.setAuditUserId(sellPriceInfoPo.getAuditUserId());
			sellPriceInfoPo1.setAuditTime(new Date());
			i=sellPriceInfoDao.updateByPrimaryKeySelective(sellPriceInfoPo1);
			//修改修改记录表状态为已拒绝
			//新增销售关系事件表
			ProdSellInfoEventsPo prodSellInfoEventsPo = new ProdSellInfoEventsPo();
			prodSellInfoEventsPo.setCreateTime(new Date());//设置创建时间
			prodSellInfoEventsPo.setCreateUserId(sellPriceInfoPo.getAuditUserId());//设置创建人
			prodSellInfoEventsPo.setPriceId(sellPriceInfoPo.getId());//设置关联id
			prodSellInfoEventsPo.setSerializedPayload(JSON.toJSONString(sellPriceInfoPo));//设置序列化数据
			i=prodSellInfoEventsDao.insert(prodSellInfoEventsPo);
		}
		return true;
}

	/**
	 * 根据传入参数修改子表信息
	 *
	 * @param sellSectionPricePo
	 * @return
	 */
	@Override
	public Boolean prodSellSectionUpadte(SellSectionPricePo sellSectionPricePo) {
		int i = sellSectionPriceDao.updateByPrimaryKeySelective(sellSectionPricePo);
		return i>=1;
	}


	/**
	 * 根据传入信息查询审核状态不是已提交和为未删除状态的销售区间价格
	 * @param goodsSellPriceQueryParamPo
	 * @author kangdong
	 * @return
	 */
	@Override
	public SellPriceInfoPo getGoodsSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo){
		return sellPriceInfoDao.getGoodsSellPriceInfo(goodsSellPriceQueryParamPo);
	}

	/**
	 * 封装价格变动参数
	 * @return
	 */
	private ProdPriceChangePo getParamProdPrice(SellPriceInfoPo sellPriceInfoPo){
		ProdPriceChangePo prodPriceChangePo = new ProdPriceChangePo();
		prodPriceChangePo.setChangeType(1);//设置变更类型:0:采购进价变更;1:售价变更
		prodPriceChangePo.setBranchCompanyId(sellPriceInfoPo.getBranchCompanyId());//设置子公司
		prodPriceChangePo.setProductId(sellPriceInfoPo.getProductId());//设置商品id
		prodPriceChangePo.setCreateTime(sellPriceInfoPo.getCreateTime());//设置操作时间
		prodPriceChangePo.setCreateUserId(sellPriceInfoPo.getCreateUserId());//设置操作人
		prodPriceChangePo.setNewestPrice(sellPriceInfoPo.getLowestPrice());//设置最新价格
		prodPriceChangePo.setGrossProfitMargin(BigDemicalUtil.getInterestRate(sellPriceInfoPo.getLowestPrice(),getPurchasePrice(sellPriceInfoPo.getBranchCompanyId(),sellPriceInfoPo.getProductId()),2));//设置毛利率
		prodPriceChangePo.setPriceId(sellPriceInfoPo.getId());//设置原销售价格id
		return prodPriceChangePo;
	}



	@Override
	public SellPriceInfoPo addGrossRate(SellPriceInfoPo sellPriceInfoPo) {
		List<SellSectionPricePo> sellSectionPricePos=sellPriceInfoPo.getSellSectionPrices();
		BigDecimal purchasePrice = getPurchasePrice(sellPriceInfoPo.getBranchCompanyId(), sellPriceInfoPo.getProductId());
		this.reckonGrossRate(sellSectionPricePos,purchasePrice);

		SellPriceInfoPo sellPriceInfoPoInReview=sellPriceInfoPo.getSellPricesInReview();
		if (sellPriceInfoPoInReview!=null && sellPriceInfoPoInReview.getSellSectionPrices()!=null && sellPriceInfoPoInReview.getSellSectionPrices().size()>0){
			this.reckonGrossRate(sellPriceInfoPoInReview.getSellSectionPrices(),purchasePrice);
			sellPriceInfoPoInReview.setSellSectionPrices(sellPriceInfoPoInReview.getSellSectionPrices());
		}
		sellPriceInfoPo.setSellSectionPrices(sellSectionPricePos);
		return sellPriceInfoPo;
	}



	@Override
	public Map<String, Integer> querySaleInnerNumberForApp(List<String> productIds,String branchCompanyId) {
		//批量查询销售内装数
		List<SellPriceInfoPo> sellPriceInfoPos = sellPriceInfoDao
				.querySaleInnerNumberForApp(productIds, branchCompanyId);
		Map<String,Integer> dataResult=new HashMap<>();
		if (sellPriceInfoPos != null && sellPriceInfoPos.size()>0){
			for (SellPriceInfoPo sellPriceInfoPo:sellPriceInfoPos){
				dataResult.put(sellPriceInfoPo.getProductId(),sellPriceInfoPo.getSalesInsideNumber());
			}
		}
		return dataResult;
	}



	/**
	 * 计算销售价格毛利率
	 * @param sellSectionPricePos
	 * @param
	 * @author yinyuxin
	 */
	public void reckonGrossRate(List<SellSectionPricePo> sellSectionPricePos,BigDecimal purchasePrice){
		if (sellSectionPricePos!=null && sellSectionPricePos.size()>0){
			for (SellSectionPricePo sellSectionPricePo:sellSectionPricePos){
					sellSectionPricePo.setPercentage(BigDemicalUtil.getInterestRate(sellSectionPricePo.getPrice(),purchasePrice,2));
			}
		}
	}

	/**
	 * 查询采购价（默认取主供应商的采购价，如果没有则选择第一个供应商地点的采购价）
	 * @param branchCompanyId
	 * @param productId
	 * @author lvheping
	 */
	private BigDecimal getPurchasePrice(String branchCompanyId,String productId) {
		BigDecimal purchasePrice = prodPurchaseServiceImpl.queryPurchasePriceForProdSell(productId,branchCompanyId);
		return purchasePrice;
	}

}
