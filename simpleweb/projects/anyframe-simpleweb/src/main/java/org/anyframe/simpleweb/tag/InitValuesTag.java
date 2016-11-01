/*
 * Copyright 2002-2010 the original author or authors.
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
package org.anyframe.simpleweb.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Initial valueList Tag Class to support SubmitTag and LinkTag
 * 
 * @author Sooyeon Park
 *
 */
public class InitValuesTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * property valueList.
	 */
	private String valueList;

	/**
	 * Sets the initial valueList.
	 * 
	 * @param valueList
	 *            String
	 */
	public void setValueList(String valueList) {
		this.valueList = valueList;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		SubmitTag submitTag = (SubmitTag) findAncestorWithClass(this,
				SubmitTag.class);
		
		if (submitTag == null) {
			throw new JspException("no parent tag(submit or link) has been set");
		}
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * Passes attribute information up to the parent SubmitTag.
	 * <p>
	 * When we hit the end of the tag, we simply let our parent (which better be
	 * a DirectTag) know what the user wants to change a property value, and we
	 * pass the name/value pair that the user gave us, up to the parent
	 * </p>
	 * 
	 * @return <code>TagSupport.EVAL_PAGE</code>
	 * @throws JspException
	 *             if no value or body content has been set
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		SubmitTag submitTag = (SubmitTag) findAncestorWithClass(this,
				SubmitTag.class); 

		submitTag.setInitValues(this.valueList);

		this.valueList = null;

		return EVAL_PAGE;
	}

}