package converter;

/**
 * 转换器将网络响应类型 P 转换为结果类型 R
 * @param <R>
 * @param <P>
 */
public interface IConverter<R, P> {
    R convert(P responseBean);
}
