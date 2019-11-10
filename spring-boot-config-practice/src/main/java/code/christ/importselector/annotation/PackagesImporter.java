package code.christ.importselector.annotation;

import code.christ.importselector.PackagesImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by christmad on 2019/10/23.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PackagesImportSelector.class)
public @interface PackagesImporter {
    String[] packages();
}
