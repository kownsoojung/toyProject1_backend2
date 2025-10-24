package toy.project.apiserver.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;

@Component
public class UQueryDslUtils {

    private static String gEncKey;

    public UQueryDslUtils(@Value("${mybatis.configuration-properties.G_EncKey}") String gEncKey) {
        UQueryDslUtils.gEncKey = gEncKey;
    }

    public StringTemplate aesDecryptChar(Object column) {
        return Expressions.stringTemplate(
            "AES_DECRYPT(UNHEX({0}), {1})",
            column,
            Expressions.constant(gEncKey)
        );
    }
}