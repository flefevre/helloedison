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

import com.prismtech.agentv.core.types.NodeInfo;

public interface NodeListener {
	
	public void onNewNode(NodeInfo myNode);

}
