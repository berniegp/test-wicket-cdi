package com.wicketCdiTest;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Base page for test pages. Adds a debug panel among others.
 * 
 * @author Bertrand Guay-Paquet
 * 
 */
public class TestBasePage extends WebPage {

	public TestBasePage(PageParameters parameters) {
		super(parameters);
		createBaseComponents();
	}

	public TestBasePage() {
		super();
		createBaseComponents();
	}

	private void createBaseComponents() {
		add(new BookmarkablePageLink<Void>("home", WicketApplication.get()
				.getHomePage()));
		add(new DebugBar("debug"));
	}
}
