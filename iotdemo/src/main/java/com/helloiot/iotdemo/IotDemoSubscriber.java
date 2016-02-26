package com.helloiot.iotdemo;

import java.util.ArrayList;
import java.util.List;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import com.prismtech.agentv.core.types.NodeInfo;

public class IotDemoSubscriber {

	private static final String PARTITION = "com/prismtech/node";
	private static final String TOPIC = "NodeInfo2";

	public static void main(String[] args) {
		// Set "serviceClassName" property to Vortex Cafe implementation
		System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
				"com.prismtech.cafe.core.ServiceEnvironmentImpl");

		// Instantiate a DDS ServiceEnvironment
		ServiceEnvironment env = ServiceEnvironment.createInstance(IotDemoSubscriber.class.getClassLoader());

		// Get the DomainParticipantFactory
		DomainParticipantFactory dpf = DomainParticipantFactory.getInstance(env);

		// Create a DomainParticipant with domainID=0
		DomainParticipant p = dpf.createParticipant(0);

		// Create a Topic named "HelloWorldData_Msg" and with
		// "HelloWorldData.Msg" as a type.
		Topic<NodeInfo> topic = p.createTopic(TOPIC, NodeInfo.class);

		// Create a Partition QoS with "HelloWorld example" as partition.
		Partition partition = PolicyFactory.getPolicyFactory(env).Partition().withName(PARTITION);

		// Create a Subscriber using default QoS except partition
		Subscriber sub = p.createSubscriber(p.getDefaultSubscriberQos().withPolicy(partition));

		// Create Reliability and Durability QoS
		// Reliability r =
		// PolicyFactory.getPolicyFactory(env).Reliability().withReliable();
		// Durability d =
		// PolicyFactory.getPolicyFactory(env).Durability().withTransient();

		// Create DataReader on our topic with default QoS except Reliability
		// and Durability
		DataReader<NodeInfo> reader = sub.createDataReader(topic, sub.getDefaultDataReaderQos());// .withPolicies(r,
																									// d));

		// Prepare a List of Sample<Msg> for received samples
		List<Sample<NodeInfo>> samples = new ArrayList<Sample<NodeInfo>>();

		// Try to take samples every seconds. We stop as soon as we get some.
		while (samples.size() == 0) {
			reader.take(samples);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// nothing
			}
		}
		System.out.println(" ________________________________________________________________");
		System.out.println("|");
		System.out.println("| Received message : " + samples.get(0).getData().uuid);
		System.out.println("|________________________________________________________________");
		System.out.println("");

		// Close Participant (closing also chlidren entities: Topic, Subscriber,
		// DataReader)
		p.close();

	}

}
