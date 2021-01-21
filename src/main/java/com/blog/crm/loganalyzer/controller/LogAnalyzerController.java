package com.blog.crm.loganalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.crm.enums.ModuleName;
import com.blog.crm.logger.GenericLogger;

/**
 * @author Anudeep
 * @version %I%, %G%
 * @since 26-Jun-2020
 *
 */
@Controller
public class LogAnalyzerController {

	/**
	 * @return log analyzer page with embedded Kibana dashboard
	 */
	@GetMapping(value = "/loganalyzer")
	public String userDetailsForm() {
		GenericLogger.info(ModuleName.LOGANALYZER, this, "[HTTP:GET]calling /loganalyzer");
		return "bsmartframework/loganalyzer/logAnalyzer";
	}

}
