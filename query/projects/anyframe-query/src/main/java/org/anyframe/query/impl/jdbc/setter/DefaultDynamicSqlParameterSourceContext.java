/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.anyframe.query.impl.jdbc.setter;

import org.apache.velocity.context.Context;

/**
 * Dynamic SQL일 경우, 쿼리문에 대체되어야 할 변수와 변수값을 저장한 SqlParameterSource를 관리하는 Context
 * 객체이다.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class DefaultDynamicSqlParameterSourceContext implements Context {
	private DefaultDynamicSqlParameterSource parameterSource = null;

	public DefaultDynamicSqlParameterSourceContext(
			DefaultDynamicSqlParameterSource parameterSource) {
		this.parameterSource = parameterSource;
	}

	public boolean containsKey(Object key) {
		return parameterSource.hasValue((String) key);
	}

	public Object get(String key) {
		try {
			return parameterSource.getValue(key);
		} catch (Exception e) {
			return null;
		}
	}

	public Object[] getKeys() {
		return parameterSource.getKeys();
	}

	public Object put(String key, Object value) {
		return parameterSource.addValue(key, value);
	}

	public Object remove(Object key) {
		// java.lang.UnsupportedOperationException
		// at
		// java.util.Collections$UnmodifiableMap.remove(Collections.java:1272)
		return null;
	}
}
