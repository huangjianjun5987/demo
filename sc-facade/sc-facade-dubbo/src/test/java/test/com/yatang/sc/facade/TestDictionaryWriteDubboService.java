package test.com.yatang.sc.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yatang.sc.facade.dubboservice.DictionaryWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;
import com.yatang.sc.facade.dubboservice.DictionaryQueryDubboService;
import com.yatang.sc.facade.enums.PublicEnum;

import javax.sound.midi.Soundbank;

/**
 * @描述:
 * @类名:
 * @作者: kangdong
 * @创建时间: 2017/6/12 13:48
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestDictionaryWriteDubboService {
    @Autowired
    DictionaryWriteDubboService service;

    @Test
    public void testInsetDictionary() {
        DictionaryDto dto = new DictionaryDto();
        dto.setCode("ABC222");
        dto.setDictionary("财务单位22");
        dto.setRemark("用于财务的22");
        service.insertDictionaryInfo(dto);

    }

    @Test
    public void testUpdateDictionary() {
        DictionaryDto dto = new DictionaryDto();
        dto.setId(2);
        dto.setCode("ABC333");
        dto.setDictionary("财务单位3");
        dto.setRemark("用于财务的3。");
        service.updateDictionary(dto);
    }

    @Test
    public void testDelete(){
        service.deleteDictionaryInfo(2);
    }

    //-------------------------维护字典内容----------------------------------------

    @Test
    public void testInsertContent() {
        DictionaryContentDto dto = new DictionaryContentDto();
        dto.setDictionaryId(1);
        dto.setContentName("对");
        dto.setState(1);
        Response<Boolean> dic =  service.insertDictionaryContentInfo(dto);
        System.out.println("==================================="+dic);
    }

    /**
     * 更新内容
     */
    @Test
    public void testUpdateContent(){
        DictionaryContentDto dto = new DictionaryContentDto();
        dto.setId(2);
        dto.setContentName("对a");
        service.updateContentInfo(dto);
    }

    /**
     * 更新状态
     */
    @Test
    public void testUpdateContentState(){
        DictionaryContentDto dto = new DictionaryContentDto();
        dto.setId(2);
        dto.setState(0);
        service.updateContentInfo(dto);
    }
}
