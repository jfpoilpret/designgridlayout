//  Copyright 2009 Jason Aaron Osgood, Jean-Francois Poilpret
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package net.java.dev.designgridlayout.internal.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.java.dev.designgridlayout.internal.engine.ILayoutEngine;

public final class LayoutEngineProxy
{
	static public ILayoutEngine createProxy()
	{
		// Build a proxy
		return (ILayoutEngine) Proxy.newProxyInstance(
			Thread.currentThread().getContextClassLoader(),
			new Class<?>[]{ILayoutEngine.class},
			new Interceptor());
	}

	static public void setDelegate(ILayoutEngine proxy, ILayoutEngine delegate)
	{
		if (Proxy.isProxyClass(proxy.getClass()))
		{
			Object handler = Proxy.getInvocationHandler(proxy);
			if (handler instanceof Interceptor)
			{
				((Interceptor) handler).setDelegate(delegate);
			}
		}
	}

	static public ILayoutEngine getDelegate(ILayoutEngine proxy)
	{
		if (Proxy.isProxyClass(proxy.getClass()))
		{
			Object handler = Proxy.getInvocationHandler(proxy);
			if (handler instanceof Interceptor)
			{
				return ((Interceptor) handler).getDelegate();
			}
		}
		return null;
	}
	
	static private final class Interceptor implements InvocationHandler
	{
		//CSOFF: IllegalThrowsCheck
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			return method.invoke(_delegate, args);
		}
		//CSON: IllegalThrowsCheck
		
		public void setDelegate(ILayoutEngine delegate)
		{
			_delegate = delegate;
		}
		
		public ILayoutEngine getDelegate()
		{
			return _delegate;
		}
		
		private ILayoutEngine _delegate;
	}
}
