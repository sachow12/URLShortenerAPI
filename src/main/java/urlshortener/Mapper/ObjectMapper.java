package urlshortener.Mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;


public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static<O, D> D parseObject(O object, Class<D> destinationClass) {
        return mapper.map(object, destinationClass);
    }
}
