#!/bin/sh
java \
	-Dddsi.network.interface=eth3 \
	-cp "target/helloedison-2.1.1.jar;./lib/cafe.jar;./lib/agentv-core-types_2.11-0.5.0-SNAPSHOT.jar;./lib/agentv-prelude_2.11-0.5.0-SNAPSHOT.jar" \
	helloedison.HelloWorldSubscriber  