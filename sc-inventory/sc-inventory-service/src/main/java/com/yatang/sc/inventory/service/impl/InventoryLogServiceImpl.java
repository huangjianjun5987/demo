package com.yatang.sc.inventory.service.impl;

import com.yatang.sc.inventory.dao.InventoryLogDao;
import com.yatang.sc.inventory.domain.InventoryLog;
import com.yatang.sc.inventory.service.InventoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("InventoryLogService")
public class InventoryLogServiceImpl implements InventoryLogService {
    @Autowired
    private InventoryLogDao inventoryLogDao;

    @Override
    public int insert(InventoryLog inventoryLog) {
        return inventoryLogDao.insert(inventoryLog);
    }

}
