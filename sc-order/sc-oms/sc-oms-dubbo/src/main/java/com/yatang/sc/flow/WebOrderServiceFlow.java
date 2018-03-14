package com.yatang.sc.flow;

import java.util.List;
import java.util.Map;

public interface WebOrderServiceFlow {
    boolean manualSplitOrder(String parentOrderId, List<Map<String, Long>> groups, String operator) throws Exception;
}
