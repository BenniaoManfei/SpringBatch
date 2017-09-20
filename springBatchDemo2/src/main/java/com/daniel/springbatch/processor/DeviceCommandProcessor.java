package com.daniel.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.daniel.springbatch.model.bean.DeviceCommand;

public class DeviceCommandProcessor implements ItemProcessor<DeviceCommand, DeviceCommand> {

	@Override
	public DeviceCommand process(DeviceCommand deviceCommand) throws Exception {
		System.err.println("send command to device,id="+deviceCommand.getId());
		deviceCommand.setStatus("SENT");
		
		return deviceCommand;
	}

}
