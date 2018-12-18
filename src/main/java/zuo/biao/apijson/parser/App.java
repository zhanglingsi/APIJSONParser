package zuo.biao.apijson.parser;

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
		String json = "{\r\n" + 
				"	\"Retail\": {\r\n" + 
				"		\"description\": \"20190101,元旦快乐\",\r\n" + 
				"		\"INVOICE_ADDRESS\": 13,\r\n" + 
				"		\"docno\": \"RE1807250000002\"\r\n" + 
				"	}\r\n" + 
				"}";
		JSONObject req = JSONObject.parseObject(json);
		long fastJsonet = System.currentTimeMillis();
		//JSON解析太耗时了174ms
		long st2 = System.currentTimeMillis();
		APIJSONProvider apijsonProvider = new APIJSONProvider(req);
		apijsonProvider.setStatementType(StatementType.SELECT);
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