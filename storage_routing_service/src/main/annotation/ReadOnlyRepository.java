package thai.pham.storageroutingservice.annotation

@Retention(RetentionPolicy.RUNTIME) // Used so that spring can processing in runtime
@Target({ElementType.TYPE}) // Ensure the annotation only used in desired location - in this case only for class, interface or enums
@Documented // Help to include the annotation in javadoc where annotation is used
public @interface ReadOnlyRepository {
}
