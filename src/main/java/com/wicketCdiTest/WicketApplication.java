package com.wicketCdiTest;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;

import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.wicketTest.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	@Override
	public Class<CdiScopesPage> getHomePage() {
		return CdiScopesPage.class;
	}

	@Override
	public void init() {
		super.init();

		// add your configuration here
		getApplicationSettings().setUploadProgressUpdatesEnabled(true);
		getRequestCycleSettings().setTimeout(Duration.ONE_HOUR);

		setupCdi();
	}

	private void setupCdi() {
		BeanManager manager = null;
		// BeanManager manager = (BeanManager) getServletContext().getAttribute(
		// Listener.BEAN_MANAGER_ATTRIBUTE_NAME);

		// in application servers you can retrieve the bean manager from JNDI:
		try {
			Object lookup = new InitialContext()
					.lookup("java:comp/BeanManager");
			manager = (BeanManager) lookup;
		} catch (Exception e) {
			e.printStackTrace();
		}

		new CdiConfiguration(manager).configure(this);
	}
}
