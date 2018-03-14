package com.yatang.sc.service.impl;

import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.service.ProductHelper;
import com.yatang.sc.service.ThirdInventoryHelper;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ThirdInventoryHelperImpl implements ThirdInventoryHelper {

    protected final Logger log = LoggerFactory.getLogger(getClass());

   /* @Autowired
    IPurchaseOrderDubboService iPurchaseOrderDubboService;*/

    @Autowired
    OrderService orderService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    ProductHelper productHelper;

    @Override
    public void sendXCSignNumber(String orderId) {
       /* log.info("sendSignNumber,orderId:{}",orderId);
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        DecimalFormat df = new DecimalFormat("0.00");
        try{
            Order order = orderService.selectByPrimaryKey(orderId);
            if(order !=null){
                purchaseOrderDTO.setRelateOrderId(orderId);
                purchaseOrderDTO.setFranchiseeId(order.getFranchiseeId());
                purchaseOrderDTO.setShopCode(order.getFranchiseeStoreId());
                purchaseOrderDTO.setOperateUser(order.getProfileId());
                purchaseOrderDTO.setChannel(PurchaseOrderChannel.SUPPLY_CHAIN);
                Response<StoreDto> response = organizationService.queryStoreById(order.getFranchiseeStoreId());
                if(response != null && response.isSuccess() && response.getResultObject() != null){
                    purchaseOrderDTO.setShopName(response.getResultObject().getName());
                }
            }
            List<PurchaseOrderDetailDTO> purchaseOrderDetailDTOList = new ArrayList<>();
            List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId(orderId);
            if(CollectionUtils.isNotEmpty(commerceItemList)){
                for (CommerceItem commerceItem:commerceItemList){
                    PurchaseOrderDetailDTO purchaseOrderDetailDTO = new PurchaseOrderDetailDTO();
                    purchaseOrderDetailDTO.setPurchaseCount(commerceItem.getQuantity());
                    double orderDiscountShare = commerceItem.getItemPrice().getOrderDiscountShare()==null?0:commerceItem.getItemPrice().getOrderDiscountShare();
                    double salePrice = (commerceItem.getItemPrice().getAmount() - orderDiscountShare)/commerceItem.getQuantity();
                    long purchasePrice = (long)(Double.valueOf(df.format(salePrice))*100);
                    purchaseOrderDetailDTO.setPurchasePrice(purchasePrice);
                    ProductIndexDto productIndexDto = productHelper.findProductIndexById(commerceItem.getProductId());
                    if(productIndexDto != null){
                        purchaseOrderDetailDTO.setInternationalCode(productIndexDto.getInternationalCodes().get(0).getInternationalCode());
                        purchaseOrderDetailDTO.setProductName(productIndexDto.getName());
                        purchaseOrderDetailDTO.setUnit(productIndexDto.getMinUnit());
                        purchaseOrderDetailDTO.setProductType(productIndexDto.getProductType());
                        purchaseOrderDetailDTO.setPackingSpecifications(productIndexDto.getPackingSpecifications());
                        purchaseOrderDetailDTO.setQualityGuaranteePeriod(productIndexDto.getQualityGuaranteePeriod());
                        purchaseOrderDetailDTO.setFirstLevelCategoryId(productIndexDto.getFirstLevelCategoryId());
                        purchaseOrderDetailDTO.setSecondLevelCategoryId(productIndexDto.getSecondLevelCategoryId());
                        purchaseOrderDetailDTO.setThirdLevelCategoryId(productIndexDto.getThirdLevelCategoryId());
                        purchaseOrderDetailDTO.setProductName(productIndexDto.getName());
                    }
                    purchaseOrderDetailDTOList.add(purchaseOrderDetailDTO);
                }
            }
            Response<String> reponse = iPurchaseOrderDubboService.addPurchaseOrder(purchaseOrderDTO,purchaseOrderDetailDTOList);
            if(reponse.isSuccess()){
                log.info("sendSignNumber,success orderId:{}",orderId);
            }else{
                log.info("sendSignNumber,failed orderId:{}",orderId);
            }

        }catch (Exception e){
            log.info("sendSignNumber,failed,Exception, orderId:{}",orderId,e);
        }*/
    }
}
