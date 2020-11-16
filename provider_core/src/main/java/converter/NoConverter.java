package converter;

@SuppressWarnings("all")
public class NoConverter<R, P> implements IConverter<R, P> {
    @Override
    public R convert(P bean) {
        return ((R) bean);
    }
}
