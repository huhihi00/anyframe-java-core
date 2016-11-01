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
package org.anyframe.jasperreports.view;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;

/**
 * Implementation of <code>AbstractJasperReportsSingleFormatView</code>
 * that renders report results in CSV format.
 *<br><br>
 * 
 * We changed org.springframework.web.servlet.view.jasperreports.JasperReportsCsvView Class into org.anyframe.jasperreports.ReportsCsvView Class in Anyframe.
 * <ul>
 * 	<li>We re-wrote ExtendedJasperReportsCsvView class as a child of ExtendedAbstractJasperReportsSingleFormatView.</li>
 * </ul>
 * 
 * @since 1.1.3
 * 
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author modified by Sooyeon Park
 * 
*/
public class ReportsCsvView extends AbstractReportsSingleFormatView {

	public ReportsCsvView() {
		setContentType("text/csv");
	}

	protected JRExporter createExporter() {
		return new JRCsvExporter();
	}

	protected boolean useWriter() {
		return true;
	}

}