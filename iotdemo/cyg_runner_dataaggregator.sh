#!/bin/sh
java \
	-Dddsi.network.interface=eth3 \
	-cp "target/iotdemo-0.0.1-SNAPSHOT.jar;./lib/cafe.jar;./lib/agentv-core-types_2.11-0.5.0-SNAPSHOT.jar;./lib/agentv-prelude_2.11-0.5.0-SNAPSHOT.jar" \
	com.helloiot.iotdemo.IotDemoDataAggragator  