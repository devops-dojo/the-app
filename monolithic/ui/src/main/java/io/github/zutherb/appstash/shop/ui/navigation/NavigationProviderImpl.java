package io.github.zutherb.appstash.shop.ui.navigation;

import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author zutherb
 */
@Component("navigationProvider")
public class NavigationProviderImpl implements NavigationProvider {

    private static final Predicate PAGE_PREDICATE = pageCandidate -> Page.class.isAssignableFrom((Class<?>) pageCandidate);

    private static final String[] PACKAGE_SCAN_PATH = {"io.github.zutherb.appstash.shop.ui.page"};

    private static final Set<Class<? extends Page>> PAGES_WITH_NAVIGATION_ANNOTATION;
    private static final Set<Class<? extends Page>> PAGES_WITH_ENUM_NAVIGATION_ANNOTATION;

    static {
        PAGES_WITH_NAVIGATION_ANNOTATION = getAnnotatedWicketPage(PACKAGE_SCAN_PATH[0],
                NavigationItem.class);
        PAGES_WITH_ENUM_NAVIGATION_ANNOTATION = getAnnotatedWicketPage(PACKAGE_SCAN_PATH[0],
                EnumProductTypeNavigationItem.class);
    }

    private ApplicationContext applicationContext;
    private AuthenticationService authenticationService;

    @Autowired
    public NavigationProviderImpl(ApplicationContext applicationContext,
                                  AuthenticationService authenticationService) {
        this.applicationContext = applicationContext;
        this.authenticationService = authenticationService;
    }

    @Override
    public Navigation getNavigation() {
        Navigation navigation = new Navigation();
        for (Class<?> annotatedClass : PAGES_WITH_NAVIGATION_ANNOTATION) {
            processNavigationItem(navigation, annotatedClass);
        }
        for (Class<?> enumAnnotatedClass : PAGES_WITH_ENUM_NAVIGATION_ANNOTATION) {
            processEnumNavigationItem(navigation, enumAnnotatedClass);
        }
        return navigation;
    }

    private void processNavigationItem(Navigation navigation, Class<?> clazz) {
        NavigationItem annotation = clazz.getAnnotation(NavigationItem.class);
        NavigationGroup navigationGroup = getNavigationGroup(navigation, annotation.group());
        Class<? extends Page> aClassAsSubClass = clazz.asSubclass(Page.class);

        navigationGroup.getNavigationEntries().add(new NavigationEntry(annotation.name(), annotation.sortOrder(),
                aClassAsSubClass, isVisible(annotation.visible(), clazz)));
    }

    private void processEnumNavigationItem(Navigation navigation, Class<?> clazz) {
        EnumProductTypeNavigationItem annotation = clazz.getAnnotation(EnumProductTypeNavigationItem.class);
        NavigationGroup navigationGroup = getNavigationGroup(navigation, annotation.group());
        Class<? extends Page> aClassAsSubClass = clazz.asSubclass(Page.class);

        for (ProductType anEnum : annotation.enumClazz().getEnumConstants()) {
            ProductType productType = anEnum;
            PageParameters pageParameters = new PageParameters().set("type", productType.getUrlname());

            navigationGroup.getNavigationEntries().add(new NavigationEntry(productType.getName(), annotation.sortOrder(),
                    aClassAsSubClass, pageParameters, isVisible(annotation.visible(), clazz)));
        }
    }


    private NavigationGroup getNavigationGroup(Navigation navigation, String groupName) {
        if (navigation.getNavigationGroup(groupName) == null) {
            navigation.getNavigationGroups().add(new NavigationGroup(groupName));
        }
        return navigation.getNavigationGroup(groupName);
    }

    private boolean isVisible(String visible, Class<?> aClass) {
        if (aClass.isAnnotationPresent(Secured.class)) {
            Secured annotation = aClass.getAnnotation(Secured.class);
            return isVisible(visible) && authenticationService.isAuthorized(annotation.value());
        }
        return isVisible(visible);
    }

    private boolean isVisible(String visible) {
        if (StringUtils.isNotEmpty(visible)) {
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setBeanResolver(new ApplicationContextBeanResolver());
            return parser.parseExpression(visible).getValue(context, Boolean.class);
        }
        return true;
    }

    private class ApplicationContextBeanResolver implements BeanResolver {
        public Object resolve(EvaluationContext context, String beanname) throws AccessException {
            return applicationContext.getBean(beanname);
        }
    }

    @Override
    public void setClassPathToScan(String classPathToScan) {
        PACKAGE_SCAN_PATH[0] = classPathToScan;
    }

    @SuppressWarnings("unchecked") // apache commons collection api does not support generics
    public static Set<Class<? extends Page>> getAnnotatedWicketPage(String packageScanPath, Class<? extends Annotation> annotationClazz) {
        Reflections reflections = new Reflections(packageScanPath);
        return SetUtils.predicatedSet(reflections.getTypesAnnotatedWith(annotationClazz), PAGE_PREDICATE);
    }
}
