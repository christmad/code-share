package code.christ.importselector;

import code.christ.importselector.annotation.PackagesImporter;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by christmad on 2019/11/7.
 */
public class PackagesImportSelector implements DeferredImportSelector {

    private List<String> clzList = new ArrayList<>(100);

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(PackagesImporter.class.getName(), true);
        if (attributes == null) {
            return new String[0];
        }
        String[] packages = (String[]) attributes.get("packages");
        if (packages == null) {
            return new String[0];
        }
        scanPackages(packages);
        return clzList.isEmpty() ? new String[0] : clzList.toArray(new String[0]);
    }

    private void scanPackages(String[] packages) {
        for (String path : packages) {
            doScanPackages(path);
        }
    }

    private LinkedList<File> directories = new LinkedList<>();
    private LinkedList<String> pathList = new LinkedList<>();
    /**
     * 递归处理子文件夹
     */
    private void doScanPackages(String path) {
        URL resource = this.getClass().getClassLoader().getResource(path.replaceAll("\\.", "/"));
        if (resource == null) {
            return;
        }
        File file = new File(resource.getFile());
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                pathList.addLast(path);
                directories.addLast(f);
            } else {
                // 先处理当前目录下的文件
                String fileName = f.getName();
                if (fileName.endsWith(".class")) {
                    String fullClassName = path + "." + fileName.substring(0, fileName.indexOf("."));
                    clzList.add(fullClassName);
                }
            }
        }
        // 保证先处理平级的包。比如我的demo中，会先加载 ClassA、ClassB、ClassC，然后加载 ClassAA
        while (!directories.isEmpty()) {
            doScanPackages(pathList.removeFirst() + "." + directories.removeFirst().getName());
        }

    }
}
