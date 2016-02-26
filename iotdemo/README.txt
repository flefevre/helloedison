====
                                Vortex Cafe

       This software and documentation are Copyright 2010 to 2015 PrismTech
       Limited and its licensees. All rights reserved. See file:

                              docs/LICENSE.html

       for full copyright notice and license terms.
====

Description
-----------
HelloWorld example demonstrates a simple usage of DDS.
The HelloWorldPublisher class publishes a "HelloWorld" message on a "HelloWorldData_Msg" Topic.
The HelloWorldSubscriber class subscribes to this Topic and display the received messages.

Building the demo
-----------------
Open a terminal in the helloworld/ directory.

If you use Ant run the following command:
   ant

If you use Apache Maven run the following command:
   mvn package


Running the demo
----------------
Open a command prompt and change directory to "helloworld".
Enter the following command to start the HelloWorldSubscriber:
   run_subscriber.sh
   (or run_subscriber.bat for Windows)

Open another command prompt and enter the following command to start the HelloWorldPublisher:
   run_publisher.sh
   (or run_publisher.bat for Windows)
