package com.daniel.springbatch.model.bean;

import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * 将DeviceCommand对象转换成CSV的文件的每行
 *
 * @description
 *
 * @author DaiZM
 * @date 2017年9月19日
 *
 */
public class DeviceCommandLineAggregator implements LineAggregator<DeviceCommand> {

	@Override
	public String aggregate(DeviceCommand deviceCommand) {
		StringBuffer sb = new StringBuffer();
		sb.append(deviceCommand.getId());
		sb.append(",");
		sb.append(deviceCommand.getStatus());
		return sb.toString();
	}

}
