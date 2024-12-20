package in.virit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.button.DefaultButton;
import org.vaadin.firitin.util.style.LumoProps;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        // This class contains some examples of how to style components in Vaadin, with Java.

        Button button = new Button("Click me");

        // the worst possible way to make your button red
        button.getElement().getStyle().set("color", "red");

        // a slightly better way to make your button red
        button.getStyle().set("color", "red");

        // Using a typed API on a typed Java makes the code easier to write and maintain.
        // Too bad the color itself is a string still, the world isn't ready yet
        button.getStyle().setColor("red");

        // But good code is like a story, this story is missing why the button is red

        // Hide the red color to a meaningful class
        button = new ImportantButton("Click me");

        // or if it needs to become read
        var highlightableButton = new HighlightableButton("Click me");
        highlightableButton.highlight();

        // TIP, if you want to give possibility for your CSS artist to customize the color
        // with pure CSS, you should use a CSS class instead of setting the color directly.
        // Still, it makes sense to write readable Java code, for your own sake...

        var primaryButton = new DefaultButton("Click me"); // a button with a primary style, coming from Viritin add-on
        add(primaryButton);

        // now it would be "blueish" by default, but you could customise the color with themes css properties
        // withing the scope of this view. The follow affects all components within this layout,
        // not just the primary buttons, but e.g. focus color of certain componens etc.

        getStyle() // the style of the layout
                .set("--lumo-primary-color", "green");

        // The there is currently no official enumeration of the Lumo properties, but you can find
        // most of them from Viritin LumoStyles class. The following would do the same as above.
        // The first parameter is the scope. If you leave that out it would affect the whole page.
        // If there are only Lumo property customisations in your theme, you could express it in pure Java.
        LumoProps.PRIMARY_COLOR.define(this, "green");

        // In your own components, you can also utilize the LumoProps, and this way keep them
        // consistent (and configurable) with the rest of the Vaadin components.
        // The following uses 10% tint of your theme's warning color.
        getStyle().setBackgroundColor(LumoProps.WARNING_COLOR_10PCT.var());

        add(button);
    }

    class ImportantButton extends Button {
        public ImportantButton(String text) {
            super(text);
            getStyle().setColor("red");
        }
    }

    class HighlightableButton extends Button {
        public HighlightableButton(String text) {
            super(text);
        }

        public void highlight() {
            getStyle().setColor("red");
        }
    }


}
