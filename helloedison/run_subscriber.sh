#!/bin/sh
#
#                             Vortex Cafe
#
#    This software and documentation are Copyright 2010 to 2015 PrismTech
#    Limited and its licensees. All rights reserved. See file:
#
#                           docs/LICENSE.html
#
#    for full copyright notice and license terms.
#

java \
	-Dddsi.network.interface=lo \
	-cp target/classes:./lib/cafe.jar:./lib/agentv-core-types_2.11-0.5.0-SNAPSHOT.jar:./lib/agentv-prelude_2.11-0.5.0-SNAPSHOT.jar \
	helloedison.HelloWorldSubscriber  
