package com.helloiot.iotdemo;

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
import org.omg.dds.topic.Topic;

import com.prismtech.agentv.core.types.NodeInfo;

public class IotDemoPublisher
{

   public static void main(String[] args)
   {
      // Set "serviceClassName" property to Vortex Cafe implementation
      System.setProperty(ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
            "com.prismtech.cafe.core.ServiceEnvironmentImpl");

      // Instantiate a DDS ServiceEnvironment
      ServiceEnvironment env = ServiceEnvironment.createInstance(
            IotDemoPublisher.class.getClassLoader());

      // Get the DomainParticipantFactory
      DomainParticipantFactory dpf = DomainParticipantFactory.getInstance(env);

      // Create a DomainParticipant with domainID=0
      DomainParticipant p = dpf.createParticipant(0);

      // Create a Topic named "HelloWorldData_Msg" and with "HelloWorldData.Msg" as a type.
      Topic<NodeInfo> topic = p.createTopic("NodeInfo", NodeInfo.class);

      // Create a Partition QoS with "HelloWorld example" as partition.
      Partition partition = PolicyFactory.getPolicyFactory(env)
            .Partition().withName("com/prismtech/node");

      // Create a Subscriber using default QoS except partition
      Publisher pub = p.createPublisher(p.getDefaultPublisherQos().withPolicy(partition));

      // Create Reliability and Durability QoS
      Reliability r = PolicyFactory.getPolicyFactory(env).Reliability().withReliable();
      Durability d = PolicyFactory.getPolicyFactory(env).Durability().withTransient();

      // Create DataReader on our topic with default QoS except Reliability and Durability
      DataWriter<NodeInfo> writer = pub.createDataWriter(topic,
            pub.getDefaultDataWriterQos().withPolicies(r, d));

      // The message we want to publish
      NodeInfo msg = new NodeInfo("uuid","info");

      try
      {
         System.out.println(" ________________________________________________________________");
         System.out.println("|");
         


         // Publish the message
         for(int i=0; i<10;i++){
        	 msg.uuid = "Uuid"+i;
        	 msg.info = "Info"+i;
        	 System.out.println("| Publish message : " + msg.uuid+"\t"+msg.info);
        	 writer.write(msg);
        	 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         System.out.println("|________________________________________________________________");
         System.out.println("");
         
      }
      catch (TimeoutException e)
      {
         // TimeoutException may happen using Reliable QoS (if publication buffers are full)
         e.printStackTrace();
      }

      try
      {
         // Wait to ensure data is received before we delete writer
         Thread.sleep(1000);
      }
      catch (InterruptedException e1)
      {
         e1.printStackTrace();
      }

      // Close Participant (closing also chlidren entities: Topic, Publisher, DataWriter)
      p.close();

   }

}
