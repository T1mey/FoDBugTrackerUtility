package com.fortify.processrunner.tfs.connection;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.UsernamePasswordCredentials;

import com.fortify.processrunner.context.Context;
import com.fortify.processrunner.context.ContextProperty;
import com.fortify.processrunner.tfs.context.IContextTFS;
import com.fortify.processrunner.util.rest.ContextAwareProxyConfigurationFactory;
import com.fortify.util.rest.ProxyConfiguration;

public final class TFSConnectionFactory 
{
	public static final void addContextProperties(Collection<ContextProperty> contextProperties, Context context) {
		contextProperties.add(new ContextProperty(IContextTFS.PRP_BASE_URL, "TFS base URL", context, "Read from console", false));
		contextProperties.add(new ContextProperty(IContextTFS.PRP_USER_NAME, "TFS user name", context, "Read from console", false));
		contextProperties.add(new ContextProperty(IContextTFS.PRP_PASSWORD, "TFS password", context, "Read from console", false));
		ContextAwareProxyConfigurationFactory.addContextProperties(contextProperties, context, "TFS");
	}
	
	public static final TFSRestConnection getConnection(Context context) {
		IContextTFSConnection ctx = context.as(IContextTFSConnection.class);
		TFSRestConnection result = ctx.getTFSConnection();
		if ( result == null ) {
			result = createConnection(context);
			ctx.setTFSConnection(result);
		}
		return result;
	}

	private static final TFSRestConnection createConnection(Context context) {
		IContextTFS ctx = context.as(IContextTFS.class);
		
		String baseUrl = ctx.getTFSBaseUrl();
		String userName = ctx.getTFSUserName();
		String password = ctx.getTFSPassword();
		
		// Read base URL from console if not defined
		if ( StringUtils.isBlank(baseUrl) ) {
			baseUrl = System.console().readLine("TFS URL: ");
		}
		
		// Read user name from console if not defined
		if ( StringUtils.isBlank(userName) ) {
			userName = System.console().readLine("TFS User Name: ");
		}
		
		// Read password from console if not defined
		if ( StringUtils.isBlank(password) ) {
			password = new String(System.console().readPassword("TFS Password: "));
		}
		
		ProxyConfiguration proxy = ContextAwareProxyConfigurationFactory.getProxyConfiguration(context, "TFS");
		return new TFSRestConnection(baseUrl, new UsernamePasswordCredentials(userName, password), proxy);
	}
	
	private interface IContextTFSConnection {
		public void setTFSConnection(TFSRestConnection connection);
		public TFSRestConnection getTFSConnection();
	}
}
