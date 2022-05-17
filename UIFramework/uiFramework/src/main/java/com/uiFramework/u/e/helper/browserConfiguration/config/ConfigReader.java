package com.uiFramework.ust.experian.helper.browserConfiguration.config;

import com.uiFramework.ust.experian.helper.browserConfiguration.BrowserType;

public interface ConfigReader {
	public int getImpliciteWait();
	public int getExplicitWait();
	public int getPageLoadTime();
	public BrowserType getBrowserType();
	public String getUrl();
	public String getNormalUserName();
	public String getNormalUserPassword();
	public String getSuperUserName();
	public String getSuperUserPassword();
	public String getCaptcha();
}
