package com.daniel.springbatch.model.bean;

import org.springframework.batch.item.file.LineMapper;

/**
 * 将CSV的文件的每行转换成DeviceCommand对象
 *
 * @description
 *
 * @author DaiZM
 * @date 2017年9月19日
 *
 */
public class DeviceCommandLineMapper implements LineMapper<DeviceCommand> {

	@Override
	public DeviceCommand mapLine(String line, int lineNumber) throws Exception {
		
		//逗号分割每一行数据
		String[] args = line.split(",");
		
		DeviceCommand deviceCommand = new DeviceCommand();
		
		deviceCommand.setId(args[0]);
		deviceCommand.setStatus(args[1]);
		
		return deviceCommand;
	}

}
