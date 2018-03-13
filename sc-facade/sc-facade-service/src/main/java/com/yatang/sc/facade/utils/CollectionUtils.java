package com.yatang.sc.facade.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {
	
	/**
	 * 查找两个List集合的差异值并返回
	 * @param oneSet
	 * @param twoSet
	 * @return List<Long>
	 */
	public static List<Long> getDiffrent(List<Long> oneList, List<Long> twoList) {  
    	 List<Long> diff = new ArrayList<Long>();  
    	 List<Long> maxList = oneList;  
    	 List<Long> minList = twoList;  
         if(twoList.size() > oneList.size())  
         {  
             maxList = twoList;
             minList = oneList; 
         }  
         Map<Long,Integer> map = new HashMap<Long,Integer>(maxList.size());  
         for (Long i : maxList) {  
             map.put(i, 1);  
         }  
         for (Long ii : minList) {  
             if(map.get(ii)!=null)  
             {  
                 map.put(ii, 2);  
                 continue;  
             }  
             diff.add(ii);  
         }  
         for(Map.Entry<Long, Integer> entry:map.entrySet())  
         {  
             if(entry.getValue()==1)  
             {  
                 diff.add(entry.getKey());  
             }  
         }  
        return diff;  
    } 
    
    /**
     * 查找两个set集合的差异值并返回
     * @param oneSet
     * @param twoSet
     * @return Set<Long>
     */
	public static Set<Long> getDiffrent(Set<Long> oneSet, Set<Long> twoSet) {  
    	Set<Long> diff = new HashSet<Long>();  
    	Set<Long> maxList = oneSet;  
    	Set<Long> minList = twoSet;  
    	if(twoSet.size() > oneSet.size())  
    	{  
    		maxList = twoSet;
    		minList = oneSet; 
    	}  
    	Map<Long,Integer> map = new HashMap<Long,Integer>(maxList.size());  
    	for (Long i : maxList) {  
    		map.put(i, 1);  
    	}  
    	for (Long ii : minList) {  
    		if(map.get(ii)!=null)  
    		{  
    			map.put(ii, 2);  
    			continue;  
    		}  
    		diff.add(ii);
    	}  
    	for(Map.Entry<Long, Integer> entry:map.entrySet())  
    	{  
    		if(entry.getValue()==1)  
    		{  
    			diff.add(entry.getKey());  
    		}  
    	}  
    	return diff;  
    } 
	
}
