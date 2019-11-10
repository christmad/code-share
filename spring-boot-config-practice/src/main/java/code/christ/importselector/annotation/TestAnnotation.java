package code.christ.importselector.annotation;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

/**
 * Created by christmad on 2019/10/25.
 */
@Configuration("simpleAnnotationTest")
public class TestAnnotation {

    public static void main(String[] args) {
        Class<TestAnnotation> clz = TestAnnotation.class;
        AnnotatedType[] annotatedInterfaces = clz.getAnnotatedInterfaces();
        System.out.println("==========clz.getAnnotatedInterfaces=========");
        System.out.println("AnnotatedType[] length=" + annotatedInterfaces.length);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (AnnotatedType annotatedInterface : annotatedInterfaces) {
            sb.append("no ").append(i++).append(":").append(annotatedInterface.toString());
        }
        System.out.println(sb.toString());

        AnnotatedType annotatedSuperclass = clz.getAnnotatedSuperclass();
        System.out.println("=========clz.getAnnotatedSuperclass===========");
        System.out.println(annotatedSuperclass.toString());

        Annotation[] annotations = clz.getAnnotations();
        System.out.println("=============clz.getAnnotations=============");
        sb = new StringBuilder();
        i = 0;
        for (Annotation annotation : annotations) {
            sb.append("no ").append(i++).append(":").append(annotation.toString());
        }
        System.out.println(sb.toString());

        Annotation[] declaredAnnotations = clz.getDeclaredAnnotations();
        System.out.println("=========clz.getDeclaredAnnotations=========");
        sb = new StringBuilder();
        i = 0;
        for (Annotation declaredAnnotation : declaredAnnotations) {
            sb.append("no ").append(i++).append(":").append(declaredAnnotation.toString());
        }
        System.out.println(sb.toString());
    }
}
