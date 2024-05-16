package com.attraction.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.attraction.entity.Sido;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataCrawler {
	
	private final String SERVICE_KEY;
	private final String NUM_OF_ROWS;
	private final String MOBILE_OS;
	private final String MOBILE_APP;
	private final String ATTRACTION_ROWS;
	
	private static final String UTF8 = "UTF-8";
	private static final String CONTENT_TYPE = "json";
	private static final String PAGE_NO = "1";
	
	public DataCrawler(
			@Value("${api.service-key}") String serviceKey,
			@Value("${api.num-of-rows}") String rows,
			@Value("${api.mobile-os}") String mobileOs,
			@Value("${api.mobile-app}") String mobileApp,
			@Value("${api.attraction-rows}") String attractionRows
	) {
		this.SERVICE_KEY = serviceKey;
		this.NUM_OF_ROWS = rows;
		this.MOBILE_OS = mobileOs;
		this.MOBILE_APP = mobileApp;
		this.ATTRACTION_ROWS = attractionRows;
	}
	
	// 위치 기반 데이터 가져오기
	public String getAttractionInfo(String pageNo) throws IOException {
		String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
		
		return crawlAttractionInfo(url, pageNo);
	}
	
	// 시 도 정보 가져오기
	public String getSido() throws IOException {
		String URL = "https://apis.data.go.kr/B551011/KorService1/areaCode1";
		
		return crawlSido(URL);
	}
	
	// 시 군 구 정보 가져오기
	public String getGuGun(Sido sido) throws IOException {
		String URL = "https://apis.data.go.kr/B551011/KorService1/areaCode1";
		
		return crawlGuGun(URL, String.valueOf(sido.getSidoCode()));	
	}
	
	// 여행 상세정보 가져오기
	public String getDetail(int contentId) throws IOException {
		String URL = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";
		
		return crawlDetail(URL, String.valueOf(contentId));
	}
	
	private String crawlDetail(String baseURL, String contentId) throws IOException {
		StringBuilder urlStringBuilder = initBuilder(new StringBuilder(baseURL), 0, PAGE_NO);
		
		urlStringBuilder = addProperty(urlStringBuilder, "&", "contentId", contentId);
		urlStringBuilder = addProperty(urlStringBuilder, "&", "defaultYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "firstImageYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "areacodeYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "catcodeYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "addrinfoYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "mapinfoYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "overviewYN", "Y");
		
		return crawl(urlStringBuilder.toString());
	}
	
	private String crawlGuGun(String baseURL, String sidoCode) throws IOException {
		StringBuilder urlStringBuilder = initBuilder(new StringBuilder(baseURL), 0, PAGE_NO);
		
		urlStringBuilder = addProperty(urlStringBuilder, "&", "areaCode", sidoCode);
		
		return crawl(urlStringBuilder.toString());
	}
	
	private String crawlSido(String baseURL) throws IOException {
		StringBuilder urlStringBuilder = initBuilder(new StringBuilder(baseURL), 0, PAGE_NO);
		
		return crawl(urlStringBuilder.toString());
	}
	
	private String crawlAttractionInfo(String baseURL, String pageNo) throws IOException {
		StringBuilder urlStringBuilder = initBuilder(new StringBuilder(baseURL), 1, pageNo);
		
		urlStringBuilder = addProperty(urlStringBuilder, "&", "listYN", "Y");
		urlStringBuilder = addProperty(urlStringBuilder, "&", "arrange", "A");
		
		return crawl(urlStringBuilder.toString());
	}
	
	private StringBuilder initBuilder(StringBuilder sb, int option, String pageNo) throws UnsupportedEncodingException {
		sb = addProperty(sb, "?", "serviceKey", SERVICE_KEY);
		sb = addProperty(sb, "&", "pageNo", pageNo);
		sb = addProperty(sb, "&", "numOfRows", option == 1 ? ATTRACTION_ROWS : NUM_OF_ROWS);
		sb = addProperty(sb, "&", "MobileOS", MOBILE_OS);
		sb = addProperty(sb, "&", "MobileApp", MOBILE_APP);
		sb = addProperty(sb, "&", "_type", CONTENT_TYPE);
		
		return sb;
	}
	
	private String crawl(String totalURL) throws IOException {
		URL url = new URL(totalURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		int responseCode = conn.getResponseCode();
		BufferedReader br;
		
		if(responseCode >= 200 && responseCode < 300) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder resultBuilder = new StringBuilder();
		String line;
		
		while((line = br.readLine()) != null) {
			resultBuilder.append(line);
		}
		br.close();
		
		conn.disconnect();
		
		return resultBuilder.toString();
	}
	
	private StringBuilder addProperty(StringBuilder sb, String code, String name, String value) throws UnsupportedEncodingException {
		return sb.append(code)
			.append(URLEncoder.encode(name, UTF8))
			.append("=")
			.append(URLEncoder.encode(value, UTF8));
	}
}
