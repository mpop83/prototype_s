package com.test.prototype_s.server;


import com.test.prototype_s.model.Item;

import java.util.List;

public interface NetTaskCallback {

	public void taskFinished(List<? extends Item> result);
}
