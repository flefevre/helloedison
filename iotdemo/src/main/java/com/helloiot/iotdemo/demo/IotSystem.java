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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.dds.sub.Sample;

import com.helloiot.iotdemo.NodeListener;
import com.prismtech.agentv.core.types.NodeInfo;

public class IotSystem {
	
	private NodeListener systemListener;
	
	private Map<String,NodeInfo> nodesMap;
	private List<Sample<NodeInfo>> nodes;
	
	
	public IotSystem(){
		nodesMap = new HashMap<String,NodeInfo>();
		nodes = new ArrayList<Sample<NodeInfo>>();
		systemListener=new NodeListener() {
			public void onNewNode(NodeInfo myNode) {
				System.out.println("Create a Class with name"+myNode.uuid+"on table X");
			}
		};
	}
	
	public void addNode(NodeInfo nodeInfo){
		if(!nodesMap.containsKey(nodeInfo.uuid)){
			nodesMap.put(nodeInfo.uuid,nodeInfo);
			systemListener.onNewNode(nodeInfo);
		}
	}
	
	public void addNodes(List<NodeInfo> nodeInfos){
		for(NodeInfo ni : nodeInfos){
			addNode(ni);
		}
	}
}
