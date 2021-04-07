# Pulsar Function Sample with Message Property

## Create Message with Property

Below function will create a new message with two properties:

- content, refer to the original message record value.
- random, refer to a random generated string.

```java
public class PropertiesGeneratorFunction implements Function<String, String> {
    @Override
    public String process(String s, Context context) throws Exception {
        try {
            String generatedString = RandomStringUtils.randomAlphabetic(10);
            context.newOutputMessage(context.getOutputTopic(), Schema.STRING).value(s + "-properties").property("content", s).property("random", generatedString).sendAsync();
        }catch (PulsarClientException e) {
            context.getLogger().error("newOutputMessage failed", e);
        }
        return null;
    }
}
```

## Copy Message with Property

Below function make property copy and re-created Message with content and properties.
```java
public class PropertiesCopyFunction implements Function<String, String>{
    @Override
    public String process(String s, Context context) throws Exception {
        Map<String, String> properties = context.getCurrentRecord().getProperties();

        try {
            context.newOutputMessage(context.getOutputTopic(), Schema.STRING).value(s + "!").properties(properties).sendAsync();
        }catch (PulsarClientException e) {
            context.getLogger().error("newOutputMessage failed", e);
        }
        return null;
    }
}
```

## Read Message Property

Below function prints message properties if the message contains any property.
```java
public class PropertiesPrintFunction implements Function<String, String> {
    @Override
    public String process(String s, Context context) throws Exception {
        Map<String, String> properties = context.getCurrentRecord().getProperties();
        if(properties!=null && !properties.isEmpty()) {
            properties.forEach((k,v) -> {
                context.getLogger().info("message: {}, property: {}: {}", s, k, v);
            });
        }
        return null;
    }
}
```