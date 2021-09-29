package com.xschen.commonmistakes.java8;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * {@link Optional} 示例
 * @author xschen
 */
public class OptionalTest {

    @Test
    public void optionalUsageTest() throws Throwable {
        // 创建空的 Optional 对象
        Optional.empty();
        // 使用非null值创建 Optional 对象
        Optional.of("Tom");
        // 可接受null的 Optional 对象
        Optional optional = Optional.ofNullable("Tom");

        // 判断是否引用缺失的方法(不建议使用)
        optional.isPresent();

        // 如果非空，则执行 consumer 方法
        // 类似的方法 map, filter, flatMap
        optional.ifPresent(System.out::println);

        // 当Optional缺失时
        optional.orElse("Jack");
        optional.orElseGet(() -> "Mary");
        optional.orElseThrow(() -> {
            throw new RuntimeException("exception");
        });
    }

    /**
     * 如果测试产生 IllegalArgumentException，则代表测试通过
     */
    @Test(expected = IllegalArgumentException.class)
    public void optional() {
        // 通过 get 方法获取 Optional 中的实际值
        Assert.assertThat(Optional.of(1).get(), Matchers.is(1));

        // 通过 orElse 给一个空值时的默认值
        Assert.assertThat(Optional.ofNullable(null).orElse("A"), Matchers.is("A"));

        // 通过 isPresent 判断有无数据, OptionalDouble 是基本double类型的Optional对象
        Assert.assertFalse(OptionalDouble.empty().isPresent());

        // 通过map方法可以对Optional对象进行级联转换，不会出现空指针，转换后还是一个Optional
        Assert.assertThat(Optional.of(1).map(Math::incrementExact).get(), Matchers.is(2));

        // 通过filter实现Optional中数据的过滤，得到一个Optional，然后级联使用orElse提供默认值
        Assert.assertThat(Optional.of(1).filter(i -> i % 2 == 0).orElse(null), Matchers.is(Matchers.nullValue()));

        // 通过 orElseThrow 实现无数据时抛出异常
        Optional.empty().orElseThrow(IllegalArgumentException::new);
    }
}
