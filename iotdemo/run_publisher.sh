#!/bin/sh

java \
	-Dddsi.network.interface=lo \
	-cp target/classes:./lib/cafe.jar:./lib/agentv-core-types_2.11-0.5.0-SNAPSHOT.jar:./lib/agentv-prelude_2.11-0.5.0-SNAPSHOT.jar \
	com.helloiot.iotdemo.IotDemoPublisher
