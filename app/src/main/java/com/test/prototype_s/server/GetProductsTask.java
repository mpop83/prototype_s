package com.test.prototype_s.server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.test.prototype_s.model.Product;
import com.test.prototype_s.util.JSONParser;

import java.util.ArrayList;
import java.util.List;


public class GetProductsTask extends AsyncTask<GetProductsTask.ProdType, Integer, List<Product>> {

	public enum ProdType {
		LAMPS,
		CLOTHES
	}

	private static final String TAG = "GetProductsTask";

	private static final String LAMPS_ID = "28107";
	private static final String CLOTHES_ID = "10203";

	private Context mContext;
	private NetTaskCallback callback;

	public GetProductsTask(Context context, NetTaskCallback callback) {
		this.mContext = context;
		this.callback = callback;
	}
	

	@Override
	protected List<Product> doInBackground(ProdType... params) {

		Log.d(TAG, "Retrieving products...");

		if (params != null && params.length > 0) {

			String catId = params[0] == ProdType.LAMPS ? LAMPS_ID : CLOTHES_ID;

			Result prodsJSON = new ServerRequest().getJSONContent(mContext,
					ServerRequest.REQ_TYPE.PRODUCTS, catId);

			if (prodsJSON.getResCode() == Result.ResultCode.RES_OK) {
				return JSONParser.buildProductsList(prodsJSON.getResult());
			} else {
				// TODO - consider error use cases and propagate message to user
			}
		}
		return new ArrayList<>();//avoid NPE
	}

	@Override
	protected void onPostExecute(List<Product> result) {
		callback.taskFinished(result);
	}			
	
}