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
package com.helloiot.iotdemo.demo;

import java.util.ArrayList;
import java.util.List;

import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.event.SubscriptionMatchedEvent;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;

import com.helloiot.iotdemo.IotDemoSubscriber2;
import com.prismtech.agentv.core.types.NodeInfo;

public class IotDemo {

	public static void main(String[] args) {
		//Creation of the system
		IotSystem muIotSystem = new IotSystem();
		
		//Creation of the Subscriber
		IotDemoSubscriber2 myIotDemoSubscriber2 = new IotDemoSubscriber2();

		// Prepare a List of Sample<Msg> for received samples
		List<Sample<NodeInfo>> samples = new ArrayList<Sample<NodeInfo>>();
		
		DataReaderListener<NodeInfo> myDRL = new DataReaderListener<NodeInfo>() {
			
			public void onSubscriptionMatched(SubscriptionMatchedEvent<NodeInfo> arg0) {
				System.out.println("onSubscriptionMatched");
				System.out.println(arg0.getSource().getTopicDescription().getName());
			}
			
			public void onSampleRejected(SampleRejectedEvent<NodeInfo> arg0) {
				System.out.println("onSampleRejected");				
			}
			
			public void onSampleLost(SampleLostEvent<NodeInfo> arg0) {
				System.out.println("onSampleLost");
				
			}
			
			public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<NodeInfo> arg0) {
				System.out.println("onRequestedIncompatibleQos");
				
			}
			
			public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<NodeInfo> arg0) {
				System.out.println("onRequestedDeadlineMissed");
				
			}
			
			public void onLivelinessChanged(LivelinessChangedEvent<NodeInfo> arg0) {
				System.out.println("onLivelinessChanged");		
				System.out.println(arg0.getSource().read().next().getData().uuid);
			}
			
			public void onDataAvailable(DataAvailableEvent<NodeInfo> arg0) {
				System.out.println("onDataAvailable");	
				
				DataReader<NodeInfo> source = arg0.getSource();
				Iterator<NodeInfo> read = source.read();
				while (read.hasNext()) {
					System.out.println(read.next().getData().uuid);					
				}
				
			}
		};

		myIotDemoSubscriber2.getReader().setListener(myDRL);
		
		// Try to take samples every seconds. We stop as soon as we get some.
		while (true)
		{
			
			//myIotDemoSubscriber2.getReader().take(samples);
			
			//;
//			List<NodeInfo> nodes = new ArrayList<NodeInfo>();
//			for()
//			muIotSystem.addNodes();
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// nothing
			}
		}

		// Close Participant (closing also chlidren entities: Topic, Subscriber, DataReader)
		//myIotDemoSubscriber2.getP().close();

	}

}
