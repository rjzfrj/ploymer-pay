package com.zyzf.polymer.pay.controller;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zyzf.polymer.pay.common.core.utils.Base64Util;
import com.zyzf.polymer.pay.common.core.utils.MD5;
import com.zyzf.polymer.pay.common.core.utils.ServerPassUtil;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponSeller;
import com.zyzf.polymer.pay.service.SellerListService;
/**
 * 优惠券商户列表
 * @author wuhp
 *
 */
@Controller
public class CouponSellerAction {
	@Resource
	private SellerListService sellerListService;

	@ResponseBody
	@RequestMapping("/getSellerList")
	public String getSellerList(String action,String mcode,String tcode,Integer version) throws Exception  {
		
		//创建map存放数据
		Map<String, Object> map= new HashMap<String, Object>();
		List<PmsCouponSeller> sellerList=sellerListService.selectSellerList();
		map.put("action", action);
		map.put("mcode", mcode);
		map.put("tcode", tcode);
		map.put("version",version);
		map.put("sellerList",sellerList);
		
		if(sellerList.size()>=0 ){
		map.put("reqCode", "0000");
		map.put("reqMsg", "查询优惠券商户列表成功");
		
		}else{
		map.put("reqCode", "500");	
		map.put("reqMsg", "查询优惠券商户列表失败了");
		
		}
		//返回所需加密数据
		String result=ServerPassUtil.getPassUtil(mcode, map);
        return result;
	}
	
	    public static void main(String[] args) {
	    String a=Base64Util.getResolveDecoder("NTlEMTgyMURFQjM4QkY1NkZENzExNTQzOTlCREE3MzM=");
	    System.out.println("a===="+a);
	    String b= Base64Util.getResolveDecoder("SXM1bkhzSG1OTWJBZUQ0Zkg5YnNVaHJJdmsrdTlaV2lVMmc2bm1IUzUraS9Oa0pXT1YzS0ZFMk1wY3NXdzR4OEpvOE5OT1BJbXkyOHBEMkkvT0F6REhDNVVLS1cxTWprQVJ5UnlVeFJXbU1DVkdueTB3QWpqaU1oVlB1SXZxdlVJeE5FZjV6cDVsaTdndDV0c2hoZ3ZJbUNzRXhUWDRUbnhoSTlXUjBEa0lUWk5UL1loemtnM2M1bnZKekU0UlNRd0FWaFA0NzkwWGExMlA2V3E3YlBuZnRmQVduSnB6OWFTNDlVeGVHMzJ3T1lhVDdyL1VGa2E1L0JCSWpQWFhZc3lQaEN2cGoyaWxBM0FlYk1ZZlYxdUR6RzBlcWtidDQ1dHhwekg3TnVOTFNmOW1rZTFnVjA1QkI5eFBzTnJ5cmxJWDB6STRZK1FTdkUwMGo4NW50Wm5ud050aXBGWmE4VWNnV1Zrc24ySlJMa2d0SkNycitoTEc4akZhWnNMTTVZcURCV090VnVJb0lUbGxtYndiSGVjMlJUQmJoVWtzTm1jWWhUQ0E0UjNJYThRYmpBMVdtQUc0NVpDWlgzVWFrVkdPU0tOYzdCWmRubE9PL2MxRmNkQVNwdXZvMmJPdzhMSDV2L093ZXczODhCMHV5b0dNL0RybFg2TDhVQmdZaE5qV29hQ2hwT1E4TXlaeS95Y2w5NDZ4aUx2YTg0NVU3YUIrMEgrZFZYSXh4RHA4eGE2VmlnWHhkOS9sNUpNSjdDVlNwS2hUUytmRDZ3c2JIcEx0VHpOQ0xuaXJraHhKY25KR3hMcllEUVFaYjVpdFVXc1BaSkJnbk5lVVNZdWtRbXlmYmw4aGdWTVlTT1dsb3V3dTk2OCtOTVRac2ZlTjJ2NHlEWGVzOEJHR0hySUwxelhsSnJXRmY1TEdOeVpFNWc2RFVOS3laR093MExUd2RRVWFDeHlYRzNqVS9OWEcwNzR4aWZIUHhVc2tPOXo1eEJ5czJaakdpb0w1SHZLYlRSU2hnVnVkQS9tWnRHWVNMUjMyMlR2aUlQMzRSdGVQcTc5aEk5cFgwNEk0eXQrTXdEOXdJQWVyajdETUV2eDcxNmk1dWV2WGduS0JGUmVwQitVZmZGQ1R6VWk0S0p1VzBJTkxwcGx4ZW54dlNUNjZta2FKQnJ4R0ZqZ0NXZ015VVFYSGZyQ0U4ZWhXRklGTzhlTWcvdW9zYjF2MlYrUU5VZUpzeThaQUs3ZjlCMmpzeDRNVUVWcXAzdDN6WDJ0bG9HVkxCSDc3QlVDM05UUHJHdUtPcWtGRnQ2VXZmTk9vMjk");	
	    String c= MD5.mD5ofStr(b);	
	    System.out.println("c==="+c);
		
	    }

}
