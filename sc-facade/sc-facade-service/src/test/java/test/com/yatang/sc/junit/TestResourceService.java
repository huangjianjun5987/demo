package test.com.yatang.sc.junit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.service.ResourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class TestResourceService {

	protected final Log		log	= LogFactory.getLog(this.getClass());

	@Autowired
	private ResourceService	service;



	@Before
	public void setUp() throws Exception {
	}



	@Test
	public void testFindAll() {
	}



	@Test
	public void testMenuButton() {
	}

}
