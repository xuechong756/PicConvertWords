package com.hzy.pcw.main;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.google.gson.Gson;
import com.hzy.pcw.main.entiy.ResultWords;
import com.hzy.pcw.main.entiy.Words;

public class Main {

	public static void main(String[] args) {
		
		// 设置APPID/AK/SK
		final String APP_ID = "67626";
		final String API_KEY = "1d964c00b4c540a9a55c5a895b9fbb8e";
		final String SECRET_KEY = "a1653525c0a94c1b9ff033767da0ee3e";

		// 初始化一个OcrClient
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 调用身份证识别接口
//		String idFilePath = "test.jpg";
//		JSONObject idcardRes = client.idcard(idFilePath, true, null);
//		System.out.println(idcardRes.toString(2));
//
//		// 调用银行卡识别接口
//		String bankFilePath = "test_bank.jpg";
//		JSONObject bankRes = client.bankcard(bankFilePath);
//		System.out.println(bankRes.toString(2));

		// 调用通用识别接口
		String genFilePath = "a.jpg";
		JSONObject genRes = client.basicGeneral(genFilePath, new HashMap<String, String>());
		System.out.println(genRes.toString(2));
		ResultWords resultWords = new Gson().fromJson(genRes.toString(2), ResultWords.class);
		List<Words> listWords = resultWords.getWords_result();
		for(Words words : listWords)
		{
			System.out.println(words.getWords());
		}
		

		// 调用通用识别（含位置信息）接口
		//String genFilePath1="test_general.jpg";
//		JSONObject genRes1 = client.general(genFilePath1, new HashMap<String, String>());
//		System.out.println(genRes1.toString(2));
	}

	public static String getChinese(String paramValue) {
		String regex = "([\\s*|\t|\r|\n|，。？！‘’“”、\u4e00-\u9fa5]+)";
		StringBuilder stringBuilder = new StringBuilder();
		Matcher matcher = Pattern.compile(regex).matcher(paramValue);
		while (matcher.find()) {
			stringBuilder.append(matcher.group(0));
		}
		return stringBuilder.toString();
	}
}
