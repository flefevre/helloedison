package com.helloiot.iotdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.Durability;
import org.omg.dds.core.policy.Partition;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.policy.Reliability;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.Topic;

import com.prismtech.agentv.core.types.NodeInfo;

/**
 * Copy data from a topic to another
 * 
 * @author Benoit Maggi
 *
 */
public class IotDemoDataAggragator {

	private static final String PARTITION = "com/prismtech/node";
	private static final String SRC_TOPIC = "NodeInfo";
	private static final String TARGET_TOPIC = "NodeInfo2";
	private static final String COM_PRISMTECH_CAFE_CORE_SERVICE_ENVIRONMENT_IMPL = "com.prismtech.cafe.core.ServiceEnvironmentImpl";

	public static void main(String[] args) throws TimeoutException {
		// Set "serviceClassName" property to Vortex Cafe implementation
		System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
				COM_PRISMTECH_CAFE_CORE_SERVICE_ENVIRONMENT_IMPL);

		// Instantiate a DDS ServiceEnvironment
		ServiceEnvironment env = ServiceEnvironment.createInstance(IotDemoDataAggragator.class.getClassLoader());

		// Get the DomainParticipantFactory
		DomainParticipantFactory dpf = DomainParticipantFactory.getInstance(env);

		// Create a DomainParticipant with domainID=0
		DomainParticipant p = dpf.createParticipant(0);

		// Create a Topic named "HelloWorldData_Msg" and with
		// "HelloWorldData.Msg" as a type.
		Topic<NodeInfo> srcTopic = p.createTopic(SRC_TOPIC, NodeInfo.class);
		Topic<NodeInfo> targetTopic = p.createTopic(TARGET_TOPIC, NodeInfo.class);
		// Create a Partition QoS with "HelloWorld example" as partition.
		Partition partition = PolicyFactory.getPolicyFactory(env).Partition().withName(PARTITION);

		// Create a Subscriber using default QoS except partition
		Publisher pub = p.createPublisher(p.getDefaultPublisherQos().withPolicy(partition));

		// Create Reliability and Durability QoS
		Reliability r = PolicyFactory.getPolicyFactory(env).Reliability().withReliable();
		Durability d = PolicyFactory.getPolicyFactory(env).Durability().withTransient();

		// Create DataReader on our topic with default QoS except Reliability
		// and Durability
		DataWriter<NodeInfo> writer = pub.createDataWriter(targetTopic, pub.getDefaultDataWriterQos().withPolicies(r, d));
		Subscriber sub = p.createSubscriber(p.getDefaultSubscriberQos().withPolicy(partition));

		// The message we want to publish

		DataReader<NodeInfo> reader = sub.createDataReader(srcTopic, sub.getDefaultDataReaderQos());// .withPolicies(r,
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
		if (samples.size() != 0){
			System.out.println(" ________________________________________________________________");
			System.out.println("|");
			NodeInfo data = samples.get(0).getData();
			System.out.println("| IotDemoDataAggragator Received message : " + data.uuid);
			System.out.println("|________________________________________________________________");
			System.out.println("");
			data.uuid+="_fake";
			writer.write(data);
			System.out.println(" ________________________________________________________________");
			System.out.println("|");
			System.out.println("| IotDemoDataAggragator Publish message : " + data.uuid + "\t" + data.info);
			System.out.println("|________________________________________________________________");
			System.out.println("");
		}


		// Close Participant (closing also chlidren entities: Topic, Subscriber,
		// DataReader)
		p.close();
	}
}
