package com.test.prototype_s.server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.test.prototype_s.model.Post;
import com.test.prototype_s.util.JSONParser;

import java.util.ArrayList;
import java.util.List;


public class GetPostsTask extends AsyncTask<GetPostsTask.PostType, Integer, List<Post>> {

	public enum PostType {
		FASHION,
		LIFESTYLE
	}

	private static final String TAG = "GetPostsTask";

	private static final String CAT_FASHION = "fashion";
	private static final String CAT_LIFESTYLE = "lifestyle";

	private Context mContext;
	private NetTaskCallback callback;

	public GetPostsTask(Context context, NetTaskCallback callback) {
		this.mContext = context;
		this.callback = callback;
	}
	

	@Override
	protected List<Post> doInBackground(PostType... params) {

		Log.d(TAG, "Retrieving Posts...");

		if (params != null && params.length > 0) {

			String cat = params[0] == PostType.FASHION ? CAT_FASHION : CAT_LIFESTYLE;

			Result prodsJSON = new ServerRequest().getJSONContent(mContext,
					ServerRequest.REQ_TYPE.POSTS, cat);

			if (prodsJSON.getResCode() == Result.ResultCode.RES_OK) {
				return JSONParser.buildPostsList(prodsJSON.getResult());
			} else {
				// TODO - consider error use cases and propagate message to user
			}
		}
		return new ArrayList<>();//avoid NPE
	}

	@Override
	protected void onPostExecute(List<Post> result) {
		callback.taskFinished(result);
	}			
	
}