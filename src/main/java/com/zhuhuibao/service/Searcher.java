package com.zhuhuibao.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zhuhuibao.utils.search.CollectionUtil;
import com.zhuhuibao.utils.search.EncryptUtil;
import com.zhuhuibao.utils.search.FormatUtil;
import com.zhuhuibao.utils.search.HttpClient;
import com.zhuhuibao.utils.search.JSONUtil;
import com.zhuhuibao.service.exception.ServiceException;
import com.zhuhuibao.service.exception.UnknownServiceException;
import com.zhuhuibao.service.exception.DetailedServiceException;
import com.zhuhuibao.utils.G;
import com.zhuhuibao.utils.L;

public class Searcher {
	private static String URL = null;
	private static String KEY = null;
	private static String SECRET = null;
	static {
		Map<?, ?> map = G.getConfig().getJSONObject("searchserver");
		URL = map.get("url").toString();
		KEY = map.get("key").toString();
		SECRET = map.get("secret").toString();
	}

	private static String makeSign(Map<?, ?> kv, String secret) {
		Set<?> keySet = kv.keySet();
		List<String> keys = new ArrayList<String>(keySet.size());
		for (Object key : keySet) {
			keys.add(key.toString());
		}
		Collections.sort(keys);

		String encodeString = null;
		{
			StringBuilder buf = new StringBuilder();
			for (String key : keys) {
				Object value = kv.get(key);
				buf.append(key);
				if (value != null) {
					buf.append(value);
				}
			}
			buf.append(secret);
			encodeString = buf.toString();
		}
		String realSign;
		try {
			realSign = EncryptUtil.md5(encodeString);
		} catch (Exception e) {
			throw new RuntimeException("Failed to encode sign", e);
		}
		return realSign;
	}

	public static Object request(String api, Map<String, Object> params)
			throws ServiceException {
		HttpClient client = new HttpClient();
		Map<String, Object> headers = CollectionUtil.arrayAsMap("X-Search-Api",
				api, "X-Search-Key", KEY, "X-Search-Time", "nope");
		String sign = makeSign(headers, SECRET);
		headers.put("X-Search-Sign", sign);
		try {
			client.doPost(URL, params, headers);
		} catch (Exception e) {
			throw new UnknownServiceException(e);
		}
		if (L.isInfoEnabled()) {
			L.info("SEARCH params: " + params + ", result: "
					+ client.getResponseBody());
		}
		Map<String, Object> map = JSONUtil.parseAsMap(client.getResponseBody());
		if (map == null) {
			throw new UnknownServiceException("Bad result: "
					+ client.getResponseBody());
		}
		Map<?, ?> errorAsMap = (Map<?, ?>) map.get("error");
		if (errorAsMap != null) {
			String msg = FormatUtil.parseString(errorAsMap.get("msg"));
			if (msg == null) {
				throw new UnknownServiceException("Bad result: "
						+ client.getResponseBody());
			}
			throw new DetailedServiceException(msg);
		}
		Object result = map.get("result");
		if (result == null) {
			throw new UnknownServiceException("Bad result: "
					+ client.getResponseBody());
		}
		return result;
	}

	public static Map<String, Object> wrapEqualQuery(
			Map<String, Map<String, Object>> query, String key, Object value) {
		return query.put(key,
				CollectionUtil.arrayAsMap("type", "equal", "value", value));
	}
}
