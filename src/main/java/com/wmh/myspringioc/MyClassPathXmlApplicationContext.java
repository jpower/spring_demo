package com.wmh.myspringioc;

import com.wmh.myspringioc.annotation.MyResource;
import com.wmh.myspringioc.annotation.MyService;
import com.wmh.myspringioc.util.ClassUtil;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by 周大侠
 * 2018-11-26 9:17
 */
public class MyClassPathXmlApplicationContext {
    private String xmlUrl;
    // Bean容器
    private ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

    public MyClassPathXmlApplicationContext(String xmlUrl) throws Exception {
        this.xmlUrl = xmlUrl;
        init();
    }

    /**
     * 初始化
     * 1.获取所有子节点
     * 2.创建对象
     *
     * @throws Exception
     */
    public void init() throws Exception {
        List<Element> elements = getElements();
        if (elements == null || elements.isEmpty()) {
            throw new Exception("该配置文件没有子元素");
        }

        for (Element element : elements) {
            String name = element.getName();
            // 获取需要扫描包的地址 根据注解创建bean
            if (name.equals("component-scan")) {
                String basePackage = element.attributeValue("base-package");
                newInstanceByannotation(basePackage);
            }
            // 根据xml创建bean
            if (name.equals("bean")) {
                newInstanceByXml(element);
            }
        }

    }

    /**
     * 通过Id获取对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Object getBean(String id) throws Exception {
        Object bean = map.get(id);
        if (bean == null) {
            throw new Exception("找不到该bean");
        }
        // 属性赋值
        fieldInit(bean);
        System.out.println("sdf");
        return bean;
    }

    /**
     * 通过class获取对象
     *
     * @param cla
     * @return
     * @throws Exception
     */
    public Object getBean(Class<?> cla) throws Exception {
        Collection<Object> values = map.values();
        for (Object value : values) {
            if (value.getClass() == cla) {
                // 属性赋值
                fieldInit(value);
                return value;
            }
        }
        throw new Exception("找不到該bean");
    }

    /**
     * 将xnml中定义的bean创建对象放入map中
     *
     * @param element
     * @throws Exception
     */
    private void newInstanceByXml(Element element) throws Exception {
        // 获取全类名
        String classValue = element.attributeValue("class");
        if (StringUtils.isEmpty(classValue)) {
            throw new Exception("没有定义bean的class地址");
        }
        Class<?> aClass = Class.forName(classValue);
        Object object = aClass.newInstance();
        // 获取id属性
        String id = element.attributeValue("id");
        // 进行处理 如果没有id 类名首字母小写
        if (StringUtils.isEmpty(id)) {
            id = idToLower(aClass.getSimpleName());
        }
        map.put(id, object);
    }

    /**
     * 创建注解定义的bean 放入map中
     * @param basePackage
     * @throws Exception
     */
    private void newInstanceByannotation(String basePackage) throws Exception {
        List<Class<?>> classes = ClassUtil.getClasses(basePackage);
        for (Class<?> aClass : classes) {
            MyService annotation = aClass.getDeclaredAnnotation(MyService.class);
            if (annotation != null) {
                Object o = aClass.newInstance();
                map.put(idToLower(aClass.getSimpleName()),o);
            }
        }

    }

    private void fieldInit(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            MyResource resource = field.getAnnotation(MyResource.class);
            if (resource!=null){
                field.setAccessible(true);
                field.set(object,map.get(field.getName()));
            }
        }
    }


    /**
     * 转换Id
     *
     * @param simpleName
     * @return
     */
    private String idToLower(String simpleName) {
        return new StringBuilder().append(simpleName.toLowerCase().charAt(0)).append(simpleName.substring(1)).toString();
    }

    /**
     * 获取所有子节点
     *
     * @return
     * @throws DocumentException
     */
    private List<Element> getElements() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(xmlUrl);
        Document document = saxReader.read(stream);
        // 获取根节点
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return elements;
    }
}
