/**
 *                             Vortex Cafe
 *
 *    This software and documentation are Copyright 2010 to 2015 PrismTech
 *    Limited and its licensees. All rights reserved. See file:
 *
 *                           docs/LICENSE.html
 *
 *    for full copyright notice and license terms.
 */
package com.helloiot.iotdemo;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import com.prismtech.agentv.core.types.NodeInfo;

public class IotDemoSubscriber2
{

	// Create a Subscriber using default QoS except partition
	Subscriber sub;

	// Create DataReader on our topic with default QoS except Reliability and Durability
	DataReader<NodeInfo> reader;
	
	// Create a DomainParticipant with domainID=0
	DomainParticipant p;

	public IotDemoSubscriber2(){
		// Set "serviceClassName" property to Vortex Cafe implementation
		System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,IoTUtil.SERVICE_ENV);

		// Instantiate a DDS ServiceEnvironment
		ServiceEnvironment env = ServiceEnvironment.createInstance(
				IotDemoSubscriber2.class.getClassLoader());

		// Get the DomainParticipantFactory
		DomainParticipantFactory dpf = DomainParticipantFactory.getInstance(env);

		// Create a DomainParticipant with domainID=0
		p = dpf.createParticipant(IoTUtil.DOMAIN);

		// Create a Topic named "HelloWorldData_Msg" and with "HelloWorldData.Msg" as a type.
		Topic<NodeInfo> topic = p.createTopic("NodeInfo", NodeInfo.class);

		// Create a Partition QoS with "HelloWorld example" as partition.
		Partition partition = PolicyFactory.getPolicyFactory(env)
				.Partition().withName(IoTUtil.PARTITION);

		// Create a Subscriber using default QoS except partition
		sub = p.createSubscriber(p.getDefaultSubscriberQos().withPolicy(partition));

		// Create Reliability and Durability QoS
		//Reliability r = PolicyFactory.getPolicyFactory(env).Reliability().withReliable();
		//Durability d = PolicyFactory.getPolicyFactory(env).Durability().withTransient();
		reader = sub.createDataReader(topic,sub.getDefaultDataReaderQos());//.withPolicies(r, d));
	}

	public DataReader<NodeInfo> getReader() {
		return reader;
	}

	public DomainParticipant getP() {
		return p;
	}

}
