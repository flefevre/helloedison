/*****************************************************************************
 * Copyright (c) 2015 CEA
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Francois Le Fevre  francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package com.helloiot.iotdemo;

import java.util.ArrayList;
import java.util.List;

import org.omg.dds.sub.Sample;

import com.prismtech.agentv.core.types.NodeInfo;

public class IotDemo {

	public static void main(String[] args) {
		IotDemoSubscriber2 myIotDemoSubscriber2 = new IotDemoSubscriber2();

		// Prepare a List of Sample<Msg> for received samples
		List<Sample<NodeInfo>> samples = new ArrayList<Sample<NodeInfo>>();

		// Try to take samples every seconds. We stop as soon as we get some.
		while (samples.size() == 0)
		{
			myIotDemoSubscriber2.getReader().take(samples);
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// nothing
			}
		}
		System.out.println(" ________________________________________________________________");
		System.out.println("|");
		
		for(Sample<NodeInfo> sample: samples){
			System.out.println("| Received message : " + sample.getData().uuid);
			
		}
		
		System.out.println("|________________________________________________________________");
		System.out.println("");

		// Close Participant (closing also chlidren entities: Topic, Subscriber, DataReader)
		myIotDemoSubscriber2.getP().close();

	}

}
