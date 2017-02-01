package com.test.prototype_s.util;

import android.text.TextUtils;

import com.test.prototype_s.model.Post;
import com.test.prototype_s.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Helper class which offers methods for parsing JSON data and return 
 * corresponded objects
 *
 */
public class JSONParser {

	/**
	 * Parse the provided data which should be in JSON format and builds a list of
	 * {@link Product} objects
	 * @param data - the String which contains the JSON format data.
	 * @return a list of {@link Product} or empty list
	 * @throws JSONException if there are errors when parsing data.
	 */
	public static List<Product> buildProductsList(String data) {
		List<Product> resultList = new ArrayList<Product>();
		try {
			JSONObject content = new JSONObject(data);
			JSONArray products = content.getJSONArray("products");
			final int respCount = products.length();
			for (int i = 0; i < respCount; i++) {
				JSONObject prodJSON = products.getJSONObject(i);
				String title = prodJSON.getString("name");
				double price = prodJSON.getDouble("price");
				JSONObject currObj = prodJSON.getJSONObject("currency");
				String currency = currObj.getString("symbol");

				String imgUrl = "";
				JSONArray imgs = prodJSON.getJSONArray("images");
				final int imgCount = imgs.length();
				for (int j = 0; j < imgCount; j++) {
					JSONObject imgJSON = imgs.getJSONObject(j);
					boolean isPrimary = imgJSON.getBoolean("primary");
					if (isPrimary) {
						imgUrl = imgJSON.getString("url");
						break;
					}
				}
				//fallback when no 'primary' found
				if (TextUtils.isEmpty(imgUrl) && imgCount > 0) {
					JSONObject imgJSON = imgs.getJSONObject(0);
					imgUrl = imgJSON.getString("url");
				}

				Product product = new Product(title, String.valueOf(price), currency, imgUrl);
				resultList.add(product);
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resultList;
	}


	/**
	 * Parse the provided data which should be in JSON format and builds a list of
	 * {@link Post} objects
	 * @param data - the String which contains the JSON format data.
	 * @return a list of {@link Post} or empty list
	 * @throws JSONException if there are errors when parsing data.
	 */
	public static List<Post> buildPostsList(String data) {
		List<Post> resultList = new ArrayList<Post>();
		try {
			JSONObject content = new JSONObject(data);
			JSONArray posts = content.getJSONArray("posts");
			final int respCount = posts.length();
			for (int i = 0; i < respCount; i++) {
				JSONObject postJSON = posts.getJSONObject(i);
				String title = postJSON.getString("title");
				String imgUrl = postJSON.getString("teaserImage");

				Post post = new Post(title, imgUrl);
				resultList.add(post);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resultList;
	}

}
