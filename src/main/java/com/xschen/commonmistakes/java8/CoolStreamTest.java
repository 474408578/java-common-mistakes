package com.xschen.commonmistakes.java8;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Stream 使用示例
 * 
 * @see Map#computeIfAbsent(Object, Function)
 *
 * @author xschen
 */
public class CoolStreamTest {

    private Map<Long, Product> cache = new ConcurrentHashMap<>();

    private static double calc(List<Integer> ints) {
        List<Point2D> point2DList = new ArrayList<>();
        for (Integer i : ints) {
            point2DList.add(new Point2D.Double((double) i % 3, (double) i / 3));
        }

        double total = 0;
        int count = 0;
        for (Point2D point2D : point2DList) {
            if (point2D.getY() > 1) {
                double distance = point2D.distance(0, 0);
                total += distance;
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    /**
     * map 方法传入一个Function，实现对象转换
     * filter 方法传入一个Predicate， 实现对象的 bool 判断，只保留返回 true 的数据
     * mapToDouble 传入一个Function，将对象转换为 double
     * average 传入一个 OptionalDouble（代表一个可空double），求平均数
     * orElse 有值则返回，否则返回默认值
     */
    @Test
    public void streamTest() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        double average = calc(ints);
        double streamResult = ints.stream()
                .map(i -> new Point2D.Double((double) i % 3, (double) i / 3))
                .filter(point -> point.getY() > 1)
                .mapToDouble(point -> point.distance(0, 0))
                .average()
                .orElse(0);
        Assert.assertThat(average, Matchers.is(streamResult));
    }

    @Test
    public void notCoolCache() {
        getProductAndCache(1L);
        getProductAndCache(100L);

        System.out.println(cache);
        Assert.assertThat(cache.size(), Matchers.is(1));
        Assert.assertTrue(cache.containsKey(1L));
    }

    @Test
    public void CoolCache() {
        getProductAndCacheCool(1L);
        getProductAndCacheCool(100L);

        System.out.println(cache);
        Assert.assertThat(cache.size(), Matchers.is(1));
        Assert.assertTrue(cache.containsKey(1L));
    }

    private Product getProductAndCache(Long id) {
        Product product = null;
        if (cache.containsKey(id)) { // 缓存中存在，直接取
            return cache.get(id);
        } else { // 去数据库查
            for (Product p : Product.getData()) {
                if (p.getId().equals(id)) {
                    product = p;
                    break;
                }
            }
            // 加入到 cache
            if (product != null) {
                cache.put(id, product);
            }
        }
        return product;
    }

    private Product getProductAndCacheCool(Long id) {
        return cache.computeIfAbsent(id, i -> // 当 key 不存在时，提供一个Function来根据Key获取Value
                Product.getData().stream()
                        .filter(product -> product.getId().equals(id)) // 过滤
                        .findFirst() // 找第一个，得到一个Optional<Product>
                        .orElse(null));
    }

    @Test
    public void coolFilesExample() throws IOException {
        // 无限深度，递归遍历文件夹
        try (Stream<Path> pathStream = Files.walk(Paths.get("."))) {
            pathStream.filter(Files::isRegularFile) // 只查普通文件
                    // 搜索Java源码文件
                    .filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches)
                    // Files.readAllLines会抛出受检异常 IOException
                    .flatMap(ThrowingFunction.unchecked(path ->
                            Files.readAllLines(path).stream() // 读取文件内容，转换为Stream<List>
                                    // 使用正则表达式过滤开头为public class 的行
                                    .filter(line -> Pattern.compile("^public class").matcher(line).find())
                                    .map(line -> path.getFileName() + " >> " + line)))
                    .forEach(System.out::println);
        }
    }

    /**
     *  将受检异常转换为运行时异常
     * @param <T> - the type of the input to the function
     * @param <R> - the type of the result of the function
     * @param <E> Exception
     */
    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Throwable> {
        static <T, R, E extends Throwable> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
            return t -> {
                try {
                    return f.apply(t);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        }

        R apply(T t) throws E;
    }
}
