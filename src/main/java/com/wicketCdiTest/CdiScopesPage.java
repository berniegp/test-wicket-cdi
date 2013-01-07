package com.wicketCdiTest;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Tests for a wicket-cdi.
 * 
 * @author Bertrand Guay-Paquet
 * 
 */
public class CdiScopesPage extends TestBasePage {

	@Inject
	private RequestScope requestScoped;
	@Inject
	private SessionScope sessionScoped;
	@Inject
	private ApplicationScope applicationScoped;

	private UnmanagedObject unmanagedObject = new UnmanagedObject();

	public CdiScopesPage() {
		add(new StatelessLink<Void>("clearSession") {
			@Override
			public void onClick() {
				Session.get().invalidate();
				setResponsePage(CdiScopesPage.class);
			}
		});

		Object modelObject;
		WebMarkupContainer parent;

		// Managed objects
		parent = new WebMarkupContainer("managed");
		add(parent);
		modelObject = this;
		parent.add(new Label("requestScoped", new PropertyModel<String>(
				modelObject, "requestScoped.obj.value")));
		parent.add(new Label("sessionScoped", new PropertyModel<String>(
				modelObject, "sessionScoped.obj.value")));
		parent.add(new Label("applicationScoped", new PropertyModel<String>(
				modelObject, "applicationScoped.obj.value")));

		// Unmanaged objects (manually injected)
		parent = new WebMarkupContainer("unmanaged");
		add(parent);
		modelObject = unmanagedObject;
		parent.add(new Label("requestScoped", new PropertyModel<String>(
				modelObject, "requestScoped.obj.value")));
		parent.add(new Label("sessionScoped", new PropertyModel<String>(
				modelObject, "sessionScoped.obj.value")));
		parent.add(new Label("applicationScoped", new PropertyModel<String>(
				modelObject, "applicationScoped.obj.value")));
	}

	/**
	 * Inner random int holder object
	 */
	public static class RandomInt implements Serializable {
		private int random = new Random().nextInt();;

		public String getValue() {
			return "RandomInt == " + random;
		}
	}

	@RequestScoped
	public static class RequestScope {
		@Inject
		private RandomInt obj;

		public RandomInt getObj() {
			return obj;
		}
	}

	@SessionScoped
	public static class SessionScope implements Serializable {
		@Inject
		private RandomInt obj;

		public RandomInt getObj() {
			return obj;
		}
	}

	@ApplicationScoped
	public static class ApplicationScope implements Serializable {
		@Inject
		private RandomInt obj;

		public RandomInt getObj() {
			return obj;
		}
	}

	/**
	 * This object is not managed by wicket-cdi (field of this type for the Page
	 * is not annotated with @Inject. It is injected via its constructor.
	 */
	public static class UnmanagedObject implements Serializable {

		@Inject
		private RequestScope requestScoped;
		@Inject
		private SessionScope sessionScoped;
		@Inject
		private ApplicationScope applicationScoped;

		public UnmanagedObject() {
			// How to inject objects which are not Components, Pages, etc.
			CdiContainer.get().getNonContextualManager().inject(this);
		}

		public RequestScope getRequestScoped() {
			return requestScoped;
		}

		public SessionScope getSessionScoped() {
			return sessionScoped;
		}

		public ApplicationScope getApplicationScoped() {
			return applicationScoped;
		}
	}
}
