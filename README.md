
# Use Java - It Can Be the Most Elegant Method for You

*TLDR: this is a ranty reply to a [recent article about styling](https://vaadin.com/blog/theming-vaadin-applications-a-practical-guide) I didn't fully agree with. Rest of the TLDR in the [Java code example](https://github.com/mstahv/java-styling-examples/blob/main/src/main/java/in/virit/MainView.java).*

Java code can be elegant — even when it’s adjusting the style of your UI components. In a recent article about theming Vaadin applications, my colleague suggested that using Java for styling components is a bad practice. I couldn’t disagree more (with some considerations). 

Sure, if your colleague primarily works with CSS, they might think this opinion is heretical. But for you, as a Java developer, using a typed and discoverable Java API from your favorite Java IDE can be the most elegant solution available. There’s a good chance you chose Vaadin because it lets you avoid HTML and JavaScript. Well, Vaadin can shield you from CSS too—at least to some extent.

Yes, working with raw CSS has its benefits, like making it easier to delegate styling tasks to a specialist who might not know Java. But if you’re a Java developer, don’t feel ashamed of styling components in Java. It really doesn’t make sense to dive into CSS just to make a button green. The best approach depends on your application, skills, and requirements. Personally, I’m proficient in CSS, but when I’m working in my Java "bubble" with Vaadin apps, I prefer to handle smaller tweaks using the pure Java API.

On another note, I wasn’t thrilled with the code examples in that recent post. If you choose to style components with Java, don’t just copy and paste from there! Let me share a few tips on how to style your components *the Java style*.


## Do It at the Right Abstraction Level

The [Style class](https://vaadin.com/api/platform/current/com/vaadin/flow/dom/Style.html) was introduced in Vaadin 10 as part of the `Element` API, a lower-level API for accessing the DOM. It’s designed to wrap HTML elements as Vaadin Flow UI components. For most components, `Style` is also exposed to the higher-level `Component` API via the `HasStyle` interface. In both cases, the `Style` API modifies the `style` property of an HTML element.

Here’s a key tip: watch out for `getElement()` calls. They’re almost always a sign that you’re doing it wrong. When building applications, you should generally retrieve the `Style` reference from the component itself. So instead of this:

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L19-L20

Do this:

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L22-L23

If you’re building custom components, direct access to the `Element` level is fine. But even then, you’ll often have a reference to the specific elements used within your component.

## The Style API Is More Than a String-String Map

In earlier versions, the `Style` API was essentially just a string-string map that updated the HTML element’s `style` property in the browser. This approach is prone to errors—similar to writing raw CSS without tooling support. 

Thankfully, recent updates have introduced better APIs. Many commonly used properties now have dedicated methods, some with typed APIs. This makes the styling options more discoverable and less error-prone.

For example setting that color is faster and less error prone to write in your Java IDE with this form:

```java
getStyle().setColor("red");
```

Too bad we don't (at least yet) provide an asbtraction for the color, but for most of the "enums" we do. E.g.

```java
getStyle().setWhiteSpace(Style.WhiteSpace.NOWRAP);
```

Let your IDE autocomplete the options for you—it’s faster and safer.

## Do It in a Right Class

A code like `button.getStyle().setColor("red")` above still indicates a code smell. Your CSS-loving friend might suggest creating a CSS class to name your styled button. I recommend doing the same — but [don’t feel bad if it’s a Java class](https://vaadin.com/blog/dont-be-afraid-of-java-classes)! 

Here’s a simple example:

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L66-L71

This not only applies the style but also documents why the component is red.

The same principle applies when styles need to change dynamically. For example:

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L73-L81

By providing a clear API for the user, you improve code readability and maintainability. As your styling requirements grow, you can leverage your Java skills to make the code more reusable, configurable, and maintainable.

## CSS Variables Can Be Used from Java Too

As mentioned in the previous article (the one I’m critiquing a bit here), Vaadin provides a great configurable foundation for custom themes. The current version, Lumo, uses modern CSS variables (unlike its predecessor, which relied on the SASS preprocessor). While there’s no built-in shorthand for these variables, you can use them through the Java API and even redefine their values.

Consider you have are using a button with ButtonVariant.LUMO_PRIMARY (or e.g. the DefaultButton from Viritin which does that). By default those buttons would be blue. The following code snippet in your layout/view would turn all of them green.

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L49-L50

You could also scope the variable override to a level of single component.

In the Viritin add-on, I’ve gathered Lumo’s variables into an enumeration, with some helper methods. Similar to the typed `Style` API, this makes working with Lumo properties faster and less error-prone (as long as you are using and mastering your Java IDE).

https://github.com/mstahv/java-styling-examples/blob/cf6a143c1cf89696e22b38ba342e96b659471197/src/main/java/in/virit/MainView.java#L52-L61

---

To sum up: embrace Java for styling when it fits your skills and application requirements. You’ll be surprised how elegant and powerful it can be!
