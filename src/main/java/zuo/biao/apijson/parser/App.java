package zuo.biao.apijson.parser;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLProviderException
    {
    	long st = System.currentTimeMillis();
		String json = "{'Product:p':{'name~':'双11','@group':'name','@column':'name,max(price):max_price'}}";
		JSONObject req = JSONObject.parseObject(json);
		long fastJsonet = System.currentTimeMillis();
		//JSON解析太耗时了174ms
		long st2 = System.currentTimeMillis();
		APIJSONProvider apijsonProvider = new APIJSONProvider(req);
		apijsonProvider.setStatementType(StatementType.SELECT);
//		apijsonProvider.getTableBlackList().add("Retail");
//		apijsonProvider.getTableWhiteList().add("Retail");
//		apijsonProvider.getTableWhiteList().add("StorE");
//		apijsonProvider.getColumnBlackList().add("retail.id");
//		apijsonProvider.getColumnWhiteList().add("retail.*");
//		apijsonProvider.getColumnWhiteList().add("retail.amt");
//		apijsonProvider.getColumnBlackList().add("retail.amt");
//		apijsonProvider.getColumnWhiteList().add("store.id");
		SQLExplorer builder = new SQLExplorer(apijsonProvider);
		System.out.println(builder.getSQL());
		long et = System.currentTimeMillis();
		System.out.println();
		System.out.println("fastJson解析用时:" + (fastJsonet - st) + "ms");
		System.out.println("APIJSONParser解析用时:" + (et - st2) + "ms");
		System.out.println("合计用时:" + (et - st) + "ms");
		//实际解析耗时21ms
		 
    }
}
